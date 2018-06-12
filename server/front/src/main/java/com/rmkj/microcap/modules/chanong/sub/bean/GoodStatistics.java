package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/5/16.
 */
public class GoodStatistics {
    private int todayTotalNum;              //'今日销售数量'
    private BigDecimal todayTotalMoney;     //'今日销售总额'
    private BigDecimal averagePrice;        //'平均价格'
    private int totalLeftNum;               //'库存总量'
    private BigDecimal firstPrice;          //'首单价格'
    private BigDecimal maxPrice;            //'最高价格'
    private BigDecimal minPrice;            //'最低价格'
    private int forSell;                    //'待售数量'
    private String goodsName;
    private String goodsCode;
    private String goodsId;

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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getTodayTotalNum() {
        return todayTotalNum;
    }

    public void setTodayTotalNum(int todayTotalNum) {
        this.todayTotalNum = todayTotalNum;
    }

    public BigDecimal getTodayTotalMoney() {
        return todayTotalMoney;
    }

    public void setTodayTotalMoney(BigDecimal todayTotalMoney) {
        this.todayTotalMoney = todayTotalMoney;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getTotalLeftNum() {
        return totalLeftNum;
    }

    public void setTotalLeftNum(int totalLeftNum) {
        this.totalLeftNum = totalLeftNum;
    }

    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public int getForSell() {
        return forSell;
    }

    public void setForSell(int forSell) {
        this.forSell = forSell;
    }
}
