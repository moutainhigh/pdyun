package com.rmkj.microcap.common.modules.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmkj.microcap.common.bean.DataEntity;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
public class Ml3AgentBean extends DataEntity {
	private String id;
	//市场管理部id
	private String centerId;
    //会员单位id
    private String unitsId;
    //角色：0代理商 1会员单位用户 2市场管理部用户
    private Integer roleType;
    //账号
    private String loginName;
    //手机号
    private String mobile;
    //安全密码
    private String password;
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
	//会员单位启用停用状态  0：启用  1：停用
	private String unitsStatus;
	//会员单位Name
	private String unitsName;
	//市场管理部Name
	private String centerName;
	//市场管理部启用停用状态  0：启用  1：停用
	private String centerStatus;

	private BigDecimal returnFeeMoney;

	private BigDecimal returnFeeTotalMoney;

	private BigDecimal returnFeePercent;

	private BigDecimal returnServiceMoney;
	private BigDecimal returnServiceTotalMoney;
	private BigDecimal returnServicePercent;

	public BigDecimal getReturnServiceMoney() {
		return returnServiceMoney;
	}

	public void setReturnServiceMoney(BigDecimal returnServiceMoney) {
		this.returnServiceMoney = returnServiceMoney;
	}

	public BigDecimal getReturnServiceTotalMoney() {
		return returnServiceTotalMoney;
	}

	public void setReturnServiceTotalMoney(BigDecimal returnServiceTotalMoney) {
		this.returnServiceTotalMoney = returnServiceTotalMoney;
	}

	public BigDecimal getReturnServicePercent() {
		return returnServicePercent;
	}

	public void setReturnServicePercent(BigDecimal returnServicePercent) {
		this.returnServicePercent = returnServicePercent;
	}

	//角色列表数组
	private String[] roleAttr;
	// 拥有角色列表
	private List<Ml3RoleBean> roleList = new ArrayList<>();

	public Ml3AgentBean(){
		super();
	}
	public Ml3AgentBean(String id) {
		super(id);
	}

	public Ml3AgentBean(String id, String loginName) {
		super(id);
		this.loginName = loginName;
	}

	public BigDecimal getReturnFeeMoney() {
		return returnFeeMoney;
	}

	public void setReturnFeeMoney(BigDecimal returnFeeMoney) {
		this.returnFeeMoney = returnFeeMoney;
	}

	public BigDecimal getReturnFeeTotalMoney() {
		return returnFeeTotalMoney;
	}

	public void setReturnFeeTotalMoney(BigDecimal returnFeeTotalMoney) {
		this.returnFeeTotalMoney = returnFeeTotalMoney;
	}

	public BigDecimal getReturnFeePercent() {
		return returnFeePercent;
	}

	public void setReturnFeePercent(BigDecimal returnFeePercent) {
		this.returnFeePercent = returnFeePercent;
	}

	public static boolean isAdmin(String id) {
		return id != null && "1".equals(id);
	}

	public String[] getRoleAttr() {
		return roleAttr;
	}

	public void setRoleAttr(String[] roleAttr) {
		this.roleAttr = roleAttr;
	}
	@JsonIgnore
	public List<Ml3RoleBean> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Ml3RoleBean> roleList) {
		this.roleList = roleList;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
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
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return this.mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = new ArrayList<>();
		for (Ml3RoleBean role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = new ArrayList<>();
		for (String roleId : roleIdList) {
			Ml3RoleBean role = new Ml3RoleBean();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return StringUtils.join(roleList, ",");
	}
	@Override
	public String toString() {
		return getId();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getUnitsStatus() {
		return unitsStatus;
	}

	public void setUnitsStatus(String unitsStatus) {
		this.unitsStatus = unitsStatus;
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

	public String getCenterStatus() {
		return centerStatus;
	}

	public void setCenterStatus(String centerStatus) {
		this.centerStatus = centerStatus;
	}
}
