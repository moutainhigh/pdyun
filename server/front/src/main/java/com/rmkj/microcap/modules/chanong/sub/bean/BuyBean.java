package com.rmkj.microcap.modules.chanong.sub.bean;

/**
 * Created by jinghao on 2018/5/16.
 */
public class BuyBean {
    private int buyNum;
    private String buyPoints;
    private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public String getBuyPoints() {
        return buyPoints;
    }

    public void setBuyPoints(String buyPoints) {
        this.buyPoints = buyPoints;
    }
}
