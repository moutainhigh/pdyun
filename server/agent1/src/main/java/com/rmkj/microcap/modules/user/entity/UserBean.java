package com.rmkj.microcap.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016-10-17.
 */
public class UserBean extends DataEntity {
	private String id;
	//微信openid
	private String openId;
	//用户头像
	private String userHeader;
	//中文姓名
	private String chnName;
	//手机号
	private String mobile;
	//交易密码
	private String tradePassword;
	//资金
	private Double money;
	//累计充值资金
	private Double rechargeMoney;
	//用户状态：0 正常 1 停用
	private Integer status;
	//注册时间
	private Date registerTime;
	//最后一次登录时间
	private Date lastLoginTime;
	//最后一次登录地址
	private String lastLoginIp;
	private Double uMoneyMax;
	private Double uMoneyMin;
	private String thirdSerialNo;
	//代金券余额
	public Double couponMoney;
	//赠送的代金券
	private Double sendMoney;
	//备注
	private String content;
	//经纪人姓名
	private String realName;
	private String agentInviteCode;
	//军团长的微信二维码
	private String ticket;
	//累计交易总量
	private Integer tradeCount;
	private String orderKey;
	private String orderValue;
	//当前军团长的军团成员的累计充值金额
	private Double totalMoney;
	//当前军团长的军团成员的累计交易总量
	private BigDecimal  totalTradeCount;
	//当前军团长的军团成员的个数
	private Integer totalCount;
	//代理商姓名
	private String agentRealName;
	//代理商手机号
	private String agentMobile;
	//当前军团长的手机号
	private String juntuanMobile;
	//当前军团长的姓名
	private String juntuanChnName;
	//所属级别
	private String jType;
	//会员单位的id
	private String unitsId;
	//代理商的账号
	private String account;

	private String agentId;

	private String parent1Id;
	private String parent2Id;
	private String parent3Id;

	public String getParent1Id() {
		return parent1Id;
	}

	public void setParent1Id(String parent1Id) {
		this.parent1Id = parent1Id;
	}

	public String getParent2Id() {
		return parent2Id;
	}

	public void setParent2Id(String parent2Id) {
		this.parent2Id = parent2Id;
	}

	public String getParent3Id() {
		return parent3Id;
	}

	public void setParent3Id(String parent3Id) {
		this.parent3Id = parent3Id;
	}

	private BigDecimal totalRechargeMoney;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getTotalRechargeMoney() {
		return totalRechargeMoney;
	}

	public void setTotalRechargeMoney(BigDecimal totalRechargeMoney) {
		this.totalRechargeMoney = totalRechargeMoney;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUnitsId() {
		return unitsId;
	}

	public void setUnitsId(String unitsId) {
		this.unitsId = unitsId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
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

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalTradeCount() {
		return totalTradeCount;
	}

	public void setTotalTradeCount(BigDecimal totalTradeCount) {
		this.totalTradeCount = totalTradeCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getAgentRealName() {
		return agentRealName;
	}

	public void setAgentRealName(String agentRealName) {
		this.agentRealName = agentRealName;
	}

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public String getJuntuanMobile() {
		return juntuanMobile;
	}

	public void setJuntuanMobile(String juntuanMobile) {
		this.juntuanMobile = juntuanMobile;
	}

	public String getJuntuanChnName() {
		return juntuanChnName;
	}

	public void setJuntuanChnName(String juntuanChnName) {
		this.juntuanChnName = juntuanChnName;
	}

	public String getjType() {
		return jType;
	}

	public void setjType(String jType) {
		this.jType = jType;
	}

	public String getAgentInviteCode() {
		return agentInviteCode;
	}

	public void setAgentInviteCode(String agentInviteCode) {
		this.agentInviteCode = agentInviteCode;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Double getSendMoney() {
		return sendMoney;
	}

	public void setSendMoney(Double sendMoney) {
		this.sendMoney = sendMoney;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Double couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getThirdSerialNo() {
		return thirdSerialNo;
	}

	public void setThirdSerialNo(String thirdSerialNo) {
		this.thirdSerialNo = thirdSerialNo;
	}

	public Double getuMoneyMax() {
		return uMoneyMax;
	}

	public void setuMoneyMax(Double uMoneyMax) {
		this.uMoneyMax = uMoneyMax;
	}

	public Double getuMoneyMin() {
		return uMoneyMin;
	}

	public void setuMoneyMin(Double uMoneyMin) {
		this.uMoneyMin = uMoneyMin;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setOpenId(String openId){
		this.openId=openId;
	}
	public String getOpenId(){
		return this.openId;
	}
	public void setUserHeader(String userHeader){
		this.userHeader=userHeader;
	}
	public String getUserHeader(){
		return this.userHeader;
	}
	public void setChnName(String chnName){
		this.chnName=chnName;
	}
	public String getChnName(){
		return this.chnName;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return this.mobile;
	}
	public void setTradePassword(String tradePassword){
		this.tradePassword=tradePassword;
	}
	public String getTradePassword(){
		return this.tradePassword;
	}

	public void setRechargeMoney(Double rechargeMoney){
		this.rechargeMoney=rechargeMoney;
	}
	public Double getRechargeMoney(){
		return this.rechargeMoney;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setRegisterTime(Date registerTime){
		this.registerTime=registerTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterTime(){
		return this.registerTime;
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

}
