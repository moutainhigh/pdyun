package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/5/15.
 */
public class HangGoodsDetail {
    private String goodsId;
    private String goodsName;
    private int  totalHoldNum;
    private BigDecimal buyPoint;

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

    public int getTotalHoldNum() {
        return totalHoldNum;
    }

    public void setTotalHoldNum(int totalHoldNum) {
        this.totalHoldNum = totalHoldNum;
    }

    public BigDecimal getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(BigDecimal buyPoint) {
        this.buyPoint = buyPoint;
    }
}
