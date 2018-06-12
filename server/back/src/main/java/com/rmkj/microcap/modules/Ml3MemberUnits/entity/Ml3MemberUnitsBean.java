package com.rmkj.microcap.modules.Ml3MemberUnits.entity;

import com.rmkj.microcap.common.bean.DataEntity;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
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
    private Integer status;
	//所属市场管理部名称
	private String centerName;
	private String agentInviteCode;
	//二级域名
	private String twoLevelDomain;
	//备注
	private String remark;

	//所属市场管理部Name
	private String operateCenterName;
	//追加保证金
	@Range(min = 1, message = "金额不合法")
	private BigDecimal addUnitsBondMoney;
	//会员单位返手续费余额
	private BigDecimal unitsReturnFeeMoney;
	//会员单位返手续费累计余额
	private BigDecimal unitsReturnFeeTotalMoney;
	//市场管理部给会员单位返手续费比例
	private BigDecimal unitsReturnFeePercent;
	//保证金余额限制
	private BigDecimal moneyLimit;
	//会员单位手机号
	private String mobile;

	private BigDecimal unitsReturnServiceMoney;
	private BigDecimal unitsReturnServiceTotalMoney;
	private BigDecimal unitsReturnServicePercent;

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

	public BigDecimal getUnitsReturnServicePercent() {
		return unitsReturnServicePercent;
	}

	public void setUnitsReturnServicePercent(BigDecimal unitsReturnServicePercent) {
		this.unitsReturnServicePercent = unitsReturnServicePercent;
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

	public BigDecimal getAddUnitsBondMoney() {
		return addUnitsBondMoney;
	}

	public void setAddUnitsBondMoney(BigDecimal addUnitsBondMoney) {
		this.addUnitsBondMoney = addUnitsBondMoney;
	}

	public String getOperateCenterName() {
		return operateCenterName;
	}

	public void setOperateCenterName(String operateCenterName) {
		this.operateCenterName = operateCenterName;
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
