package com.rmkj.microcap.common.modules.pay.rongya.service;/**
 * Created by Administrator on 2017/12/29.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.rongya.bean.RongYaNotifyBean;
import com.rmkj.microcap.common.modules.pay.rongya.http.RongYaPayApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author k
 * @create -12-29-11:41
 **/
@Service
public class RongYaPayService {

    private final Logger Log = Logger.getLogger(RongYaPayService.class);

    @HttpService
    private RongYaPayApi rongYaPayApi;
    @Autowired
    NotifyInterface notifyInterface;

    public ResponseEntity callback(RongYaNotifyBean entity){
        Log.info("融雅支付回调通知:".concat(JSON.toJSONString(entity)));

        StringBuffer signStr = new StringBuffer();
        signStr.append("orderid=").append(entity.getOrderid())
                .append("&opstate=").append(entity.getOpstate())
                .append("&ovalue=").append(entity.getOvalue()).append(ProjectConstants.RONGYA_PAY_KEY);
        String sign = DigestUtils.md5Hex(signStr.toString()).toLowerCase();
        //验证签名
        if(sign.equals(entity.getSign())){
            if("0".equals(entity.getOpstate())){
                notifyInterface.success(entity.getOrderid(), entity.getSysorderid());
                return ResponseEntity.ok("success");
            }
        }else{
            Log.error("验签失败：" + sign + " " + entity.getSign());
        }

        return ResponseEntity.ok("failure");
    }

    /**
     * 构造支付请求参数
     * @param moneyRecord
     * @param payType
     * @return
     */
    public Map<String, Object> buildParameter(MoneyRecord moneyRecord, String payType){
        Map<String, Object> parameter = new HashMap();
        parameter.put("pay_version", "vb1.0");
        parameter.put("pay_memberid", ProjectConstants.RONGYA_PAY_MEMBERID);
        parameter.put("pay_orderid", moneyRecord.getSerialNo());
        parameter.put("pay_applydate", DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
        parameter.put("pay_bankcode", payType);
        parameter.put("pay_notifyurl", ProjectConstants.RONGYA_PAY_NOTIFY_URL);
        parameter.put("pay_amount", moneyRecord.getMoney().toString());
        parameter.put("pay_md5sign", requestSign(parameter));
        parameter.put("pay_productname", "中赢國際");

        Log.info("融雅支付请求:".concat(JSON.toJSONString(parameter)));
        return parameter;
    }

    /**
     * 请求支付参数签名
     * @param parameter
     * @return
     */
    public String requestSign(Map<String, Object> parameter){
        StringBuffer sign = new StringBuffer();
        sign.append("pay_memberid=").append(parameter.get("pay_memberid"))
                .append("&pay_bankcode=").append(parameter.get("pay_bankcode"))
                .append("&pay_amount=").append(parameter.get("pay_amount"))
                .append("&pay_orderid=").append(parameter.get("pay_orderid"))
                .append("&pay_notifyurl=").append(parameter.get("pay_notifyurl")).append(ProjectConstants.RONGYA_PAY_KEY);
        return DigestUtils.md5Hex(sign.toString()).toLowerCase();

    }
}
