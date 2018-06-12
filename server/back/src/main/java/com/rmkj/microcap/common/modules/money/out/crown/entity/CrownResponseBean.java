package com.rmkj.microcap.common.modules.money.out.crown.entity;/**
 * Created by Administrator on 2017/11/21.
 */

/**
 * 皇冠代付请求响应
 * @author k
 * @create -11-21-14:44
 **/

public class CrownResponseBean {

//    请求结果标识	success	M	true表示成功，false表示失败
//    请求失败编码	code	C	success为true时出现，表示失败的原因，
//    具体原因请查阅请求失败编码
//    请求结果描述	message	C	请求结果描述
//    代付费用	chargeFee	C	代付费用，单位分
//    代付金额	agencyAmt	C	代付金额，单位分
//    平台订单号	orderId	C	皇冠唯一订单号
//    商户订单号	merOrderId	M	商户订单号（10位以上，不重复）
//    签名信息	signature	M	MD5加密结果
//    签名算法	signMethod	M	签名算法，当前仅支持MD5

    private String success;

    private String code;

    private String message;

    private String chargeFee;

    private String agencyAmt;

    private String orderId;

    private String merOrderId;

    private String signature;

    private String signMethod;

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

    public String getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }

    public String getAgencyAmt() {
        return agencyAmt;
    }

    public void setAgencyAmt(String agencyAmt) {
        this.agencyAmt = agencyAmt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
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
