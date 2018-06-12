package com.rmkj.microcap.common.modules.pay.zhinengcloud.service;/**
 * Created by Administrator on 2017/12/15.
 */

import com.alibaba.fastjson.JSON;
import com.capinfo.crypt.Md5;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.zhinengcloud.entity.ZhiNengNotifyBean;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author k
 * @create -12-15-16:04
 **/
@Service
public class ZhiNengCloudPayService {
    private final Logger Log = Logger.getLogger(ZhiNengCloudPayService.class);

    @Autowired
    NotifyInterface notifyInterface;

    public ResponseEntity callBack(ZhiNengNotifyBean zhiNengNotifyBean){
        Log.info("智能云支付异步回调:".concat(JSON.toJSONString(zhiNengNotifyBean)));

        StringBuffer signStr = new StringBuffer();
        signStr.append(zhiNengNotifyBean.getOrderid())
                .append(zhiNengNotifyBean.getOrderuid())
                .append(zhiNengNotifyBean.getP_id())
                .append(zhiNengNotifyBean.getPrice())
                .append(zhiNengNotifyBean.getRealprice()).append(ProjectConstants.ZHINENG_CLOUD_TOKEN);
        String sigature = DigestUtils.md5Hex(signStr.toString());
        if(sigature.equals(zhiNengNotifyBean.getKey())){
            notifyInterface.success(zhiNengNotifyBean.getOrderid(), zhiNengNotifyBean.getP_id());
            return ResponseEntity.ok("success");
        }else{
            Log.error("验签失败：" + sigature + " " + zhiNengNotifyBean.getKey());
        }
        return ResponseEntity.ok("failure");
    }


    /**
     * 构造支付参数
     * @param parameter
     * @param moneyRecord
     * @param payType 1：支付宝；2：微信支付
     */
    public void buildParamter(Map<String, String> parameter, MoneyRecord moneyRecord, String payType){
        parameter.put("uid", ProjectConstants.ZHINENG_CLOUD_UID);
        parameter.put("price", moneyRecord.getMoney().toString());
        parameter.put("istype", payType);
        parameter.put("notify_url", ProjectConstants.ZHINENG_CLOUD_NOTIFY_URL);
        parameter.put("return_url", ProjectConstants.ZHINENG_CLOUD_RETURN_URL);
        parameter.put("orderid", moneyRecord.getSerialNo());
        parameter.put("orderuid", moneyRecord.getUserId());
        parameter.put("goodsname", moneyRecord.getChannel());
        parameter.put("token", ProjectConstants.ZHINENG_CLOUD_TOKEN);
    }

    public void signature(Map<String, String> parameter){
        Set<String> set = parameter.keySet();
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++){
            buffer.append(parameter.get(list.get(i)));
        }

        parameter.put("key", DigestUtils.md5Hex(buffer.toString()));
    }
}
