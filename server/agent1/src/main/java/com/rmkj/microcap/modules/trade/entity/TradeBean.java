package com.rmkj.microcap.modules.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016-11-22.
 */
public class TradeBean extends DataEntity {
	//交易模型：0  微盘模型 1 微交易时间模型 2 微交易点位模型
	private Integer model;
	//流水号
	private String serialNo;
	//会员用户id
	private String userId;
	//购买金额
	private BigDecimal money;
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
	//时间 单位分钟(微交易时间模式)
	private String offTime;
	//点数（微交易点数模式）
	private String offPoint;
	//买涨买跌 0 买涨 1 买跌
	private Integer buyUpDown;
	//状态：0 持仓 1 平仓（交割）
	private Integer status;
	//建仓时间
	private Date buyTime;
	//建仓价
	private BigDecimal buyPoint;
	//止盈最大行情值
	private BigDecimal profitMaxPoint;
	//止损最大行情值
	private BigDecimal lossMaxPoint;
	//平仓价
	private BigDecimal sellPoint;
	//盈亏金额（含手续费）
	private BigDecimal difMoney;
	//平仓类型 0 手动平仓 1 止盈止损平仓 2 休市平仓
	private Integer sellType;
	//平仓（交割）时间
	private Date sellTime;
	//代理商ID
	private String agentId;
	//会员单位ID
	private String unitsId;
	//市场管理部ID
	private String centerId;
	//全民经纪人id
	private String brokerId;
	private String chnName;
	private String mobile;
	private String realName;
	private BigDecimal money1;
	private BigDecimal rechargeMoney;
	private BigDecimal outMoney;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTimeMax;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date registerTime;
	private BigDecimal totalMoney;
	private BigDecimal totalReturn;
	private String uname;
	//输入的合伙人
	private String jtMobile;
	private String userMobile;
	private String userChnName;
	private String junTuanChnName;
	private String junTuanMobile;
	private BigDecimal return1Money;
	private BigDecimal return2Money;
	private BigDecimal return3Money;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sellTimeMax;
	private String jType;
	private String agentRealName;
	private String agentMobile;
	private BigDecimal totalDifMoney;

	/** 品道新增参数 **/
	//持仓数量
	private int holdNum;
	//服务费
	private BigDecimal serviceFee;
	private String goodsName;
	private String goodsId;
	private BigDecimal feeBuy;
	private BigDecimal feeSell;

	public int getHoldNum() {
		return holdNum;
	}

	public void setHoldNum(int holdNum) {
		this.holdNum = holdNum;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
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

	public BigDecimal getTotalDifMoney() {
		return totalDifMoney;
	}

	public void setTotalDifMoney(BigDecimal totalDifMoney) {
		this.totalDifMoney = totalDifMoney;
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

	public String getjType() {
		return jType;
	}

	public void setjType(String jType) {
		this.jType = jType;
	}

	public Date getSellTimeMin() {
		return sellTimeMin;
	}

	public void setSellTimeMin(Date sellTimeMin) {
		this.sellTimeMin = sellTimeMin;
	}

	public Date getSellTimeMax() {
		return sellTimeMax;
	}

	public void setSellTimeMax(Date sellTimeMax) {
		this.sellTimeMax = sellTimeMax;
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

	public String getJunTuanMobile() {
		return junTuanMobile;
	}

	public void setJunTuanMobile(String junTuanMobile) {
		this.junTuanMobile = junTuanMobile;
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

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getJtMobile() {
		return jtMobile;
	}

	public void setJtMobile(String jtMobile) {
		this.jtMobile = jtMobile;
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

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public BigDecimal getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(BigDecimal rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

	public BigDecimal getMoney1() {
		return money1;
	}

	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setModel(Integer model){
		this.model=model;
	}
	public Integer getModel(){
		return this.model;
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
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return this.code;
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
	public void setOffTime(String offTime){
		this.offTime=offTime;
	}
	public String getOffTime(){
		return this.offTime;
	}
	public void setOffPoint(String offPoint){
		this.offPoint=offPoint;
	}
	public String getOffPoint(){
		return this.offPoint;
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
	public void setProfitMaxPoint(BigDecimal profitMaxPoint){
		this.profitMaxPoint=profitMaxPoint;
	}
	public BigDecimal getProfitMaxPoint(){
		return this.profitMaxPoint;
	}
	public void setLossMaxPoint(BigDecimal lossMaxPoint){
		this.lossMaxPoint=lossMaxPoint;
	}
	public BigDecimal getLossMaxPoint(){
		return this.lossMaxPoint;
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
	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setUnitsId(String unitsId){
		this.unitsId=unitsId;
	}
	public String getUnitsId(){
		return this.unitsId;
	}
	public void setCenterId(String centerId){
		this.centerId=centerId;
	}
	public String getCenterId(){
		return this.centerId;
	}
	public void setBrokerId(String brokerId){
		this.brokerId=brokerId;
	}
	public String getBrokerId(){
		return this.brokerId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
}
