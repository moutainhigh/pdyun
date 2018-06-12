package com.rmkj.microcap.modules.Ml3AgentUser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-17.
*/
public class Ml3AgentUserBean extends DataEntity {
    //会员用户id
    private String userId;
    //经纪人id
    private String agentId;
    //会员单位id
    private String unitsId;
    //市场管理部id
    private String centerId;
    //累计返佣金额
    private BigDecimal totalMoney;
    //累计交易数量
    private Integer totalTradeCount;
    //
    private Date createTime;
	//市场管理部名称
	private String centerName;
	//会员单位名称
	private String unitsName;
	//代理账号
	private String agentAccount;
	//手机号
	private String agentMobile;
	//密码
	private String agentPassWord;
	//创建时间
	private Date agentCreateTime;
	//上次登录时间
	private Date agentLastTime;
	//上次登录ip
	private String agentLastIp;
	//状态
	private Integer agentStatus;

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}

	public String getAgentAccount() {
		return agentAccount;
	}

	public void setAgentAccount(String agentAccount) {
		this.agentAccount = agentAccount;
	}

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public String getAgentPassWord() {
		return agentPassWord;
	}

	public void setAgentPassWord(String agentPassWord) {
		this.agentPassWord = agentPassWord;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAgentCreateTime() {
		return agentCreateTime;
	}

	public void setAgentCreateTime(Date agentCreateTime) {
		this.agentCreateTime = agentCreateTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAgentLastTime() {
		return agentLastTime;
	}

	public void setAgentLastTime(Date agentLastTime) {
		this.agentLastTime = agentLastTime;
	}

	public String getAgentLastIp() {
		return agentLastIp;
	}

	public void setAgentLastIp(String agentLastIp) {
		this.agentLastIp = agentLastIp;
	}

	public Integer getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(Integer agentStatus) {
		this.agentStatus = agentStatus;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setUnitsId(String unitsId){
		this.unitsId=unitsId;
	}
	public String getUnitsId(){
		return this.unitsId;
	}
	public void setCenterId(String centerId){
		this.centerId=centerId;
	}
	public String getCenterId(){
		return this.centerId;
	}
	public void setTotalMoney(BigDecimal totalMoney){
		this.totalMoney=totalMoney;
	}
	public BigDecimal getTotalMoney(){
		return this.totalMoney;
	}
	public void setTotalTradeCount(Integer totalTradeCount){
		this.totalTradeCount=totalTradeCount;
	}
	public Integer getTotalTradeCount(){
		return this.totalTradeCount;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}

}
