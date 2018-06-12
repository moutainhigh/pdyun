package com.rmkj.microcap.modules.agentreviewrecord.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.util.Date;

/**
* Created by Administrator on 2016-11-7.
*/
public class AgentReviewRecordBean extends DataEntity {
    //经纪人id
    private String agentId;
    //后台人员id
    private String sysUserId;
    //审核结果：1 通过 2 不通过
    private Integer resultStatus;
    //0 未读 1 已读
    private Integer readStatus;
    //
    private Date createTime;
	//失败原因
	private String remark;
	//经纪人姓名
	private String name;
	//管理员姓名
	private String loginname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setSysUserId(String sysUserId){
		this.sysUserId=sysUserId;
	}
	public String getSysUserId(){
		return this.sysUserId;
	}
	public void setResultStatus(Integer resultStatus){
		this.resultStatus=resultStatus;
	}
	public Integer getResultStatus(){
		return this.resultStatus;
	}
	public void setReadStatus(Integer readStatus){
		this.readStatus=readStatus;
	}
	public Integer getReadStatus(){
		return this.readStatus;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}

}
