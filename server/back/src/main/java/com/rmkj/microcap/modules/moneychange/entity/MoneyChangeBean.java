package com.rmkj.microcap.modules.moneychange.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class MoneyChangeBean extends DataEntity {
	private String id;
    //会员用户ID
    private String userId;
    //变更金额
    private BigDecimal difMoney;
    //类型 0 充值 1 提现 2 建仓 3 平仓
    private Integer type;
    //变更前资金
    private BigDecimal beforeMoney;
    //变更后金额
    private BigDecimal afterMoney;
    //创建时间
    private Date createTime;
	//备注
	private String remark;
	private String uname;
	private String mobile;

	//合约代码
	private String code;
	//合约名称
	private String contractName;
	//会员单位Name
	private String unitsName;
	//代理商Name
	private String agentRealName;

	public String getUnitsName() {
		return unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
	}

	public String getAgentRealName() {
		return agentRealName;
	}

	public void setAgentRealName(String agentRealName) {
		this.agentRealName = agentRealName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMin;
	//结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMax;

	public Date getCreateTimeMin() {
		return createTimeMin;
	}

	public void setCreateTimeMin(Date createTimeMin) {
		this.createTimeMin = createTimeMin;
	}

	public Date getCreateTimeMax() {
		return createTimeMax;
	}

	public void setCreateTimeMax(Date createTimeMax) {
		this.createTimeMax = createTimeMax;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return this.userId;
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
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}

}
