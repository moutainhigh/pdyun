package com.rmkj.microcap.modules.parameterset.entity;

import com.rmkj.microcap.common.bean.DataEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* Created by Administrator on 2016-12-16.
*/
public class ParameterSetBean extends DataEntity {
    //最大持仓单数
    private Integer holdCount;
    //最大持仓金额
    private BigDecimal holdMoney;
    //提现XXXX元以下系统实时返现
    private BigDecimal cashMoney;
    //每日提现限额
    private BigDecimal cashMoneyRation;
    //每日提现次数
    private Integer cashMoneyCount;
	//推广二维码url
	private String qrCodeUrl;
	//微信菜单url
	private String qrCodeMenuUrl;

	//最低建仓金额
	private BigDecimal ordersMinMoney;
	//每种商品同时持仓单数
	private Integer holdOrdersCount;

	@Min(value = 0, message = "最小返佣比例为0%")
	private String percentBuBing;

	@Min(value = 0, message = "最小返佣比例为0%")
	private String percentQiBing;

	private BigDecimal subServiceScale;
	private BigDecimal subFeeScale;
	private BigDecimal integralReturnScale;
	private BigDecimal upAndDownPercent;
	private String weekDaySet;
	private String openTime;
	private BigDecimal serviceFeeMoney;
	private BigDecimal serviceFeeMoneyTotal;
	private BigDecimal feeMoney;
	private BigDecimal feeMoneyTotal;

	public BigDecimal getServiceFeeMoney() {
		return serviceFeeMoney;
	}

	public void setServiceFeeMoney(BigDecimal serviceFeeMoney) {
		this.serviceFeeMoney = serviceFeeMoney;
	}

	public BigDecimal getServiceFeeMoneyTotal() {
		return serviceFeeMoneyTotal;
	}

	public void setServiceFeeMoneyTotal(BigDecimal serviceFeeMoneyTotal) {
		this.serviceFeeMoneyTotal = serviceFeeMoneyTotal;
	}

	public BigDecimal getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}

	public BigDecimal getFeeMoneyTotal() {
		return feeMoneyTotal;
	}

	public void setFeeMoneyTotal(BigDecimal feeMoneyTotal) {
		this.feeMoneyTotal = feeMoneyTotal;
	}

	public String getWeekDaySet() {
		return weekDaySet;
	}

	public void setWeekDaySet(String weekDaySet) {
		this.weekDaySet = weekDaySet;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public BigDecimal getUpAndDownPercent() {
		return upAndDownPercent;
	}

	public void setUpAndDownPercent(BigDecimal upAndDownPercent) {
		this.upAndDownPercent = upAndDownPercent;
	}

	public BigDecimal getIntegralReturnScale() {
		return integralReturnScale;
	}

	public void setIntegralReturnScale(BigDecimal integralReturnScale) {
		this.integralReturnScale = integralReturnScale;
	}

	public BigDecimal getSubServiceScale() {
		return subServiceScale;
	}

	public void setSubServiceScale(BigDecimal subServiceScale) {
		this.subServiceScale = subServiceScale;
	}

	public BigDecimal getSubFeeScale() {
		return subFeeScale;
	}

	public void setSubFeeScale(BigDecimal subFeeScale) {
		this.subFeeScale = subFeeScale;
	}

	public String getPercentBuBing() {
		return percentBuBing;
	}

	public void setPercentBuBing(String percentBuBing) {
		this.percentBuBing = percentBuBing;
	}

	public String getPercentQiBing() {
		return percentQiBing;
	}

	public void setPercentQiBing(String percentQiBing) {
		this.percentQiBing = percentQiBing;
	}

	public BigDecimal getOrdersMinMoney() {
		return ordersMinMoney;
	}

	public void setOrdersMinMoney(BigDecimal ordersMinMoney) {
		this.ordersMinMoney = ordersMinMoney;
	}

	public Integer getHoldOrdersCount() {
		return holdOrdersCount;
	}

	public void setHoldOrdersCount(Integer holdOrdersCount) {
		this.holdOrdersCount = holdOrdersCount;
	}

	public String getQrCodeMenuUrl() {
		return qrCodeMenuUrl;
	}

	public void setQrCodeMenuUrl(String qrCodeMenuUrl) {
		this.qrCodeMenuUrl = qrCodeMenuUrl;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public void setHoldCount(Integer holdCount){
		this.holdCount=holdCount;
	}
	public Integer getHoldCount(){
		return this.holdCount;
	}
	public void setHoldMoney(BigDecimal holdMoney){
		this.holdMoney=holdMoney;
	}
	public BigDecimal getHoldMoney(){
		return this.holdMoney;
	}
	public void setCashMoney(BigDecimal cashMoney){
		this.cashMoney=cashMoney;
	}
	public BigDecimal getCashMoney(){
		return this.cashMoney;
	}
	public void setCashMoneyRation(BigDecimal cashMoneyRation){
		this.cashMoneyRation=cashMoneyRation;
	}
	public BigDecimal getCashMoneyRation(){
		return this.cashMoneyRation;
	}
	public void setCashMoneyCount(Integer cashMoneyCount){
		this.cashMoneyCount=cashMoneyCount;
	}
	public Integer getCashMoneyCount(){
		return this.cashMoneyCount;
	}

}
