package com.rmkj.microcap.common.modules.money.out.crown.service;/**
 * Created by Administrator on 2017/11/21.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownNotifyBean;
import com.rmkj.microcap.common.modules.money.out.crown.http.CrownPayApi;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.MD5Utils;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.service.CrownService;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author k
 * @create -11-21-14:06
 **/
@Service
public class CrownPayService {

    private static Logger logger = Logger.getLogger(CrownPayService.class);

    @HttpService
    private CrownPayApi crownPayApi;

    @Autowired
    private CrownService crownService;

    @Autowired
    private com.rmkj.microcap.modules.ReturnMoneyOut.service.CrownService returnCrownService;


    public String crownMoneyOut(MoneyRecordForOutBean entity, String outType){
        Map<String, Object> parameter = buildParameter(entity, outType);
        try {
            Response<String> execute = crownPayApi.crownMoneyout(parameter).execute();
            String body = execute.body();
            if(null != body){
                logger.info("皇冠代付请求返回信息:".concat(body));
                return body;
            }else{
                logger.info("皇冠代付请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 出金构造参数
     * @param entity 实体
     * @param outType 1:提现出金 2:返佣出金
     * @return
     */
    public Map<String, Object> buildParameter(MoneyRecordForOutBean entity, String outType){
        Map<String, Object> parameter = new HashedMap();
        try {
            parameter.put("merId", ProjectConstants.CROWN_MER_ID);
            parameter.put("merOrderId", entity.getSerialNo());
            parameter.put("acctName", entity.getChnName());
            parameter.put("acctId", entity.getBankAccount());
            parameter.put("mobile", "12345678910");
            parameter.put("bizType", "6");
            parameter.put("bankCode", "1"); //银行代码
            parameter.put("bankName", entity.getOpenBankName());
            parameter.put("bankCity", entity.getCity());
            parameter.put("txnAmt", entity.getMoney().multiply(new BigDecimal(100)).toString());
            parameter.put("currency", "CNY");
            parameter.put("subject", "美亚商城代付");
            if("1".equals(outType)){
                parameter.put("notifyUrl", ProjectConstants.CROWN_NOTIFY_URL);
            }else{
                parameter.put("notifyUrl", ProjectConstants.CROWN_NOTIFY_RETURN_MONEY_URL);
            }
            parameter.put("sendIp", "11");
            parameter.put("txnTime", WeiPengUtils.getNowTime("yyyyMMddHHmmss"));
            parameter.put("signature", signature(parameter, ProjectConstants.CROWN_KEY, "UTF-8"));
            parameter.put("signMethod", "MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("皇冠请求代付信息:".concat(JSON.toJSONString(parameter)));
        return parameter;
    }


    /**
     * 皇冠代付订单，支付成功处理
     * @param entity
     * @return
     */
    public ResponseEntity crownPayNotify(CrownNotifyBean entity){
        try {
            logger.info("皇冠代付异步通知信息:".concat(JSON.toJSONString(entity)));

            if(!checkSignature(entity)){
                return new ResponseEntity("fail", HttpStatus.OK);
            }else if(!crownService.crownPayNotify(entity)){
                return new ResponseEntity("fail", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity("success", HttpStatus.OK);

    }

    /**
     * 皇冠代付，返佣提现异步通知处理
     * @param entity
     * @return
     */
    public ResponseEntity crownReturnMoneyNotify(CrownNotifyBean entity){
        try {
            logger.info("皇冠代付返佣提现异步通知信息:".concat(JSON.toJSONString(entity)));

            if(!checkSignature(entity)){
                return new ResponseEntity("fail", HttpStatus.OK);
            }else if(!returnCrownService.crownReturnMoneyNotify(entity)){
                return new ResponseEntity("fail", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity("success", HttpStatus.OK);
    }


    /**
     * 验签
     * @param entity
     * @return
     */
    public boolean checkSignature(CrownNotifyBean entity){
        try {
            //验证签名是否正确
            String sign = entity.getSignature();
            entity.setSignature(null);
            entity.setSignMethod(null);
            Map<String, Object> parameter = beanToMap(entity);
            String newSign = signature(parameter, ProjectConstants.CROWN_KEY, "UTF-8");
            if(!sign.equals(newSign)){
                logger.info("皇冠代付回调签名失败: 回调签名:".concat(sign).concat("本地签名:").concat(newSign));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 产生签名
     * @param param
     * @param keyInfo
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String signature(Map<String, Object> param, String keyInfo, String encoding) throws Exception {
        Set<String> set = param.keySet();
        List<String> keys = new ArrayList<String>(set);
        Collections.sort(keys);
        boolean start = true;
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        for (String key : keys) {
            String value = (String) param.get(key);
            if (!StringUtils.isEmpty(value) && !key.equals("signature")) {
                if (!start) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
                start = false;
            }
        }
        //sb.append("&");
        sb.append(keyInfo);
        String src = sb.toString();
        if (logger == null) {
            System.out.println("签名数据:" + src);
        } else {
            logger.info("签名数据:" + src);
        }
        return MD5Utils.md5(sb.toString()).toUpperCase();
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
