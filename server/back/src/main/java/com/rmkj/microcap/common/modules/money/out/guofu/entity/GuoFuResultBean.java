package com.rmkj.microcap.common.modules.money.out.guofu.entity;/**
 * Created by Administrator on 2017/10/18.
 */

/**
 * 国富代付请求返回
 * @author k
 * @create -10-18-16:17
 **/

public class GuoFuResultBean {

    //操作状态	返回00代表操作成功(并非代付成功)
    private String respCode;
    //操作说明
    private String message;
    //商户流水号 商家自定义的流水号
    private String traceno;
    //平台订单号  平台生成的流水号
    private String orderno;
    //代付金额
    private String amount;
    //交易状态  1-提现处理中 2-提现成功 3-提现失败（不会扣虚拟户款）  4-提现异常
    private String transStatus;
    //1-代付处理中(需要发起查询进行确认) 2-付款成功 3-付款失败 4-付款异常(需要发起查询进行确认)
    private String payStatus;
    //代付说明 代付结果说明
    private String payMsg;
    //签名	32
    private String signature;

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

    public String getTraceno() {
        return traceno;
    }

    public void setTraceno(String traceno) {
        this.traceno = traceno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getPayMsg() {
        return payMsg;
    }

    public void setPayMsg(String payMsg) {
        this.payMsg = payMsg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
