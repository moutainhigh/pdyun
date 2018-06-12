package com.rmkj.microcap.modules.cashcoupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class CashCouponBean extends DataEntity {
    //面值
    private Integer money;
    //类型 （字典表 cash_coupon_type）
    private Integer type;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
	//类型
	private String dicttype;
//标签
	private String lable;
	//排序
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public void setMoney(Integer money){
		this.money=money;
	}
	public Integer getMoney(){
		return this.money;
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
