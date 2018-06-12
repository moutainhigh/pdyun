package com.rmkj.microcap.common.modules.pay.ronghe.bean;/**
 * Created by Administrator on 2018/1/12.
 */

/**
 * 异步通知中的data
 * @author k
 * @create -01-12-16:00
 **/

public class RongHeNotifyData {

    private String ordernumber;//商户订单号

    private String amount; //交易金额

    private String payorderid; //交易流水号

    private String businesstime; //交易时间yyyy-MM-dd hh:mm:ss

    private String respcode; //交易状态 1-待支付 2-支付完成 3-已关闭 4-交易撤销

    private String extraparams; //扩展内容 原样返回

    private String respmsg; //状态说明

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayorderid() {
        return payorderid;
    }

    public void setPayorderid(String payorderid) {
        this.payorderid = payorderid;
    }

    public String getBusinesstime() {
        return businesstime;
    }

    public void setBusinesstime(String businesstime) {
        this.businesstime = businesstime;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getExtraparams() {
        return extraparams;
    }

    public void setExtraparams(String extraparams) {
        this.extraparams = extraparams;
    }

    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }
}

