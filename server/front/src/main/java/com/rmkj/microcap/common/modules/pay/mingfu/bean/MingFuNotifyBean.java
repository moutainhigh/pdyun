package com.rmkj.microcap.common.modules.pay.mingfu.bean;/**
 * Created by Administrator on 2018/1/18.
 */

/**
 * @author k
 * @create -01-18-11:19
 **/

public class MingFuNotifyBean {

    //交易流水号
    private String tranNo;
    //响应码
    private String respCode;
    //应答信息
    private String respMsg;
    //交易金额
    private String tranAmt;
    //清算日期
    private String settleDate;
    //签名
    private String sign;

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
