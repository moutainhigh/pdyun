package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/5/2.
 */
public class BuyHangBean {
    private String goodsId;     //所买挂单商品id
    private String goodsName;   //所买挂单商品名称
    private int holdNum;     //所买挂单商品数量
    private String userId;      //购买人id
    private String hangUserPhone;//挂单人手机号
    private BigDecimal curPrice;//商品当前价格
    private String hangOrderId; //挂单记录的id
    private BigDecimal feeBuy;  //手续费
    private BigDecimal buyMoney;//购买金额

    public String getHangUserPhone() {
        return hangUserPhone;
    }

    public void setHangUserPhone(String hangUserPhone) {
        this.hangUserPhone = hangUserPhone;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(BigDecimal curPrice) {
        this.curPrice = curPrice;
    }

    public String getHangOrderId() {
        return hangOrderId;
    }

    public void setHangOrderId(String hangOrderId) {
        this.hangOrderId = hangOrderId;
    }

    public BigDecimal getFeeBuy() {
        return feeBuy;
    }

    public void setFeeBuy(BigDecimal feeBuy) {
        this.feeBuy = feeBuy;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal buyMoney) {
        this.buyMoney = buyMoney;
    }
}
