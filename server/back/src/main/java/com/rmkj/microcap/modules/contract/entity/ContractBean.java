package com.rmkj.microcap.modules.contract.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class ContractBean extends DataEntity {
    //名称
    private String name;
    //代码
    private String code;
    //序号
    private Integer orderNo;
    //状态：0 正常 1 关闭
    private Integer status;
    //购买金额 格式如下 10,50,100,200,500,2000（英文逗号）
    private String stepMoneys;
    //波动一个点，盈亏多少钱 （同购买金额）
    private String pointMoneys;
    //手续费（同购买金额）
    private String fees;
    //盈利百分比
    private String profitPercentages;
    //亏损百分比
    private String lossPercentages;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
	//开始时间
	private String beginTime;
	//结束时间
	private String endTime;
	//时间间隔
	private String offTimes;
	//收益率
	private String percentProfits;
	//点位
	private String offPoints;

	//交易模型：0  微盘模型 1 微交易时间模型 2 微交易点位模型 3 全部模式
	private String model;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOffPoints() {
		return offPoints;
	}

	public void setOffPoints(String offPoints) {
		this.offPoints = offPoints;
	}

	public String getOffTimes() {
		return offTimes;
	}

	public void setOffTimes(String offTimes) {
		this.offTimes = offTimes;
	}

	public String getPercentProfits() {
		return percentProfits;
	}

	public void setPercentProfits(String percentProfits) {
		this.percentProfits = percentProfits;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return this.code;
	}
	public void setOrderNo(Integer orderNo){
		this.orderNo=orderNo;
	}
	public Integer getOrderNo(){
		return this.orderNo;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setStepMoneys(String stepMoneys){
		this.stepMoneys=stepMoneys;
	}
	public String getStepMoneys(){
		return this.stepMoneys;
	}
	public void setPointMoneys(String pointMoneys){
		this.pointMoneys=pointMoneys;
	}
	public String getPointMoneys(){
		return this.pointMoneys;
	}
	public void setFees(String fees){
		this.fees=fees;
	}
	public String getFees(){
		return this.fees;
	}
	public void setProfitPercentages(String profitPercentages){
		this.profitPercentages=profitPercentages;
	}
	public String getProfitPercentages(){
		return this.profitPercentages;
	}
	public void setLossPercentages(String lossPercentages){
		this.lossPercentages=lossPercentages;
	}
	public String getLossPercentages(){
		return this.lossPercentages;
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
