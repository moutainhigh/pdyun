package com.rmkj.microcap.common.modules.pay.mingfu.service;/**
 * Created by Administrator on 2018/1/17.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.mingfu.bean.MingFuNotifyBean;
import com.rmkj.microcap.common.modules.pay.mingfu.bean.MingFuQuickBean;
import com.rmkj.microcap.common.modules.pay.mingfu.http.MingFuPayApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author k
 * @create -01-17-18:10
 **/
@Service
public class MingFuPayService {
    private final Logger Log = Logger.getLogger(getClass());

    @HttpService
    private MingFuPayApi mingFuPayApi;


    @Value("${mingfu_pay_mer_no}")
    private String mingfu_mer_no;

    @Value("${mingfu_pay_key}")
    private String mingfu_pay_key;

    @Value("${mingfu_pay_notify_url}")
    private String mingfu_pay_notify_url;

    @Value("${mingfu_pay_return_url}")
    private String mingfu_pay_return_url;

    public boolean mingFuQuickMsgSub(MoneyRecord moneyRecord, String msgCode){
        try {
            JSONObject json = (JSONObject) JSONObject.parse(msgCode);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("merCode", mingfu_mer_no);
            parameter.put("tranTime", DateUtils.getDate("yyyyMMddHHmmss"));
            parameter.put("tranNo", DateUtils.getDate("yyyyMMddHHmmss"));
            parameter.put("smsCode", json.get("msg_no"));
            parameter.put("orgTranNo", moneyRecord.getSerialNo());
            parameter.put("sign", sign(parameter));
            Response<String> execute = mingFuPayApi.quickPayConfirm(parameter).execute();
            String body = execute.body();
            Log.info("明付短信返回:".concat(body));
            JSONObject resultJson = (JSONObject) JSONObject.parse(body);
            if(resultJson.get("respCode").equals("0")){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 快捷请求支付
     * @param money
     * @param mingFuQuickBean
     * @return
     */
    public JSONObject mingFuQuickPay(MoneyRecord money, MingFuQuickBean mingFuQuickBean){
        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("merCode", mingfu_mer_no);
            parameter.put("tranTime", DateUtils.getDate("yyyyMMddHHmmss"));
            parameter.put("tranNo", money.getSerialNo());
            parameter.put("settleType", "0");
            parameter.put("tranAmt", money.getMoney().multiply(new BigDecimal(100)).toString());
            parameter.put("subject", "中赢國際");
            parameter.put("orderDesc", "中赢國際");
            parameter.put("payMobile", mingFuQuickBean.getPhone_no());
            parameter.put("payAcctNo", mingFuQuickBean.getCard_no());
            parameter.put("payAcctName", mingFuQuickBean.getCard_name());
            parameter.put("noticeUrl", mingfu_pay_notify_url);
            parameter.put("cardNo", mingFuQuickBean.getId_no());
            parameter.put("sign", sign(parameter));
            Response<String> execute = mingFuPayApi.mingfuQuickPay(parameter).execute();
            String body = execute.body();
            Log.info("明付快捷支付请求返回:".concat(body));
            JSONObject jsonMsg = (JSONObject) JSONObject.parse(body);
            if(jsonMsg.get("respCode").equals("2")){
                return jsonMsg;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 明付网关支付
     * @param moneyRecord
     * @return
     */
    public Map<String, Object> mingFuPay(MoneyRecord moneyRecord){
        try {
            Map<String, Object> parameter = new HashMap();
            parameter.put("merCode", mingfu_mer_no);
            parameter.put("tranTime", DateUtils.getDate("yyyyMMddHHmmss"));
            parameter.put("tranNo", moneyRecord.getSerialNo());
            parameter.put("settleType", "1");
            parameter.put("tranAmt", moneyRecord.getMoney().multiply(new BigDecimal(100)).toString());
            parameter.put("noticeUrl", mingfu_pay_notify_url);
            parameter.put("frontUrl", mingfu_pay_return_url);
            parameter.put("subject", "中赢國際");
            parameter.put("orderDesc", "中赢國際");
            parameter.put("sign", sign(parameter));
            Log.info("明付银联支付请求参数:".concat(JSON.toJSONString(parameter)));
            return parameter;
        }catch (Exception e){
            throw  new RuntimeException("支付故障请联系管理员");
        }
    }


    @Autowired
    NotifyInterface notifyInterface;

    /**
     * 支付回调
     * @param message
     * @return
     */
    public ResponseEntity callback(String message){
        Log.info("明付支付回调:".concat(message));

        MingFuNotifyBean mingFuNotifyBean = JSON.parseObject(message, MingFuNotifyBean.class);
        String sign = mingFuNotifyBean.getSign();
        Map<String, Object> stringObjectMap = beanToMap(mingFuNotifyBean);
        stringObjectMap.remove("sign");
        String localSign = sign(stringObjectMap);
        if(sign.equals(localSign)){
            notifyInterface.success(mingFuNotifyBean.getTranNo(), "");
            return ResponseEntity.ok("000000");
        }else{
            Log.info("明付支付验签失败(原-本地):".concat(sign).concat(localSign));
        }
        return ResponseEntity.ok("fail");
    }

    public String sign(Map<String, Object> parameter){
        Set<String> set = parameter.keySet();
        List<String> list = new ArrayList(set);
        Collections.sort(list);

        StringBuffer signStr = new StringBuffer();
        for (String key : list) {
            signStr.append(key).append("=").append(parameter.get(key)).append("&");
        }
        String signText = signStr.substring(0, signStr.length() - 1) + mingfu_pay_key;
        Log.info("明付签名字符串:".concat(signText));
        return DigestUtils.md5Hex(signText);
    }

    /**
     * 将javabean实体类转为map类型，然后返回一个map类型的值
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
