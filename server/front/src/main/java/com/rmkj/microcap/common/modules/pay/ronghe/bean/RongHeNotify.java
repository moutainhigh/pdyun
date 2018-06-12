package com.rmkj.microcap.common.modules.pay.ronghe.bean;/**
 * Created by Administrator on 2018/1/12.
 */

/**
 * 融合支付异步通知
 * @author k
 * @create -01-12-15:46
 **/

public class RongHeNotify {

    private String Method;

    private String Data;

    private String Sign;

    private String Appid;

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getAppid() {
        return Appid;
    }

    public void setAppid(String appid) {
        Appid = appid;
    }
}
