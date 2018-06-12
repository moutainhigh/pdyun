package com.rmkj.microcap.modules.syslog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;

import java.util.Date;

/**
* Created by Administrator on 2016-10-21.
*/
public class SysLogBean extends DataEntity {
	private String id;
    //用户ID
	//用户姓名
	private String loginName;
    private String mId;
    //日志内容
    private String lContent;
    //类型 0 无
    private Integer lType;
    //时间戳
    private Date lCreateTime;


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getlContent() {
		return lContent;
	}

	public void setlContent(String lContent) {
		this.lContent = lContent;
	}

	public Integer getlType() {
		return lType;
	}

	public void setlType(Integer lType) {
		this.lType = lType;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getlCreateTime() {
		return lCreateTime;
	}

	public void setlCreateTime(Date lCreateTime) {
		this.lCreateTime = lCreateTime;
	}
}
