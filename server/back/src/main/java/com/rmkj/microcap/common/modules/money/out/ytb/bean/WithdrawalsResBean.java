package com.rmkj.microcap.common.modules.money.out.ytb.bean;

/**
 * Created by Administrator on 2017/1/17.
 */
public class WithdrawalsResBean {
    private String respCode;
    private String message;
    private String transStatus;
    private String payStatus;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
