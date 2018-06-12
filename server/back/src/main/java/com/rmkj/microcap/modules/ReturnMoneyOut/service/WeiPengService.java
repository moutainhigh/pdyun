package com.rmkj.microcap.modules.ReturnMoneyOut.service;/**
 * Created by Administrator on 2017/4/6.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPayResultBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPaySelectResBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.http.WeiPengApi;
import com.rmkj.microcap.common.modules.money.out.weipeng.service.WeiPengDaiPayService;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 佣金提现 威鹏
 * @author k
 * @create -04-06-14:36
 **/

@Service
public class WeiPengService {
    private final static Logger Log = Logger.getLogger(WeiPengService.class);

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;
    @Autowired
    private WeiPengDaiPayService weiPengDaiPayService;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IMoneyChangeDao moneyChangeDao;
    @Autowired
    private BaseService baseService;
    @HttpService
    private WeiPengApi weiPengApi;

    /**
     * 出金审核通过时-威鹏代付
     * @param id
     * @param s
     * @return
     */
    public ExecuteResult returnOutPastIn(String id, Integer s) {
        if (id.indexOf(",") == -1) {
            return weiPengReturnOutPastIn(id, s);
        } else {
            /*储存成功失败笔数*/
            Integer success = 0, fail = 0;
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; i++) {
                ExecuteResult executeResult = weiPengReturnOutPastIn(ids[i], s);
                if (11 == executeResult.getCode()) {
                    fail++;
                } else {
                    success++;
                }
            }
            return new ExecuteResult(StatusCode.WEIPENG_DAI_PAY_FAIL, "成功出金：".concat(success.toString()).concat("笔,失败：").concat(fail.toString()).concat("笔"));
        }


        /*MoneyRecordBean bean = new MoneyRecordBean();
        bean.setId(id);
        bean.setStatus(s);
        return moneyRecordDao.outPastIn(bean) == 1?new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);*/
    }

    /**
     * 用户账户提现-----威鹏代付
     * @param id
     * @param s
     * @return
     */
    public synchronized ExecuteResult weiPengReturnOutPastIn(String id, Integer s) {
        ReturnMoneyOutBean returnMoneyOutBean = iReturnMoneyOutDao.get(id);
        //审核过的订单不再进行操作
        if (0 != returnMoneyOutBean.getReviewStatus()) {
            return new ExecuteResult(StatusCode.HAS_PAST);
        }
        //出金代付请求接口
        String response = weiPengDaiPayService.getWeiPengDaiPayResponse(returnMoneyOutBean);
        if (null == response) {
            Log.error("出金接口请求失败!");
            return new ExecuteResult(StatusCode.OUT_MONEY_FAULT);
        }
        WeiPengDaiPayResultBean weiPengDaiPayResultBean = JSON.parseObject(response, WeiPengDaiPayResultBean.class);
        //出金代付失败时
        if (!"10000".equals(weiPengDaiPayResultBean.getReturn_code())) {
            Log.error("出金失败订单:" + response);
            //出金失败逻辑处理
            ReturnMoneyOutBean returnMoney = new ReturnMoneyOutBean();
            returnMoney.setId(returnMoneyOutBean.getId());
            returnMoney.setStatus(2);
            returnMoney.setReviewStatus(1);
            returnMoney.setRemark(weiPengDaiPayResultBean.getMessage() == null ? "" : weiPengDaiPayResultBean.getMessage());
            if (iReturnMoneyOutDao.outPastInStatusAndReviewStatus(returnMoney) == 1 && updateNotOutMoney(returnMoneyOutBean, weiPengDaiPayResultBean.getMessage()) == 1) {
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的提现失败，手续费").concat(returnMoneyOutBean.getFee().toString()).concat("元"));
                return new ExecuteResult(StatusCode.WEIPENG_DAI_PAY_FAIL);
            }
        }else {
            Log.info("出金成功订单:" + response);
            ReturnMoneyOutBean recordBeanSuccess = new ReturnMoneyOutBean();
            recordBeanSuccess.setId(id);
            recordBeanSuccess.setReviewStatus(1);
            recordBeanSuccess.setRemark(weiPengDaiPayResultBean.getMessage());
            recordBeanSuccess.setThirdSerialNo(weiPengDaiPayResultBean.getOrderid());
            if (iReturnMoneyOutDao.outPastInStatusAndReviewStatus(recordBeanSuccess) == 1) {
                return new ExecuteResult(StatusCode.OK);
            }
        }

        return new ExecuteResult(StatusCode.WEIPENG_DAI_PAY_FAIL);
    }

    /**
     * TODO 出金代付或审核失败时，资金返回账户余额
     * @param returnMoneyOutBean
     * @return
     */
    public int updateNotOutMoney(ReturnMoneyOutBean returnMoneyOutBean, String failureReason){

        UserBean userBean = userDao.get(returnMoneyOutBean.getUserId());
        userBean.setReturnMoney(returnMoneyOutBean.getMoney().add(returnMoneyOutBean.getFee()));

        //出金审核时，再修改用户表，新增资金变更表
        int status =  userDao.userReturnMoney(userBean);//修改用户表佣金余额
        if(1 == status){
            UserBean userBean1 = userDao.get(userBean);//修改用户表佣金余额后的用户实体

            MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
            moneyChangeBean.setId(Utils.uuid());
            moneyChangeBean.setUserId(returnMoneyOutBean.getUserId());
            moneyChangeBean.setDifMoney(returnMoneyOutBean.getMoney());//变更金额
            moneyChangeBean.setType(4);
            moneyChangeBean.setAfterMoney(userBean1.getReturnMoney());//变更后的金额
            BigDecimal moneybefore = userBean1.getReturnMoney().subtract(returnMoneyOutBean.getMoney().add(returnMoneyOutBean.getFee()));//变更前金额
            moneyChangeBean.setBeforeMoney(moneybefore);
            moneyChangeBean.setCreateTime(new Date());
            moneyChangeBean.setRemark(failureReason);
            moneyChangeDao.insert(moneyChangeBean);//新增资金变更表
        }else{
            return -1;
        }
        return status;
    }

    public String reviewNoPass(String ids, String failureReason){
        return baseService.reviewNoPass(ids, failureReason);
    }

    /**
     * 定时查询威鹏代付结果  收益提现
     */
    //@Scheduled(initialDelay = 20000, fixedRate = 15000)
    public void checkOutResult2(){
        List<ReturnMoneyOutBean> list = iReturnMoneyOutDao.findWeiPengNoResult2Check();
        if(ProjectConstants.PRO_DEBUG || CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(moneyRecord -> {
            try{
                Response<String> execut = weiPengApi.selWeiPengDaiPayResult(ProjectConstants.WEIPENG_PAY_MERCHANT_NO,moneyRecord.getSerialNo()).execute();
                if(execut.isSuccessful()){
                    String info = execut.body();
                    Log.info("威鹏定时查询代付结果返回："+info);
                    WeiPengDaiPaySelectResBean weiPengDaiPaySelectResBean = JSONObject.parseObject(info,WeiPengDaiPaySelectResBean.class);
                    if("10000".equals(weiPengDaiPaySelectResBean.getReturn_code())){
                        //代付成功,发消息
                        sendMessageToUser(moneyRecord.getUserId(), "您的提现申请已通过,手续费".concat(moneyRecord.getFee().toString()).concat("元，实际到账").concat(moneyRecord.getMoney().toString()).concat("元"));
                    }else if("10010".equals(weiPengDaiPaySelectResBean.getReturn_code())){//正在处理中

                    }else {
                        //代付失败，改状态，返钱，发消息
                        ReturnMoneyOutBean recordBeanFail = new ReturnMoneyOutBean();
                        recordBeanFail.setId(moneyRecord.getId());
                        recordBeanFail.setStatus(2);
                        recordBeanFail.setReviewStatus(1);
                        recordBeanFail.setRemark(weiPengDaiPaySelectResBean.getMessage() == null ? "" : weiPengDaiPaySelectResBean.getMessage());
                        if (iReturnMoneyOutDao.outPastInStatusAndReviewStatus(recordBeanFail) == 1 && updateNotOutMoney(moneyRecord, weiPengDaiPaySelectResBean.getMessage()) == 1) {
                            sendMessageToUser(moneyRecord.getUserId(), "您的提现申请未通过,手续费".concat(moneyRecord.getFee().toString()).concat("元，提现金额").concat(moneyRecord.getMoney().toString()).concat("元"));
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    /**
     * 通知用户
     * @param userId
     * @param content
     */
    private void sendMessageToUser(String userId, String content){
        UserMessageBean userMessageBean = new UserMessageBean();
        userMessageBean.setId(Utils.uuid());
        userMessageBean.setUserId(userId);
        userMessageBean.setTitle("提现");
        userMessageBean.setContent(content);
        userMessageBean.setReadStatus(0);
        userMessageBean.setType(0);
        userMessageBean.setCreateTime(new Date());
        userDao.insertUserMessage(userMessageBean);
    }

}
