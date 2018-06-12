package com.rmkj.microcap.common.modules.pay.zhongnan.entity;

/**
 * Created by Administrator on 2017/9/25.
 */
public class ZhongNanWithdrawalsCheckBean {
	//银行卡号
	private String bankAccount;
	//姓名
	private String chnName;
	private String idCard;
	private String phone;

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
