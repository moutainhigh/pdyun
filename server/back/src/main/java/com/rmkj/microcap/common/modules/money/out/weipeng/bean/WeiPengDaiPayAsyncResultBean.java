package com.rmkj.microcap.common.modules.money.out.weipeng.bean;/**
 * Created by Administrator on 2017/3/7.
 */

/**
 * TODO 威鹏代付异步通知-返回结果
 * @author k
 * @create -03-07-18:14
 **/

public class WeiPengDaiPayAsyncResultBean {

    //返回结果标识
    private String return_code;
    //订单号
    private String trade_no;
    //支付结果
    private String trade_result;
    //信息描述
    private String message;
    //商户订单号
    private String pay_num;
    //订单金额（整数 分）
    private Integer total_fee;
    //验签
    private String sign;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getTrade_result() {
        return trade_result;
    }

    public void setTrade_result(String trade_result) {
        this.trade_result = trade_result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPay_num() {
        return pay_num;
    }

    public void setPay_num(String pay_num) {
        this.pay_num = pay_num;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
