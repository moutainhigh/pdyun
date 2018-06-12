package com.rmkj.microcap.common.modules.pay.ronghe.service;/**
 * Created by Administrator on 2018/1/12.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.ronghe.bean.RongHeNotify;
import com.rmkj.microcap.common.modules.pay.ronghe.bean.RongHeNotifyData;
import com.rmkj.microcap.common.modules.pay.ronghe.bean.RongHeResp;
import com.rmkj.microcap.common.modules.pay.ronghe.http.RongHePayApi;
import com.rmkj.microcap.common.modules.pay.ronghe.utils.AesEncryption;
import com.rmkj.microcap.common.modules.pay.ronghe.utils.MD5Util;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author k
 * @create -01-12-14:07
 **/
@Service
public class RongHePayService {
    private final Logger Log = Logger.getLogger(getClass());

    @HttpService
    private RongHePayApi rongHePayApi;

    public String fastPay(MoneyRecord moneyRecord){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", moneyRecord.getMoney().multiply(new BigDecimal(100)));
            jsonObject.put("payordernumber", moneyRecord.getSerialNo());
            jsonObject.put("fronturl", ProjectConstants.RONGHE_PAY_RETURN_URL);
            jsonObject.put("backurl", ProjectConstants.RONGHE_PAY_NOTIFY_URL);
            jsonObject.put("Body", "中赢國際");
            jsonObject.put("PayType", "0");
            jsonObject.put("SubpayType","02");
            Log.info("base64加密".concat(jsonObject.toString()));
            String key = ProjectConstants.RONGHE_PAY_KEY;
            String data  = AesEncryption.Encrypt(jsonObject.toString(), key, key);

            Map<String,Object> map = new HashMap<String,Object>();
            String appid = ProjectConstants.RONGHR_PAY_MER_NO;
            String method = "masget.pay.compay.router.font.pay";
            String format = "json";
            String version = "2.0";
            String session = ProjectConstants.RONGHE_PAY_SESSION;
            map.put("appid", appid);
            map.put("method", method);
            map.put("format", format);
            map.put("data", data);
            map.put("v", version);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp=sdf.format(new Date());
            map.put("timestamp",timestamp);
            map.put("session", session);
            String sign =MD5Util.string2MD5(key+appid+data+format+method+session+timestamp+version+key).toLowerCase();
            //String sign = MD5Util.string2MD5(key+appid+data+format+method+timestamp+version+key).toLowerCase();
            map.put("sign",sign);
            Log.info("融合请求参数:".concat(JSONObject.toJSONString(map)));
            Response<String> execute = rongHePayApi.fastPay(map).execute();
            String body = execute.body();
            Log.info("融合支付请求:".concat(body));
            RongHeResp rongHeResp = JSONObject.parseObject(body, RongHeResp.class);
            if(rongHeResp.getRet().equals("0") && rongHeResp.getMessage().equals("成功")){
                return rongHeResp.getData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Autowired
    NotifyInterface notifyInterface;

    public ResponseEntity callback(RongHeNotify entity){
        Log.info("融合支付异步通知:".concat(JSON.toJSONString(entity)));
        String msg = "";
        try {
            //签名验证
            String signStr = entity.getData().concat(ProjectConstants.RONGHE_PAY_KEY);
            String sign = MD5Util.string2MD5(signStr).toLowerCase();
            if(sign.equals(entity.getSign())){
                //对通知中的data进行解码
                String data = AesEncryption.Desencrypt(entity.getData(), ProjectConstants.RONGHE_PAY_KEY, ProjectConstants.RONGHE_PAY_KEY);
                Log.info("融合支付异步通知Data:".concat(data));
                RongHeNotifyData rongHeNotifyData = JSONObject.parseObject(data, RongHeNotifyData.class);
                if(rongHeNotifyData.getRespcode().equals("2") && rongHeNotifyData.getRespmsg().equals("支付成功")){
                    notifyInterface.success(rongHeNotifyData.getOrdernumber(), rongHeNotifyData.getPayorderid());
                   msg = "{\"response\": \"00\", \"message\": \"成功\"}";
                    return ResponseEntity.ok(msg);
                }
            }else{
                Log.info("验签失败(本地-原始)" + sign + entity.getSign());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        msg = "{\"response\": \"22\", \"message\": \"失败\"}";
        return ResponseEntity.ok("");
    }
}
