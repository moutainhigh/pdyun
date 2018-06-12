package com.rmkj.microcap.common.modules.sms.qixin;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.sms.SmsSend;
import com.rmkj.microcap.common.modules.sms.SmsServerName;
import com.rmkj.microcap.common.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by renwp on 2016/10/13.
 * 启信网 短信服务
 */
@SmsServerName("qixin")
public class QiXinSmsService implements SmsSend {

    private final Logger Log = Logger.getLogger(QiXinSmsService.class);

    @HttpService
    private QiXinHttpApi qiXinHttpApi;

    @Value("${qi_xin_username}")
    private String userName;
    @Value("${qi_xin_pwd}")
    private String pwd;

    @Override
    public boolean send(String msg, String phone) {
        try {
            if(!ProjectConstants.SMS_DEBUG){
                String str = qiXinHttpApi.send(userName, Utils.md5(pwd), msg, phone).execute().body();
                Log.info("验证码发送 ".concat(str));
                return true;
            }else{
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sendMore(String msg, String ... phone) {
        return false;
    }
}
