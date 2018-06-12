package com.rmkj.microcap.modules.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-10-17.
*/
public class TradeBean extends DataEntity {
    //流水号
    private String serialNo;
    //会员用户id
    private String userId;
    //购买金额
    private BigDecimal money;
	private BigDecimal couponMoney;
	private String couponType;
    //类型 0 资金 1 代金券
    private Integer type;
    //手续费
    private BigDecimal fee;
	//合约代码
	private String code;
    //合约名称
    private String contractName;
    //波动一个点，盈亏多少钱
    private BigDecimal pointValue;
    //止盈百分比
    private Integer profitMax;
    //止损值
    private Integer lossMax;
    //买涨买跌 0 买涨 1 买跌
    private Integer buyUpDown;
    //状态：0 持仓 1 平仓（交割）
    private Integer status;
    //建仓时间
    private Date buyTime;
    //建仓价
    private BigDecimal buyPoint;
    //平仓价
    private BigDecimal sellPoint;
    //盈亏金额（含手续费）
    private BigDecimal difMoney;
    //平仓类型 0 手动平仓 1 止盈止损平仓 2 休市平仓
    private Integer sellType;
    //平仓（交割）时间
    private Date sellTime;

	//会员姓名
	private String uname;
	//会员手机号
	private String mobile;
	//时间间隔
	private String offTime;
	//会员用户姓名
	private String userChnName;
	//军团长姓名
	private String junTuanChnName;
	//合伙人
	private String junTuanMobile;
	//会员用户手机号
	private String userMobile;
	//会员用户所属军团
	private String jType;
	//返给炮兵团的金额
	private BigDecimal return1Money;
	//返给骑兵团的金额
	private BigDecimal return2Money;
	//返给步兵团的金额
	private BigDecimal return3Money;
	//交易总金额
	private BigDecimal totalMoney;
	//交易总盈亏
	private BigDecimal totalDifMoney;
	//总佣金
	private BigDecimal totalReturn;

	//会员单位真实姓名
	private String unitsRealName;
	//输入的会员单位名称
	private String unitsName;
	//代理商账号
	private String account;
	//代理商姓名
	private String agentRealName;
	//代理商手机号
	private String agentMobile;

	private String unitsId;
	private String agentId;
	private String junId;
	private String centerId;

	private String startDate;
	private String endDate;
	private String umobile;
	private String offPoint;

	private BigDecimal feeSum;

	private BigDecimal moneyTotal;

	/** 新增参数 **/
	//持仓数量
	private int holdNum;
	//服务费
	private BigDecimal serviceFee;
	private String goodsName;
	private String goodsId;
	private BigDecimal feeBuy;
	private BigDecimal feeSell;
	private String chnName;
	private String holdFlag;

	private String parent1Id;
	private String parent2Id;
	private String parent3Id;
	private String oldTradeSerialNo;

	public String getParent3Id() {
		return parent3Id;
	}

	public String getOldTradeSerialNo() {
		return oldTradeSerialNo;
	}

	public void setOldTradeSerialNo(String oldTradeSerialNo) {
		this.oldTradeSerialNo = oldTradeSerialNo;
	}

	public String getParent1Id() {
		return parent1Id;
	}

	public void setParent1Id(String parent1Id) {
		this.parent1Id = parent1Id;
	}

	public String getParent2Id() {
		return parent2Id;
	}

	public void setParent2Id(String parent2Id) {
		this.parent2Id = parent2Id;
	}

	public void setParent3Id(String parent3Id) {
		this.parent3Id = parent3Id;
	}

	public String getHoldFlag() {
		return holdFlag;
	}

	public void setHoldFlag(String holdFlag) {
		this.holdFlag = holdFlag;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getFeeBuy() {
		return feeBuy;
	}

	public void setFeeBuy(BigDecimal feeBuy) {
		this.feeBuy = feeBuy;
	}

	public BigDecimal getFeeSell() {
		return feeSell;
	}

	public void setFeeSell(BigDecimal feeSell) {
		this.feeSell = feeSell;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public int getHoldNum() {
		return holdNum;
	}

	public void setHoldNum(int holdNum) {
		this.holdNum = holdNum;
	}

	public BigDecimal getFeeSum() {
		return feeSum;
	}

	public void setFeeSum(BigDecimal feeSum) {
		this.feeSum = feeSum;
	}

	public BigDecimal getMoneyTotal() {
		return moneyTotal;
	}

	public void setMoneyTotal(BigDecimal moneyTotal) {
		this.moneyTotal = moneyTotal;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getOffPoint() {
		return offPoint;
	}

	public void setOffPoint(String offPoint) {
		this.offPoint = offPoint;
	}

	public String getUmobile() {
		return umobile;
	}

	public void setUmobile(String umobile) {
		this.umobile = umobile;
	}

	public String getUnitsId() {
		return unitsId;
	}

	public void setUnitsId(String unitsId) {
		this.unitsId = unitsId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getJunId() {
		return junId;
	}

	public void setJunId(String junId) {
		this.junId = junId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getTotalDifMoney() {
		return totalDifMoney;
	}

	public void setTotalDifMoney(BigDecimal totalDifMoney) {
		this.totalDifMoney = totalDifMoney;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUnitsRealName() {
		return unitsRealName;
	}

	public void setUnitsRealName(String unitsRealName) {
		this.unitsRealName = unitsRealName;
	}

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

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	//时间段
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMax;


	//输入的合伙人
	private String jtMobile;

	public String getJtMobile() {
		return jtMobile;
	}

	public void setJtMobile(String jtMobile) {
		this.jtMobile = jtMobile;
	}

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
	public BigDecimal getReturn1Money() {
		return return1Money;
	}

	public void setReturn1Money(BigDecimal return1Money) {
		this.return1Money = return1Money;
	}

	public BigDecimal getReturn2Money() {
		return return2Money;
	}

	public void setReturn2Money(BigDecimal return2Money) {
		this.return2Money = return2Money;
	}

	public BigDecimal getReturn3Money() {
		return return3Money;
	}

	public void setReturn3Money(BigDecimal return3Money) {
		this.return3Money = return3Money;
	}

	public String getjType() {
		return jType;
	}

	public void setjType(String jType) {
		this.jType = jType;
	}

	public String getJunTuanMobile() {
		return junTuanMobile;
	}

	public void setJunTuanMobile(String junTuanMobile) {
		this.junTuanMobile = junTuanMobile;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserChnName() {
		return userChnName;
	}

	public void setUserChnName(String userChnName) {
		this.userChnName = userChnName;
	}

	public String getJunTuanChnName() {
		return junTuanChnName;
	}

	public void setJunTuanChnName(String junTuanChnName) {
		this.junTuanChnName = junTuanChnName;
	}

	public String getOffTime() {
		return offTime;
	}

	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return this.type;
	}
	public void setFee(BigDecimal fee){
		this.fee=fee;
	}
	public BigDecimal getFee(){
		return this.fee;
	}
	public void setContractName(String contractName){
		this.contractName=contractName;
	}
	public String getContractName(){
		return this.contractName;
	}
	public void setPointValue(BigDecimal pointValue){
		this.pointValue=pointValue;
	}
	public BigDecimal getPointValue(){
		return this.pointValue;
	}
	public void setProfitMax(Integer profitMax){
		this.profitMax=profitMax;
	}
	public Integer getProfitMax(){
		return this.profitMax;
	}
	public void setLossMax(Integer lossMax){
		this.lossMax=lossMax;
	}
	public Integer getLossMax(){
		return this.lossMax;
	}
	public void setBuyUpDown(Integer buyUpDown){
		this.buyUpDown=buyUpDown;
	}
	public Integer getBuyUpDown(){
		return this.buyUpDown;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setBuyTime(Date buyTime){
		this.buyTime=buyTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuyTime(){
		return this.buyTime;
	}
	public void setBuyPoint(BigDecimal buyPoint){
		this.buyPoint=buyPoint;
	}
	public BigDecimal getBuyPoint(){
		return this.buyPoint;
	}
	public void setSellPoint(BigDecimal sellPoint){
		this.sellPoint=sellPoint;
	}
	public BigDecimal getSellPoint(){
		return this.sellPoint;
	}
	public void setDifMoney(BigDecimal difMoney){
		this.difMoney=difMoney;
	}
	public BigDecimal getDifMoney(){
		return this.difMoney;
	}
	public void setSellType(Integer sellType){
		this.sellType=sellType;
	}
	public Integer getSellType(){
		return this.sellType;
	}
	public void setSellTime(Date sellTime){
		this.sellTime=sellTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSellTime(){
		return this.sellTime;
	}

}
