package com.rmkj.microcap.modules.ReturnMoneyOut.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-12-7.
*/
public class ReturnMoneyOutBean extends DataEntity {
    //流水号
    private String serialNo;
    //会员用户id
    private String userId;
    //金额
    private BigDecimal money;
    //手续费
    private BigDecimal fee;
    //第三方流水号
    private String thirdSerialNo;
    //状态 0 处理中 1 成功 2 失败
    private Integer status;
    //创建时间
    private Date createTime;
    //完成时间
    private Date completeTime;
    //姓名
    private String chnName;
    //银行卡号
    private String bankAccount;
    //银行名称
    private String bankName;
    //开户行名称
    private String openBankName;
    //省
    private String province;
    //市
    private String city;
    //失败原因
    private String failureReason;
	//备注信息
	private String remark;
	//军团长真是姓名
	private String userChnName;
	private Integer reviewStatus;
	private String lianHangNo;
private String userMobile;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMin;
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

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getLianHangNo() {
		return lianHangNo;
	}

	public void setLianHangNo(String lianHangNo) {
		this.lianHangNo = lianHangNo;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getUserChnName() {
		return userChnName;
	}

	public void setUserChnName(String userChnName) {
		this.userChnName = userChnName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSerialNo(String serialNo){
		this.serialNo=serialNo;
	}
	public String getSerialNo(){
		return this.serialNo;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	public BigDecimal getMoney(){
		return this.money;
	}
	public void setFee(BigDecimal fee){
		this.fee=fee;
	}
	public BigDecimal getFee(){
		return this.fee;
	}
	public void setThirdSerialNo(String thirdSerialNo){
		this.thirdSerialNo=thirdSerialNo;
	}
	public String getThirdSerialNo(){
		return this.thirdSerialNo;
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
	public void setCompleteTime(Date completeTime){
		this.completeTime=completeTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompleteTime(){
		return this.completeTime;
	}
	public void setChnName(String chnName){
		this.chnName=chnName;
	}
	public String getChnName(){
		return this.chnName;
	}
	public void setBankAccount(String bankAccount){
		this.bankAccount=bankAccount;
	}
	public String getBankAccount(){
		return this.bankAccount;
	}
	public void setBankName(String bankName){
		this.bankName=bankName;
	}
	public String getBankName(){
		return this.bankName;
	}
	public void setOpenBankName(String openBankName){
		this.openBankName=openBankName;
	}
	public String getOpenBankName(){
		return this.openBankName;
	}
	public void setProvince(String province){
		this.province=province;
	}
	public String getProvince(){
		return this.province;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return this.city;
	}
	public void setFailureReason(String failureReason){
		this.failureReason=failureReason;
	}
	public String getFailureReason(){
		return this.failureReason;
	}

}
