package com.rmkj.microcap.common.modules.pay.weifutong;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.pay.weifutong.bean.PayResultReqBean;
import com.rmkj.microcap.common.modules.pay.weifutong.bean.PrePayReqBean;
import com.rmkj.microcap.common.modules.pay.weifutong.bean.PrePayRespBean;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.weixin.XStreamTool;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.money.service.MoneyService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by renwp on 2016/12/12.
 */
@Service
public class WeiFuTongPayService {

    private final Logger Log = Logger.getLogger(WeiFuTongPayService.class);

    @HttpService
    private WeiFuTongPrePayApi weiFuTongPrePayApi;

    @Autowired
    private MoneyService moneyService;
    /**
     * 支付初始化
     * 商户号：'7551000001'
     * 密钥：'9d101c97133837e13dde2d32a5054abb'
     * @return
     */
    public PrePayRespBean payInit(String out_trade_no, String body, String openId, Integer total_fee, String mch_create_ip){
        PrePayReqBean prePayReqBean = new PrePayReqBean();
        prePayReqBean.setService("pay.weixin.jspay");
        prePayReqBean.setMch_id(ProjectConstants.WEI_FU_TONG_PAY_MCH_ID);
        prePayReqBean.setOut_trade_no(out_trade_no);
        prePayReqBean.setBody(body);
        prePayReqBean.setSub_openid(openId);
        prePayReqBean.setTotal_fee(total_fee);
        prePayReqBean.setMch_create_ip(mch_create_ip);
        prePayReqBean.setCallback_url(ProjectConstants.WEI_FU_TONG_PAY_FRONT_CALLBACK_URL);
        prePayReqBean.setNotify_url(ProjectConstants.WEI_FU_TONG_PAY_NOTIFY_URL);
        prePayReqBean.setNonce_str(Utils.uuid());

        //MD5签名
        prePayReqBean.setSign(Utils.md5(Utils.param(prePayReqBean).concat("&key=").concat(ProjectConstants.WEI_FU_TONG_PAY_KEY)).toUpperCase());

        PrePayRespBean prePayRespBean = null;
        String reqXml = XStreamTool.toXml(prePayReqBean, PrePayReqBean.class);
        Log.info(reqXml);
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            HttpPost httpPost = new HttpPost(ProjectConstants.WEI_FU_TONG_PAY_URL);
            StringEntity entityParams = new StringEntity(reqXml, "utf-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            if (response != null && response.getEntity() != null) {
                prePayRespBean = XStreamTool.toBean(new String(EntityUtils.toByteArray(response.getEntity()), "utf-8"), PrePayRespBean.class);
            }
//            Response<String> execute = weiFuTongPrePayApi.gateway(reqXml).execute();
//            if(execute.isSuccessful()){
//                Log.info(execute.body());
//                prePayRespBean = XStreamTool.toBean(execute.body(), PrePayRespBean.class);
//            }else{
//                prePayRespBean = new PrePayRespBean();
//                Log.error(execute.body());
//            }
        } catch (IOException e) {
            e.printStackTrace();

            Log.error(reqXml);
            prePayRespBean = new PrePayRespBean();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prePayRespBean;
    }

    public ResponseEntity notify(String xml) {
        Log.info(xml);
        PayResultReqBean payResultReqBean = XStreamTool.toBean(xml, PayResultReqBean.class);
//        if (!ValidatorUtils.valid(payResultReqBean)) {
//            return new ResponseEntity("fail", HttpStatus.OK);
//        }

        if(!payResultReqBean.success()){
            Log.error("威富通支付结果回调：".concat(xml));
        }else {
            // 校验签名是否正确
            String reqSign = payResultReqBean.getSign();
            payResultReqBean.setSign(null);
            String sign = Utils.md5(Utils.param(payResultReqBean).concat("&key=").concat(ProjectConstants.WEI_FU_TONG_PAY_KEY)).toUpperCase();

            if(!reqSign.equals(sign)){
                return new ResponseEntity("fail", HttpStatus.OK);
            }else if(!moneyService.payResultOfWeiFuTong(payResultReqBean)) {// 处理业务
                return new ResponseEntity("fail", HttpStatus.OK);
            }
        }

        return new ResponseEntity("success", HttpStatus.OK);
    }


}
