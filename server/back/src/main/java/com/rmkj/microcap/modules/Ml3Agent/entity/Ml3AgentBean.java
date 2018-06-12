package com.rmkj.microcap.modules.Ml3Agent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-17.
*/
public class Ml3AgentBean extends DataEntity {
	//市场管理部id
	private String centerId;
    //会员单位id
    private String unitsId;
    //角色：0代理商 1会员单位用户 2市场管理部用户
    private Integer roleType;
    //账号
    private String account;
    //手机号
    private String mobile;
    //安全密码
    private String safePassword;
    //唯一邀请码
    private String agentInviteCode;
    //真实姓名
    private String realName;
    //头像
    private String agentHeader;
    //资金
    private BigDecimal money;
    //累积资金
    private BigDecimal totalMoney;
    //状态 0 正常 1 停用
    private Integer status;
    //审核状态：0  审核中 1 审核通过 2 审核未通过
    private Integer reviewStatus;
    //注册时间
    private Date createTime;
    //最近登录时间
    private Date lastLoginTime;
    //最后一次登录IP
    private String lastLoginIp;
    //银行开户姓名
    private String bankAccountName;
    //银行账号
    private String bankAccount;
    //
    private String bankName;
    //开户银行支行名
    private String bankChildName;
    //身份证
    private String idCard;
	private String unitsName;
	private String centerName;
	//关联的用户id
	private String userId;

	private BigDecimal agentReturnFeeMoney;

	private BigDecimal agentReturnFeeTotalMoney;
	@Range(min = 0, max = 100, message = "手续费比例范围：0-100")
	private BigDecimal agentReturnFeePercent;

	//会员单位返代理商服务费比例
	private String agentReturnServiceMoney;
	private String agentReturnServiceTotalMoney;
	@Range(min = 0, max = 100, message = "手续费比例范围：0-100")
	private BigDecimal agentReturnServicePercent;

	public BigDecimal getAgentReturnFeeMoney() {
		return agentReturnFeeMoney;
	}

	public void setAgentReturnFeeMoney(BigDecimal agentReturnFeeMoney) {
		this.agentReturnFeeMoney = agentReturnFeeMoney;
	}

	public BigDecimal getAgentReturnFeeTotalMoney() {
		return agentReturnFeeTotalMoney;
	}

	public void setAgentReturnFeeTotalMoney(BigDecimal agentReturnFeeTotalMoney) {
		this.agentReturnFeeTotalMoney = agentReturnFeeTotalMoney;
	}

	public BigDecimal getAgentReturnFeePercent() {
		return agentReturnFeePercent;
	}

	public void setAgentReturnFeePercent(BigDecimal agentReturnFeePercent) {
		this.agentReturnFeePercent = agentReturnFeePercent;
	}

	public String getAgentReturnServiceMoney() {
		return agentReturnServiceMoney;
	}

	public void setAgentReturnServiceMoney(String agentReturnServiceMoney) {
		this.agentReturnServiceMoney = agentReturnServiceMoney;
	}

	public String getAgentReturnServiceTotalMoney() {
		return agentReturnServiceTotalMoney;
	}

	public void setAgentReturnServiceTotalMoney(String agentReturnServiceTotalMoney) {
		this.agentReturnServiceTotalMoney = agentReturnServiceTotalMoney;
	}

	public BigDecimal getAgentReturnServicePercent() {
		return agentReturnServicePercent;
	}

	public void setAgentReturnServicePercent(BigDecimal agentReturnServicePercent) {
		this.agentReturnServicePercent = agentReturnServicePercent;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}


	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public void setUnitsId(String unitsId){
		this.unitsId=unitsId;
	}
	public String getUnitsId(){
		return this.unitsId;
	}
	public void setRoleType(Integer roleType){
		this.roleType=roleType;
	}
	public Integer getRoleType(){
		return this.roleType;
	}
	public void setAccount(String account){
		this.account=account;
	}
	public String getAccount(){
		return this.account;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return this.mobile;
	}
	public void setSafePassword(String safePassword){
		this.safePassword=safePassword;
	}
	public String getSafePassword(){
		return this.safePassword;
	}
	public void setAgentInviteCode(String agentInviteCode){
		this.agentInviteCode=agentInviteCode;
	}
	public String getAgentInviteCode(){
		return this.agentInviteCode;
	}
	public void setRealName(String realName){
		this.realName=realName;
	}
	public String getRealName(){
		return this.realName;
	}
	public void setAgentHeader(String agentHeader){
		this.agentHeader=agentHeader;
	}
	public String getAgentHeader(){
		return this.agentHeader;
	}
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	public BigDecimal getMoney(){
		return this.money;
	}
	public void setTotalMoney(BigDecimal totalMoney){
		this.totalMoney=totalMoney;
	}
	public BigDecimal getTotalMoney(){
		return this.totalMoney;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setReviewStatus(Integer reviewStatus){
		this.reviewStatus=reviewStatus;
	}
	public Integer getReviewStatus(){
		return this.reviewStatus;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime=lastLoginTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}
	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp=lastLoginIp;
	}
	public String getLastLoginIp(){
		return this.lastLoginIp;
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
	public void setIdCard(String idCard){
		this.idCard=idCard;
	}
	public String getIdCard(){
		return this.idCard;
	}

}
