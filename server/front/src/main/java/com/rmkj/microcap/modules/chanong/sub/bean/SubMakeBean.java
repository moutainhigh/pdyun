package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jinghao on 2018/4/28.
 */
public class SubMakeBean {
    private String id;
    private String serialNo; //流水号
    private String userId;   //用户id
    private String goodsId;  //购买商品id
    private String goodsName;//购买商品名称
    private int holdNum;  //购买数量
    private BigDecimal money;//购买金额
    private BigDecimal serviceFee; //服务费
    private BigDecimal feeBuy;  //手续费
    private BigDecimal feeSell;  //手续费
    private BigDecimal buyPoint; //买价
    private Date buyTime;    //建仓时间
    private BigDecimal sellPoint; //卖价
    private Date sellTime;   //卖出时间
    private String status;   //状态：1 持仓  2挂单 3 平仓（交割） 4 下架
    private BigDecimal difMoney; //盈亏
    private String centerId;
    private String unitsId;
    private String agentId;
    private String chnName;
    private String mobile;
    private Date hangTime;//挂单时间
    private String oldTradeSerialNo;
    private String imgLoadPath;
    private String remark;
    private String holdFlag;//持仓类型:   1-认购   2-买入
    private String parent1Id;
    private String parent2Id;
    private String parent3Id;

    public String getHoldFlag() {
        return holdFlag;
    }

    public void setHoldFlag(String holdFlag) {
        this.holdFlag = holdFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgLoadPath() {
        return imgLoadPath;
    }

    public void setImgLoadPath(String imgLoadPath) {
        this.imgLoadPath = imgLoadPath;
    }

    public String getOldTradeSerialNo() {
        return oldTradeSerialNo;
    }

    public void setOldTradeSerialNo(String oldTradeSerialNo) {
        this.oldTradeSerialNo = oldTradeSerialNo;
    }

    public Date getHangTime() {
        return hangTime;
    }

    public void setHangTime(Date hangTime) {
        this.hangTime = hangTime;
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

    public String getParent3Id() {
        return parent3Id;
    }

    public void setParent3Id(String parent3Id) {
        this.parent3Id = parent3Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getHoldNum() {
        return holdNum;
    }

    public void setHoldNum(int holdNum) {
        this.holdNum = holdNum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
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

    public BigDecimal getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(BigDecimal buyPoint) {
        this.buyPoint = buyPoint;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(BigDecimal sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDifMoney() {
        return difMoney;
    }

    public void setDifMoney(BigDecimal difMoney) {
        this.difMoney = difMoney;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
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
}
