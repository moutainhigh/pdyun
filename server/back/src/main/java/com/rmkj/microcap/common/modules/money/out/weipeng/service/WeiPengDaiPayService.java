package com.rmkj.microcap.common.modules.money.out.weipeng.service;/**
 * Created by Administrator on 2017/3/7.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.BankCodeBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPayAsyncResultBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.dao.BankCodeDao;
import com.rmkj.microcap.common.modules.money.out.weipeng.http.WeiPengApi;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * TODO 威鹏代付
 * @author k
 * @create -03-07-11:46
 **/
@Service
public class WeiPengDaiPayService {

    private final static Logger logger = Logger.getLogger(WeiPengDaiPayService.class);

    @HttpService
    private WeiPengApi weiPengApi;

    @Autowired
    private BankCodeDao bankCodeDao;

    @Autowired
    private IMoneyRecordDao moneyRecordDao;

    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private IReturnMoneyOutDao returnMoneyOutDao;

    @Autowired
    private IUserDao userDao;

    /**
     * 威鹏代付佣金成功返回异步通知结果，进行修改提现状态
     * @param weiPengDaiPayAsyncResultBean
     * @return
     */
    public String getDaiPayAsyncResultReturnMoney(WeiPengDaiPayAsyncResultBean weiPengDaiPayAsyncResultBean){
        if(null == weiPengDaiPayAsyncResultBean){
            return "fail";
        }
        logger.info("异步通知结果：" + JSON.toJSONString(weiPengDaiPayAsyncResultBean));
        //验证Sign是否正确
        StringBuffer signStr = new StringBuffer();
        signStr.append(ProjectConstants.WEIPENG_PAY_MERCHANT_NO).append(weiPengDaiPayAsyncResultBean.getTrade_no()).append(weiPengDaiPayAsyncResultBean.getPay_num())
                .append(weiPengDaiPayAsyncResultBean.getTotal_fee()).append(ProjectConstants.WEIPENG_PAY_SECRET);
        String sign = Utils.md5(signStr.toString()).toUpperCase();
        if(!sign.equals(weiPengDaiPayAsyncResultBean.getSign())){
            return "fail";
        }

        ReturnMoneyOutBean returnMoneyOutBean = returnMoneyOutDao.getReturnMoneyByserialNo(weiPengDaiPayAsyncResultBean.getPay_num());
        if(null == returnMoneyOutBean){
            return "success";
        }

        ReturnMoneyOutBean returnMoney = new ReturnMoneyOutBean();
        returnMoney.setSerialNo(weiPengDaiPayAsyncResultBean.getPay_num());//商户订单号
        returnMoney.setThirdSerialNo(weiPengDaiPayAsyncResultBean.getTrade_no());
        returnMoney.setStatus(1);
        returnMoney.setRemark(weiPengDaiPayAsyncResultBean.getMessage());
        if("success".equals(weiPengDaiPayAsyncResultBean.getTrade_result())){
            String status = "成功";
            if(returnMoneyOutDao.updateDaiPayOutPastInStatus(returnMoney) == 1){
//                weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, recordBean.getUserId(), recordBean.getMoney().toString(), status, WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"));
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您的提现申请已通过,手续费".concat(returnMoneyOutBean.getFee().toString()).concat("元，实际到账").concat(returnMoneyOutBean.getMoney().toString()).concat("元"));
                return "success";
            }
        }
        return "fail";
    }

    /**
     * TODO 威鹏代付成功返回异步通知结果，进行修改提现状态
     * @param weiPengDaiPayAsyncResultBean
     * @return
     */
    public String getWeiPengDaiPayAsyncResult(WeiPengDaiPayAsyncResultBean weiPengDaiPayAsyncResultBean){
        if(null == weiPengDaiPayAsyncResultBean){
            return "fail";
        }
        logger.info("异步通知结果：" + JSON.toJSONString(weiPengDaiPayAsyncResultBean));
        MoneyRecordBean recordBean = moneyRecordDao.getDaiPayByserialNo(weiPengDaiPayAsyncResultBean.getPay_num());
        if(null == recordBean){
            return "success";
        }
        if("success".equals(weiPengDaiPayAsyncResultBean.getTrade_result())){
            String status = "成功";
            MoneyRecordBean moneyRecordBean = new MoneyRecordBean();
            moneyRecordBean.setSerialNo(weiPengDaiPayAsyncResultBean.getPay_num());//商户订单号
            moneyRecordBean.setThirdSerialNo(weiPengDaiPayAsyncResultBean.getTrade_no());
            moneyRecordBean.setStatus(1);
            if(moneyRecordDao.updateDaiPayOutPastInStatus(moneyRecordBean) == 1){
                //weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, recordBean.getUserId(), recordBean.getMoney().toString(), status, WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"));
                return "success";
            }
        }
        return "fail";
    }


    /**
     * TODO 威鹏代付出金，请求，返回结果
     * @param moneyRecordBean
     * @return
     */
    public synchronized String getWeiPengDaiPayResponse(MoneyRecordBean moneyRecordBean){
        Map<String,String> result = WeiPengDaiPayInfoResult(moneyRecordBean);
        if(null == result){
            logger.error("出金支行信息错误！");
            Map<String, String> map = new HashedMap();
            map.put("return_code", "自生成");
            map.put("orderid", "无");
            map.put("message", "出金支行信息错误");
            return JSON.toJSONString(map);
        }
        logger.info("威鹏出金请求参数：" + JSON.toJSONString(result));
        try {
            Response<String> execut = weiPengApi.getWeiPengDaiPayInfo(result).execute();
            if(execut.isSuccessful()){
                if(execut.body() instanceof String == false){
                    logger.info("返回的不是String(下面是记录的返回结果)：".concat(moneyRecordBean.getSerialNo()));
                    logger.info(execut.body());
                    return null;
                }

                String info = JSON.toJSONString(execut.body());
                logger.info("威鹏出金请求响应body_JSON：" + info);
                logger.info("不转换json:" + execut.body());
                return execut.body();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }


    /**
     * 返回威鹏代付请求时所需的参数
     * @param moneyRecordBean
     * @return
     */
    public Map<String, String> WeiPengDaiPayInfoResult(MoneyRecordBean moneyRecordBean){
        //根据支行名称，查询支行有关信息
        BankCodeBean bankCode = bankCodeDao.findBankCodeByName(moneyRecordBean.getOpenBankName());
        if(null != bankCode) {
            String merchant_no = ProjectConstants.WEIPENG_PAY_MERCHANT_NO;//商户号，爱贝尔分配
            String accno = moneyRecordBean.getBankAccount(); //银行卡号
            String amount = WeiPengUtils.fromYuanToFen(moneyRecordBean.getMoney().toString());  //金额分
            String bank = moneyRecordBean.getBankName(); //银行
            String subbank = moneyRecordBean.getOpenBankName(); //支行名
            String bankno = bankCode.getBankNo();//联行号
            String accname = moneyRecordBean.getChnName(); //姓名
            String qsbankcode = bankCode.getSettQsBankCode();   //清算号码
            String areacode = bankCode.getSettAreaCode(); //地区码
            String key = ProjectConstants.WEIPENG_PAY_SECRET; //爱贝尔分配，用户提交 echk4gr0gd0y8wug
            //商户自己订单号	pay_num
            String pay_num = moneyRecordBean.getSerialNo();
            //异步通知地址	notifyurl
            String notifyurl = ProjectConstants.WEIPENG_DAI_PAY_ASYNCRESULT_URL;
            //拼接签名
            StringBuffer sign = new StringBuffer();
            sign.append(merchant_no).append(amount).append(WeiPengUtils.getNowTime("yyyMMdd")).append(key);

//            String params = "merchant_no=" + merchant_no + "&amount="
//                    + amount + "&bank=" + bank + "&subbank="+ subbank
//                    +"&bankno="+bankno+"&accname="+accname
//                    +"&qsbankcode="+qsbankcode+"&areacode="+areacode
//                    +"&sign="+sign+"&accno="+accno;

            Map<String, String> result = new HashedMap();
            result.put("merchant_no", merchant_no);
            result.put("amount", amount);
            result.put("bank", bank);
            result.put("subbank", subbank);
            result.put("bankno", bankno);
            result.put("accname", accname);
            result.put("qsbankcode", qsbankcode);
            result.put("areacode", areacode);
            result.put("pay_num", pay_num);
            result.put("notifyurl", notifyurl);
            //新添参数开户行所在省、市
            result.put("province", moneyRecordBean.getProvince());
            result.put("city", moneyRecordBean.getCity());
            result.put("sign", WeiPengUtils.getMD5(sign.toString()));
            result.put("accno", accno);
            return result;
        }
        return null;
    }

    /**
     * 威鹏代付出金，请求，返回结果--佣金代付
     * @param returnMoneyOutBean
     * @return
     */
    public synchronized String getWeiPengDaiPayResponse(ReturnMoneyOutBean returnMoneyOutBean){
        Map<String,String> result = WeiPengDaiPayInfoResult(returnMoneyOutBean);
        if(null == result){
            logger.error("出金支行信息错误！");
            Map<String, String> map = new HashedMap();
            map.put("return_code", "自生成");
            map.put("orderid", "无");
            map.put("message", "出金支行信息错误");
            return JSON.toJSONString(map);
        }
        logger.info("威鹏出金请求参数：" + JSON.toJSONString(result));
        try {
            Response<String> execut = weiPengApi.getWeiPengDaiPayInfo(result).execute();
            if(execut.isSuccessful()){
                String info = execut.body();
                return info;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回威鹏代付请求时所需的参数
     * @param returnMoneyOutBean
     * @return
     */
    public Map<String, String> WeiPengDaiPayInfoResult(ReturnMoneyOutBean returnMoneyOutBean){
        //根据支行名称，查询支行有关信息
        BankCodeBean bankCode = bankCodeDao.findBankCodeByName(returnMoneyOutBean.getOpenBankName());
        if(null != bankCode) {
            String merchant_no = ProjectConstants.WEIPENG_PAY_MERCHANT_NO;//商户号，爱贝尔分配
            String accno = returnMoneyOutBean.getBankAccount(); //银行卡号
            String amount = WeiPengUtils.fromYuanToFen(returnMoneyOutBean.getMoney().toString());  //金额分
            String bank = returnMoneyOutBean.getBankName(); //银行
            String subbank = returnMoneyOutBean.getOpenBankName(); //支行名
            String bankno = bankCode.getBankNo();//联行号
            String accname = returnMoneyOutBean.getChnName(); //姓名
            String qsbankcode = bankCode.getSettQsBankCode();   //清算号码
            String areacode = bankCode.getSettAreaCode(); //地区码
            String key = ProjectConstants.WEIPENG_PAY_SECRET; //爱贝尔分配，用户提交 echk4gr0gd0y8wug
            //商户自己订单号	pay_num
            String pay_num = returnMoneyOutBean.getSerialNo();
            //佣金提现异步通知地址	notifyurl
            String notifyurl = ProjectConstants.WEIPENG_DAI_PAY_ASYNCRESULT_RETURNMONEY_URL;
            //拼接签名
            StringBuffer sign = new StringBuffer();
            sign.append(merchant_no).append(amount).append(WeiPengUtils.getNowTime("yyyMMdd")).append(key);

//            String params = "merchant_no=" + merchant_no + "&amount="
//                    + amount + "&bank=" + bank + "&subbank="+ subbank
//                    +"&bankno="+bankno+"&accname="+accname
//                    +"&qsbankcode="+qsbankcode+"&areacode="+areacode
//                    +"&sign="+sign+"&accno="+accno;

            Map<String, String> result = new HashedMap();
            result.put("merchant_no", merchant_no);
            result.put("amount", amount);
            result.put("bank", bank);
            result.put("subbank", subbank);
            result.put("bankno", bankno);
            result.put("accname", accname);
            result.put("qsbankcode", qsbankcode);
            result.put("areacode", areacode);
            result.put("pay_num", pay_num);
            result.put("province", returnMoneyOutBean.getProvince());
            result.put("city", returnMoneyOutBean.getCity());
            result.put("notifyurl", notifyurl);
            result.put("sign", WeiPengUtils.getMD5(sign.toString()));
            result.put("accno", accno);
            return result;
        }
        return null;
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
