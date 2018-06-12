package com.rmkj.microcap.common.modules.money.out.crown.entity;/**
 * Created by Administrator on 2017/11/21.
 */

/**
 *
 * @author k
 * @create -11-21-10:53
 **/

public class CrownNotifyBean {

    //    商户号	merId	M	商户ID
    private String merId;
    //    商户订单号	merOrderId	M	商户订单号
    private String merOrderId;
    //    皇冠统一单号	orderId	M	皇冠支付统一单号
    private String orderId;
    //    交易金额	txnAmt	M	交易单位为分
    private String txnAmt;
    //    交易币种	currency	M	三位 ISO 货币代码，目前仅支持人民币 CNY
    private String currency;
    //    请求结果标识	success	M	true表示成功，false表示失败
    private String success;
    //    请求失败编码	code	C	success为false时出现，表示失败的原因
    private String code;
    //    请求结果描述	message	C	请求结果描述
    private String message;
    //    商户保留字段	attach	M	原样返回给通知接口
    private String attach;
    //    签名信息	signature	M	签名信息
    private String signature;
    //    签名算法	signMethod	M	签名算法，当前仅支持MD5
    private String signMethod;

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }
}
