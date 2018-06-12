package com.rmkj.microcap.common.modules.pay.zhongnan.entity;

import com.rmkj.microcap.common.constants.RegexpConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2017/9/19.
 */
public class ZhongNanQuickPayBean {
	@NotNull
	@Pattern(regexp = RegexpConstants.MONEY, message = "金额不合法")
	private String money;
	@NotNull
	private String card_no;
	@NotNull
	private String phone_no;
	@NotNull
	private String card_name;
	@NotNull
	private String id_no;

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
}
