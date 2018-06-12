package com.rmkj.microcap.modules.holiday.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class HolidayModelBean extends DataEntity {
    //假日名称
    private String name;
    //开始日期
    private Date startDate;
    //结束日期
    private Date endDate;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setStartDate(Date startDate){
		this.startDate=startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate(){
		return this.startDate;
	}
	public void setEndDate(Date endDate){
		this.endDate=endDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate(){
		return this.endDate;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime(){
		return this.updateTime;
	}

}
