package com.rmkj.microcap.modules.agentuser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-4.
*/
public class AgentUserBean extends DataEntity {
    //会员用户id
    private String userId;
    //经纪人id
    private String agentId;
    //累计返佣金额
    private BigDecimal totalMoney;
    //累计交易数量
    private Integer totalTradeCount;
    //创建时间
    private Date createTime;
	//用户姓名
	private String name;
	//手机号
	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
