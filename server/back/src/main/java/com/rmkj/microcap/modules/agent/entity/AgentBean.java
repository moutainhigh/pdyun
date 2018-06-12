package com.rmkj.microcap.modules.agent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-4.
*/
public class AgentBean extends DataEntity {
    //微信openid
    private String openId;
    //头像
    private String agentHeader;
    //手机号
    private String mobile;
    //唯一邀请码
    private String agentInviteCode;
    //真实姓名
    private String realName;
    //资金
    private BigDecimal money;
    //累积资金
    private BigDecimal totalMoney;
    //安全密码
    private String safePassword;
    //状态 0 正常 1 停用
    private Integer status;
    //审核状态：0  审核中 1 审核通过 2 审核未通过
    private Integer reviewStatus;
    //注册时间
    private Date registerTime;
    //最近登录时间
    private Date lastLoginTime;
	//邀请注册用户的人数
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setOpenId(String openId){
		this.openId=openId;
	}
	public String getOpenId(){
		return this.openId;
	}
	public void setAgentHeader(String agentHeader){
		this.agentHeader=agentHeader;
	}
	public String getAgentHeader(){
		return this.agentHeader;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return this.mobile;
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
	public void setSafePassword(String safePassword){
		this.safePassword=safePassword;
	}
	public String getSafePassword(){
		return this.safePassword;
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

}
