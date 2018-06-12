package com.rmkj.microcap.modules.trade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renwp on 2016/12/30.
 */
public class TradeBean1 {
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

    private String offTime;
    private String mobile;

    //会员姓名
    private String uname;

    private String agentMobile;
    private String unitsName;
    private String offPoint;

    private String holdNum;
    private String serviceFee;
    private String goodsName;
    private String goodsId;
    private String feeBuy;
    private String feeSell;

    public String getHoldNum() {
        return holdNum;
    }

    public void setHoldNum(String holdNum) {
        this.holdNum = holdNum;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
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

    public String getFeeBuy() {
        return feeBuy;
    }

    public void setFeeBuy(String feeBuy) {
        this.feeBuy = feeBuy;
    }

    public String getFeeSell() {
        return feeSell;
    }

    public void setFeeSell(String feeSell) {
        this.feeSell = feeSell;
    }

    public String getOffPoint() {
        return offPoint;
    }

    public void setOffPoint(String offPoint) {
        this.offPoint = offPoint;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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

    public BigDecimal getPointValue() {
        return pointValue;
    }

    public void setPointValue(BigDecimal pointValue) {
        this.pointValue = pointValue;
    }

    public Integer getProfitMax() {
        return profitMax;
    }

    public void setProfitMax(Integer profitMax) {
        this.profitMax = profitMax;
    }

    public Integer getLossMax() {
        return lossMax;
    }

    public void setLossMax(Integer lossMax) {
        this.lossMax = lossMax;
    }

    public Integer getBuyUpDown() {
        return buyUpDown;
    }

    public void setBuyUpDown(Integer buyUpDown) {
        this.buyUpDown = buyUpDown;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(BigDecimal buyPoint) {
        this.buyPoint = buyPoint;
    }

    public BigDecimal getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(BigDecimal sellPoint) {
        this.sellPoint = sellPoint;
    }

    public BigDecimal getDifMoney() {
        return difMoney;
    }

    public void setDifMoney(BigDecimal difMoney) {
        this.difMoney = difMoney;
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }
}
