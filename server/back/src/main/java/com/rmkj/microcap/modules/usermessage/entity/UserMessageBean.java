package com.rmkj.microcap.modules.usermessage.entity;

import com.rmkj.microcap.common.bean.DataEntity;
import java.util.Date;

/**
* Created by Administrator on 2016-11-4.
*/
public class UserMessageBean extends DataEntity {
    //会员用户id
    private String userId;
    //标题
    private String title;
    //内容
    private String content;
    //0 未读 1 已读
    private Integer readStatus;
    //0 默认
    private Integer type;
    //创建时间
    private Date createTime;

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return this.content;
	}
	public void setReadStatus(Integer readStatus){
		this.readStatus=readStatus;
	}
	public Integer getReadStatus(){
		return this.readStatus;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return this.type;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return this.createTime;
	}

}
