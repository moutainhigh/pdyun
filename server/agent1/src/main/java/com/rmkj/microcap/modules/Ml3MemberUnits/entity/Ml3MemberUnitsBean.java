package com.rmkj.microcap.modules.Ml3MemberUnits.entity;

import com.rmkj.microcap.common.bean.DataEntity;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
* Created by Administrator on 2016-11-17.
*/
public class Ml3MemberUnitsBean extends DataEntity {
    //市场管理部id
    private String centerId;
    //会员单位名称
    private String name;
    //余额
    private BigDecimal money;
    //保证金
    private Double bondMoney;
    //真实姓名
    private String realName;
    //身份证
    private String idCard;
    //银行开户姓名
    private String bankAccountName;
    //银行账号
    private String bankAccount;
    //
    private String bankName;
    //开户银行支行名
    private String bankChildName;
    //状态：0正常 1停用
    private int status;
	//所属市场管理部名称
	private String centerName;
	private String account;
	private String code;
	private String agentInviteCode;
	//二级域名
	private String twoLevelDomain;
	private String remark;

	private BigDecimal moneyAddFee;

	private BigDecimal unitsReturnFeeMoney;

	private BigDecimal unitsReturnFeeTotalMoney;
	@Range(min = 0 , max = 100, message = "返佣手续费比例设置在：0-100之间")
	private BigDecimal unitsReturnFeePercent;

	//会员单位保证金限制
	private BigDecimal moneyLimit;
	//会员单位手机号
	private String mobile;

	//市场管理部返会员单位服务费比例
	private BigDecimal unitsReturnServiceMoney;
	private BigDecimal unitsReturnServiceTotalMoney;
	private String unitsReturnServicePercent;

	public BigDecimal getUnitsReturnServiceMoney() {
		return unitsReturnServiceMoney;
	}

	public void setUnitsReturnServiceMoney(BigDecimal unitsReturnServiceMoney) {
		this.unitsReturnServiceMoney = unitsReturnServiceMoney;
	}

	public BigDecimal getUnitsReturnServiceTotalMoney() {
		return unitsReturnServiceTotalMoney;
	}

	public void setUnitsReturnServiceTotalMoney(BigDecimal unitsReturnServiceTotalMoney) {
		this.unitsReturnServiceTotalMoney = unitsReturnServiceTotalMoney;
	}

	public String getUnitsReturnServicePercent() {
		return unitsReturnServicePercent;
	}

	public void setUnitsReturnServicePercent(String unitsReturnServicePercent) {
		this.unitsReturnServicePercent = unitsReturnServicePercent;
	}

	private int subTimes;

	public int getSubTimes() {
		return subTimes;
	}

	public void setSubTimes(int subTimes) {
		this.subTimes = subTimes;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getMoneyLimit() {
		return moneyLimit;
	}

	public void setMoneyLimit(BigDecimal moneyLimit) {
		this.moneyLimit = moneyLimit;
	}

	public BigDecimal getUnitsReturnFeeMoney() {
		return unitsReturnFeeMoney;
	}

	public void setUnitsReturnFeeMoney(BigDecimal unitsReturnFeeMoney) {
		this.unitsReturnFeeMoney = unitsReturnFeeMoney;
	}

	public BigDecimal getUnitsReturnFeeTotalMoney() {
		return unitsReturnFeeTotalMoney;
	}

	public void setUnitsReturnFeeTotalMoney(BigDecimal unitsReturnFeeTotalMoney) {
		this.unitsReturnFeeTotalMoney = unitsReturnFeeTotalMoney;
	}

	public BigDecimal getUnitsReturnFeePercent() {
		return unitsReturnFeePercent;
	}

	public void setUnitsReturnFeePercent(BigDecimal unitsReturnFeePercent) {
		this.unitsReturnFeePercent = unitsReturnFeePercent;
	}

	public BigDecimal getMoneyAddFee() {
		return moneyAddFee;
	}

	public void setMoneyAddFee(BigDecimal moneyAddFee) {
		this.moneyAddFee = moneyAddFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTwoLevelDomain() {
		return twoLevelDomain;
	}

	public void setTwoLevelDomain(String twoLevelDomain) {
		this.twoLevelDomain = twoLevelDomain;
	}

	public String getAgentInviteCode() {
		return agentInviteCode;
	}

	public void setAgentInviteCode(String agentInviteCode) {
		this.agentInviteCode = agentInviteCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public void setCenterId(String centerId){
		this.centerId=centerId;
	}
	public String getCenterId(){
		return this.centerId;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	public BigDecimal getMoney(){
		return this.money;
	}
	public void setBondMoney(Double bondMoney){
		this.bondMoney=bondMoney;
	}
	public Double getBondMoney(){
		return this.bondMoney;
	}
	public void setRealName(String realName){
		this.realName=realName;
	}
	public String getRealName(){
		return this.realName;
	}
	public void setIdCard(String idCard){
		this.idCard=idCard;
	}
	public String getIdCard(){
		return this.idCard;
	}
	public void setBankAccountName(String bankAccountName){
		this.bankAccountName=bankAccountName;
	}
	public String getBankAccountName(){
		return this.bankAccountName;
	}
	public void setBankAccount(String bankAccount){
		this.bankAccount=bankAccount;
	}
	public String getBankAccount(){
		return this.bankAccount;
	}
	public void setBankName(String bankName){
		this.bankName=bankName;
	}
	public String getBankName(){
		return this.bankName;
	}
	public void setBankChildName(String bankChildName){
		this.bankChildName=bankChildName;
	}
	public String getBankChildName(){
		return this.bankChildName;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}

}
