package com.rmkj.microcap.common.modules.money.out.ytb.bean;

/**
 * Created by Administrator on 2017/1/17.
 */
public class BalanceBean {
    private String respCode;
    private String message;
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
