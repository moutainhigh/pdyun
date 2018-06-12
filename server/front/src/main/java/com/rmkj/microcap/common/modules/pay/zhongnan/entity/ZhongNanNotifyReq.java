package com.rmkj.microcap.common.modules.pay.zhongnan.entity;

/**
 * Created by Administrator on 2017/9/20.
 */
public class ZhongNanNotifyReq {
	private String return_code;
	private String out_trade_no;
	private String trade_result;
	private String message;
	private String pay_num;
	private String total_fee;
	private String sign;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
