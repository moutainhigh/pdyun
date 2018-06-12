package com.rmkj.microcap.modules.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class ArticleBean extends DataEntity {
    //序号
    private Integer sortNo;
    //标题
    private String title;
    //图文详情
    private String content;
    //状态：0 正常 1 关闭
    private Integer status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
	//创建人
	private String	loginname;
//更新人
	private String updatename;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setSortNo(Integer sortNo){
		this.sortNo=sortNo;
	}
	public Integer getSortNo(){
		return this.sortNo;
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
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
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
