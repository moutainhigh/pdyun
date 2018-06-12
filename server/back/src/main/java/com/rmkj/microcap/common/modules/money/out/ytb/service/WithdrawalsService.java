package com.rmkj.microcap.common.modules.money.out.ytb.service;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.BalanceBean;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.WithdrawalsResBean;
import com.rmkj.microcap.common.modules.money.out.ytb.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.parameterset.dao.IParameterSetDao;
import com.rmkj.microcap.modules.parameterset.entity.ParameterSetBean;
import com.rmkj.microcap.modules.parameterset.service.ParameterSetService;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/1/17.
 */
@Service
public class WithdrawalsService {

    private static final Logger Log = Logger.getLogger(WithdrawalsService.class);

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IParameterSetDao iParameterSetDao;

    /**
     * ytb代付 查询商户余额
     */
    public BalanceBean selBalance(){
        Map<String, String> param = Utils.buildRequestParam(false);
        param.put("cardno", ProjectConstants.MONEY_OUT_YTB_CARDNO);
        Map<String, String> reqParam = new HashMap<String, String>();
        Set<String> keySet = param.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            reqParam.put(key, param.get(key));
        }
        try {
            reqParam.put("signature",
                    Utils.signature(reqParam, "key=".concat(ProjectConstants.MONEY_OUT_YTB_SECRET)));
            String url = Utils.buildUrl("balance.do", reqParam);
            String balance = Utils.receiveBySend(url, "GBK");
            Log.info("查询商户余额".concat(balance));
            BalanceBean balanceBean = JSONObject.parseObject(balance,BalanceBean.class);
            return balanceBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ytb代付 查询代付结果
     */
    public WithdrawalsResBean selWithdrawalsResult(String traceno){
        Map<String, String> param = Utils.buildRequestParam(false);
        param.put("cardno", ProjectConstants.MONEY_OUT_YTB_CARDNO);
        param.put("traceno",traceno);
        Map<String, String> reqParam = new HashMap<String, String>();
        Set<String> keySet = param.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            reqParam.put(key, param.get(key));
        }
        try {
            reqParam.put("signature",
                    Utils.signature(reqParam, "key=".concat(ProjectConstants.MONEY_OUT_YTB_SECRET)));
            String url = Utils.buildUrl("virtOrder.do", reqParam);
            String withdrawals = Utils.receiveBySend(url, "GBK");
            WithdrawalsResBean withdrawalsResBean = JSONObject.parseObject(withdrawals,WithdrawalsResBean.class);
            return withdrawalsResBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ytb代付 实时代付
     */
    public WithdrawalsResBean withdrawals(MoneyRecordForOutBean moneyRecordForOutBean){
        Map<String, String> param = Utils.buildRequestParam(false);
        param.put("cardno", ProjectConstants.MONEY_OUT_YTB_CARDNO);
        param.put("traceno",moneyRecordForOutBean.getSerialNo());
        BigDecimal money = moneyRecordForOutBean.getMoney();
        //美元兑换成人民币
        param.put("amount",money.toString());
        param.put("accountno",moneyRecordForOutBean.getBankAccount());
        param.put("accountName",moneyRecordForOutBean.getChnName());
        UserBean userBean = userDao.get(moneyRecordForOutBean.getUserId());
        param.put("mobile",userBean.getMobile());
        if(StringUtils.isEmpty(userBean.getIdCard())){
            param.put("certno","001");
        }else{
            param.put("certno",userBean.getIdCard());
        }
        param.put("bankno",moneyRecordForOutBean.getLianHangNo());
        param.put("bankName",moneyRecordForOutBean.getOpenBankName());
        param.put("bankType",moneyRecordForOutBean.getBankName());
        param.put("remark","实时提现");
        Map<String, String> reqParam = new HashMap<String, String>();
        Set<String> keySet = param.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            reqParam.put(key, param.get(key));
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("cardno" + "=" + ProjectConstants.MONEY_OUT_YTB_CARDNO).append("&").append("traceno"+"="+moneyRecordForOutBean.getSerialNo()).append("&").append("amount"+"="+param.get("amount")).append("&")
                    .append("accountno"+"="+moneyRecordForOutBean.getBankAccount()).append("&").append("mobile"+"="+userBean.getMobile()).append("&").append("bankno"+"="+moneyRecordForOutBean.getLianHangNo()).append("&").append("key=").append(ProjectConstants.MONEY_OUT_YTB_SECRET);
            String sign = sb.toString();
            String result = DigestUtils.md5Hex(sign.getBytes("GBK")).toUpperCase();
            reqParam.put("signature", result);
            String params = Utils.buildParams(reqParam);
            String withdrawals = Utils.receiveBySendPost("http://settle.juhuibaopay.com/virtPay.do", "GBK",params);
            WithdrawalsResBean withdrawalsResBean = JSONObject.parseObject(withdrawals,WithdrawalsResBean.class);
            return withdrawalsResBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * ytb代付 佣金实时代付
     */
    public WithdrawalsResBean withdrawals(ReturnMoneyOutBean moneyRecordForOutBean){
        Map<String, String> param = Utils.buildRequestParam(false);
        param.put("cardno", ProjectConstants.MONEY_OUT_YTB_CARDNO);
        param.put("traceno",moneyRecordForOutBean.getSerialNo());
        BigDecimal money = moneyRecordForOutBean.getMoney();
        param.put("amount",money.toString());
        param.put("accountno",moneyRecordForOutBean.getBankAccount());
        param.put("accountName",moneyRecordForOutBean.getChnName());
        UserBean userBean = userDao.get(moneyRecordForOutBean.getUserId());
        param.put("mobile",userBean.getMobile());
        //如果身份证为空，赋值默认值
        if(StringUtils.isEmpty(userBean.getIdCard())){
            param.put("certno","001");
        }else{
            param.put("certno",userBean.getIdCard());
        }
        param.put("bankno",moneyRecordForOutBean.getLianHangNo());
        param.put("bankName",moneyRecordForOutBean.getOpenBankName());
        param.put("bankType",moneyRecordForOutBean.getBankName());
        param.put("remark","实时提现");
        Map<String, String> reqParam = new HashMap<String, String>();
        Set<String> keySet = param.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            reqParam.put(key, param.get(key));
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("cardno" + "=" + ProjectConstants.MONEY_OUT_YTB_CARDNO).append("&").append("traceno"+"="+moneyRecordForOutBean.getSerialNo()).append("&").append("amount"+"="+param.get("amount")).append("&")
                    .append("accountno"+"="+moneyRecordForOutBean.getBankAccount()).append("&").append("mobile"+"="+userBean.getMobile()).append("&").append("bankno"+"="+moneyRecordForOutBean.getLianHangNo()).append("&").append("key=").append(ProjectConstants.MONEY_OUT_YTB_SECRET);
            String sign = sb.toString();
            String result = DigestUtils.md5Hex(sign.getBytes("GBK")).toUpperCase();
            reqParam.put("signature", result);
            String params = Utils.buildParams(reqParam);
            String withdrawals = Utils.receiveBySendPost(ProjectConstants.MONEY_OUT_YTB_BASE_URL.concat("virtPay.do"), "GBK",params);
            WithdrawalsResBean withdrawalsResBean = JSONObject.parseObject(withdrawals,WithdrawalsResBean.class);
            return withdrawalsResBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
