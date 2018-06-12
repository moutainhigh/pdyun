package com.rmkj.microcap.modules.agentmoneychange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-7.
*/
public class AgentMoneyChangeBean extends DataEntity {
    //经纪人ID
    private String agentId;
    //变更金额
    private BigDecimal difMoney;
    //类型 0 交易返佣 1 提现
    private Integer type;
    //变更前资金
    private BigDecimal beforeMoney;
    //变更后金额
    private BigDecimal afterMoney;
    //
    private String targetId;
    //创建时间
    private Date createTime;
	//经纪人姓名
	private String name;
	//经纪人手机号
	private String mobile;
	//会员用户姓名
	private String username;
	//交易流水号
	private String serialNo;
	//交易金额
	private String trademoney;
	//手续费
	private BigDecimal fee;
	//交割时间
	private Date selltime;
	//备注
	private String remark;
	//会员用户手机号
	private String usermobile;

	//时间段
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMax;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSellTimeMin() {
		return sellTimeMin;
	}

	public void setSellTimeMin(Date sellTimeMin) {
		this.sellTimeMin = sellTimeMin;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSellTimeMax() {
		return sellTimeMax;
	}

	public void setSellTimeMax(Date sellTimeMax) {
		this.sellTimeMax = sellTimeMax;
	}

	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTrademoney() {
		return trademoney;
	}

	public void setTrademoney(String trademoney) {
		this.trademoney = trademoney;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSelltime() {
		return selltime;
	}

	public void setSelltime(Date selltime) {
		this.selltime = selltime;
	}

	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setDifMoney(BigDecimal difMoney){
		this.difMoney=difMoney;
	}
	public BigDecimal getDifMoney(){
		return this.difMoney;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return this.type;
	}
	public void setBeforeMoney(BigDecimal beforeMoney){
		this.beforeMoney=beforeMoney;
	}
	public BigDecimal getBeforeMoney(){
		return this.beforeMoney;
	}
	public void setAfterMoney(BigDecimal afterMoney){
		this.afterMoney=afterMoney;
	}
	public BigDecimal getAfterMoney(){
		return this.afterMoney;
	}
	public void setTargetId(String targetId){
		this.targetId=targetId;
	}
	public String getTargetId(){
		return this.targetId;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}

}
