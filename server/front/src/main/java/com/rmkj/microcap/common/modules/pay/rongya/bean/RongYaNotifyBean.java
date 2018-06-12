package com.rmkj.microcap.common.modules.pay.rongya.bean;/**
 * Created by Administrator on 2017/12/29.
 */

/**
 * 融雅支付回调通知
 * @author k
 * @create -12-29-14:41
 **/

public class RongYaNotifyBean {
    //商户订单号
    private String orderid;
    //订单金额
    private String ovalue;
    //平台订单号
    private String sysorderid;
    //交易状态 0成功
    private String opstate;

    private String attach;

    private String sign;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOvalue() {
        return ovalue;
    }

    public void setOvalue(String ovalue) {
        this.ovalue = ovalue;
    }

    public String getSysorderid() {
        return sysorderid;
    }

    public void setSysorderid(String sysorderid) {
        this.sysorderid = sysorderid;
    }

    public String getOpstate() {
        return opstate;
    }

    public void setOpstate(String opstate) {
        this.opstate = opstate;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
