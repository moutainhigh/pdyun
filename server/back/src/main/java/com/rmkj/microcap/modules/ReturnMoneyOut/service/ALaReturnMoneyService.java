package com.rmkj.microcap.modules.ReturnMoneyOut.service;/**
 * Created by Administrator on 2017/4/7.
 */

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.BalanceBean;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.WithdrawalsResBean;
import com.rmkj.microcap.common.modules.money.out.ytb.service.WithdrawalsService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author k
 * @create -04-07-14:52
 **/

@Service
public class ALaReturnMoneyService {
    private final static Logger Log = Logger.getLogger(ALaReturnMoneyService.class);

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;

    @Autowired
    private WithdrawalsService withdrawalsService;

    @Autowired
    private IUserDao userDao;

    /**
     * 走三方提现接口
     * id用英文逗号连接的字符串
     * @return
     */
    public String batchOut(String[] idArr){
        final int[] record = {0 ,0 ,0 ,0}; //总条数-成功-失败-超时
        final int[] failCount = {0};
        final int[] timeOutCount = {0};
        if(idArr.length != 0){
            List<ReturnMoneyOutBean> list = iReturnMoneyOutDao.queryInfoForReturnMoneyOut(Arrays.asList(idArr));
            StringBuilder sb = new StringBuilder();
            double[] totalMoney = new double[1];
            // 手续费怎么扣除
            if(!ProjectConstants.PRO_DEBUG){
                for(ReturnMoneyOutBean moneyRecord:list){
                    BalanceBean balanceBean = withdrawalsService.selBalance();
                    if(balanceBean.getAmount() == null){
                        return "查询商户余额接口异常!";
                    }
                    if(Double.parseDouble(balanceBean.getAmount()) < moneyRecord.getMoney().doubleValue()){
                        return "余额不足，剩余资金为".concat(balanceBean.getAmount()).concat("元！");
                    }
                    //提交代付
                    WithdrawalsResBean withdrawalsResBean = withdrawalsService.withdrawals(moneyRecord);
                    Log.info("实时代付接口结果返回：" + withdrawalsResBean.getRespCode() +" 描述："+ withdrawalsResBean.getMessage()+" 提现状态"+withdrawalsResBean.getTransStatus()+" 代付状态："+withdrawalsResBean.getPayStatus());
                    if ("00".equals(withdrawalsResBean.getRespCode()) && "2".equals(withdrawalsResBean.getTransStatus())) {
                        //提现成功
                        iReturnMoneyOutDao.updateSuccess(moneyRecord.getId());
                        sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                                .concat("元的提现成功处理，手续费").concat(moneyRecord.getFee().toString()).concat("元，流水号：")
                                .concat(moneyRecord.getSerialNo()));
                        record[1]++;
                    } else {
                        if("72".equals(withdrawalsResBean.getRespCode())){
                            //超时
                            iReturnMoneyOutDao.updateTimeOut(moneyRecord.getId());
                            record[3]++;
                        }else{
                            iReturnMoneyOutDao.updateFailure(moneyRecord.getId());
                            returnMoney2User(moneyRecord);
                            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                                    .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
                            record[2]++;
                        }
                    }
                }
            }
        }

        return "成功处理".concat(record[1]+"笔提现！"+"提现失败"+failCount[2]+"笔！"+"提现超时"+timeOutCount[3]+"笔！");
    }

    /**
     * 主动定频查询提现结果
     * 不开事务
     * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
     */
    //@Scheduled(initialDelay = 30000, fixedRate = 30000)
    public void checkOutResult(){
        List<ReturnMoneyOutBean> list = iReturnMoneyOutDao.findNoResultCheckOutMoney();
        if(ProjectConstants.PRO_DEBUG || CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(returnMoneyOutBean -> {
            WithdrawalsResBean withdrawalsResBean = withdrawalsService.selWithdrawalsResult(returnMoneyOutBean.getSerialNo());
            if("00".equals(withdrawalsResBean.getRespCode()) && "2".equals(withdrawalsResBean.getTransStatus())){
                // 成功
                iReturnMoneyOutDao.updateSuccess(returnMoneyOutBean.getId());
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的佣金提现成功处理，手续费").concat(returnMoneyOutBean.getFee().toString()).concat("元，流水号：")
                        .concat(returnMoneyOutBean.getSerialNo()));
            }else{
                iReturnMoneyOutDao.updateFailure(returnMoneyOutBean.getId());
                returnMoney2User(returnMoneyOutBean);
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的佣金提现处理失败，流水号：").concat(returnMoneyOutBean.getSerialNo()));
            }
        });
    }

    public String reviewPass(String ids){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        String batchOut = batchOut(idArr);
        return batchOut;
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

    /**
     * 提现失败，回滚金额
     * @param returnMoneyOutBean
     */
    private void returnMoney2User(ReturnMoneyOutBean returnMoneyOutBean){
        try{
            // 失败
            //iReturnMoneyOutDao.updateFailure(returnMoneyOutBean.getId());
            // 处理失败，退钱给用户
            UserBean user = iReturnMoneyOutDao.queryUserMoney(returnMoneyOutBean.getUserId());
            ReturnMoney2User returnMoney2User = new ReturnMoney2User();
            returnMoney2User.setId(returnMoneyOutBean.getUserId());
            // 提现金额+手续费
            returnMoney2User.setDifMoney(returnMoneyOutBean.getMoney().add(returnMoneyOutBean.getFee()));
            returnMoney2User.setMoney(user.getReturnMoney());
            iReturnMoneyOutDao.returnMoney2User(returnMoney2User);
//            if(iReturnMoneyOutDao.returnMoney2User(returnMoney2User) == 1){
//                MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
//                moneyChangeBean.setId(Utils.uuid());
//                moneyChangeBean.setUserId(returnMoney2User.getId());
//                moneyChangeBean.setBeforeMoney(returnMoney2User.getMoney());
//                moneyChangeBean.setDifMoney(returnMoney2User.getDifMoney());
//                moneyChangeBean.setAfterMoney(returnMoney2User.getMoney().add(returnMoney2User.getDifMoney()));
//                // 类型 0 充值 1 提现 2 建仓 3 平仓 4 提现失败退款
//                moneyChangeBean.setType(4);
//                moneyChangeBean.setRemark("易支付提现失败");
//
//                iReturnMoneyOutDao.addMoneyChange(moneyChangeBean);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
