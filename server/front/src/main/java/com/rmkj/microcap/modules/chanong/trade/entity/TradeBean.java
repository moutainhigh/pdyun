package com.rmkj.microcap.modules.chanong.trade.entity;/**
 * Created by Administrator on 2018/5/2.
 */

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;

/**
 * @author k
 * @create -05-02-8:37
 **/

public class TradeBean extends DataEntity {
    private String serialNo;

    private String userId;

    private String holdNum;

    private String serviceFee;

    private String money;

    private String goodsName;

    private String goodsId;

    private String feeBuy;

    private String feeSell;

    private String buyPoint;

    private BigDecimal sellPoint;

    public BigDecimal getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(BigDecimal sellPoint) {
        this.sellPoint = sellPoint;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(String buyPoint) {
        this.buyPoint = buyPoint;
    }
}
