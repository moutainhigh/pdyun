package com.rmkj.microcap.common.modules.weixin.bean;

/**
 * Created by zhangbowen on 2016/6/7.
 */
public class ResponseToken extends WeiXinResult{
    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}