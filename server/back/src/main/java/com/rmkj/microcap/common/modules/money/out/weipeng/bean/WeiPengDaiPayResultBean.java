package com.rmkj.microcap.common.modules.money.out.weipeng.bean;/**
 * Created by Administrator on 2017/3/7.
 */

/**
 * TODO 威鹏代付返回结果
 * @author k
 * @create -03-07-20:51
 **/

public class WeiPengDaiPayResultBean {
    /*请求结果标示	return_code(10000生成订单成功)	必填
    订单号	orderid	成功必填
    信息描述	message （提交验证成功）	必填*/
    //请求结果标识
    private String return_code;
    //订单号
    private String orderid;
    //信息描述
    private String message;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
