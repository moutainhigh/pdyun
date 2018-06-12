package com.rmkj.microcap.common.modules.pay.ronghe.bean;/**
 * Created by Administrator on 2018/1/12.
 */

/**
 * 融合支付请求返回
 * @author k
 * @create -01-12-15:23
 **/

public class RongHeResp {

    private String ret;

    private String message;

    private String data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
