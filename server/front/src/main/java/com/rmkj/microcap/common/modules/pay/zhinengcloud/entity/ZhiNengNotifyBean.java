package com.rmkj.microcap.common.modules.pay.zhinengcloud.entity;/**
 * Created by Administrator on 2017/12/18.
 */

/**
 * 智能云支付，付款成功回调通知（异步回调）
 * @author k
 * @create -12-18-15:13
 **/

public class ZhiNengNotifyBean {

    private String p_id;

    private String orderid;

    private String price;

    private String realprice;

    private String orderuid;

    private String key;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRealprice() {
        return realprice;
    }

    public void setRealprice(String realprice) {
        this.realprice = realprice;
    }

    public String getOrderuid() {
        return orderuid;
    }

    public void setOrderuid(String orderuid) {
        this.orderuid = orderuid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
