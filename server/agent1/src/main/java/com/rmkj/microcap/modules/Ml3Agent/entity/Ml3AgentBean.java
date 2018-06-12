package com.rmkj.microcap.modules.Ml3Agent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-17.
*/
public class Ml3AgentBean extends DataEntity {
	//代理id
	private  String id;
	//市场管理部id
	private String centerId;
    //会员单位id
    private String unitsId;
	//属于这个代理的用户id
	private String userId;
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //最近登录时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMax;
	private BigDecimal difMoney;
	private BigDecimal rechargeMoney;
	private BigDecimal outMoney;
	private BigDecimal money1;
	private String unitsCode;
	private String orderKey;
	private String orderValue;
	private String unitsName;
	private BigDecimal totalTradeCount;
	private BigDecimal totalCount;
	private BigDecimal totalRechargeMoney;

	private BigDecimal agentReturnFeeMoney;

	private BigDecimal agentReturnFeeTotalMoney;
	@Range(min = 0, max = 100, message = "手续费比例范围：0-100")
	private BigDecimal agentReturnFeePercent;

	//会员单位返代理商服务费比例
	private BigDecimal agentReturnServiceMoney;
	private BigDecimal agentReturnServiceTotalMoney;
	@Range(min = 0, max = 100, message = "手续费比例范围：0-100")
	private BigDecimal agentReturnServicePercent;

	public BigDecimal getAgentReturnServiceMoney() {
		return agentReturnServiceMoney;
	}

	public void setAgentReturnServiceMoney(BigDecimal agentReturnServiceMoney) {
		this.agentReturnServiceMoney = agentReturnServiceMoney;
	}

	public BigDecimal getAgentReturnServiceTotalMoney() {
		return agentReturnServiceTotalMoney;
	}

	public void setAgentReturnServiceTotalMoney(BigDecimal agentReturnServiceTotalMoney) {
		this.agentReturnServiceTotalMoney = agentReturnServiceTotalMoney;
	}

	public BigDecimal getAgentReturnServicePercent() {
		return agentReturnServicePercent;
	}

	public void setAgentReturnServicePercent(BigDecimal agentReturnServicePercent) {
		this.agentReturnServicePercent = agentReturnServicePercent;
	}

	/**
     * center.bank_account AS centerAccount,
     center.bank_name AS centerBankName,
     center.real_name AS centerRealName,
     center.id_card AS centerIdCard,
     center.bank_account_name AS centerBankAccountName,
     center.bank_child_name AS centerBankChildName
     * @return
     */
    private String centerBankAccount;

    private String centerBankName;

    private String centerRealName;

    private String centerIdCard;

    private String centerBankAccountName;

    private String centerBankChildName;
	private int subTimes;

	public int getSubTimes() {
		return subTimes;
	}

	public void setSubTimes(int subTimes) {
		this.subTimes = subTimes;
	}

	public String getCenterBankAccount() {
        return centerBankAccount;
    }

    public void setCenterBankAccount(String centerBankAccount) {
        this.centerBankAccount = centerBankAccount;
    }

    public String getCenterBankName() {
        return centerBankName;
    }

    public void setCenterBankName(String centerBankName) {
        this.centerBankName = centerBankName;
    }

    public String getCenterRealName() {
        return centerRealName;
    }

    public void setCenterRealName(String centerRealName) {
        this.centerRealName = centerRealName;
    }

    public String getCenterIdCard() {
        return centerIdCard;
    }

    public void setCenterIdCard(String centerIdCard) {
        this.centerIdCard = centerIdCard;
    }

    public String getCenterBankAccountName() {
        return centerBankAccountName;
    }

    public void setCenterBankAccountName(String centerBankAccountName) {
        this.centerBankAccountName = centerBankAccountName;
    }

    public String getCenterBankChildName() {
        return centerBankChildName;
    }

    public void setCenterBankChildName(String centerBankChildName) {
        this.centerBankChildName = centerBankChildName;
    }

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

	public BigDecimal getTotalRechargeMoney() {
		return totalRechargeMoney;
	}

	public void setTotalRechargeMoney(BigDecimal totalRechargeMoney) {
		this.totalRechargeMoney = totalRechargeMoney;
	}

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}

	public BigDecimal getTotalTradeCount() {
		return totalTradeCount;
	}

	public void setTotalTradeCount(BigDecimal totalTradeCount) {
		this.totalTradeCount = totalTradeCount;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getUnitsCode() {
		return unitsCode;
	}

	public void setUnitsCode(String unitsCode) {
		this.unitsCode = unitsCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getMoney1() {
		return money1;
	}

	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}

	public Date getCreateTimeMin() {
		return createTimeMin;
	}

	public void setCreateTimeMin(Date createTimeMin) {
		this.createTimeMin = createTimeMin;
	}

	public Date getCreateTimeMax() {
		return createTimeMax;
	}

	public void setCreateTimeMax(Date createTimeMax) {
		this.createTimeMax = createTimeMax;
	}

	public BigDecimal getDifMoney() {
		return difMoney;
	}

	public void setDifMoney(BigDecimal difMoney) {
		this.difMoney = difMoney;
	}

	public BigDecimal getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(BigDecimal rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public void setUnitsId(String unitsId){
		this.unitsId=unitsId;
	}
	public String getUnitsId(){
		return this.unitsId;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
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
