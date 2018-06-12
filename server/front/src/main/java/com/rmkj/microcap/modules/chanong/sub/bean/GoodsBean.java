package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/4/28.
 */
public class GoodsBean {
    private String id;
    private String imgLoadPath;
    private String goodsName;
    private int goodsTotalNum;//商品总数量
    private int goodsLeftNum;//商品剩余数量
    private BigDecimal subScale;//认购比例
    private BigDecimal goodsSubPrice;//商品原始价
    private BigDecimal goodsCost;//商品价格
    private String userMobile; //商品所属客户手机号
    private String userId;     //商品所属客户id
    private String goodsCode;  //商品代码
    private BigDecimal upAndDownPercent; //挂单时最新价格上下区间百分比
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getUpAndDownPercent() {
        return upAndDownPercent;
    }

    public void setUpAndDownPercent(BigDecimal upAndDownPercent) {
        this.upAndDownPercent = upAndDownPercent;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgLoadPath() {
        return imgLoadPath;
    }

    public void setImgLoadPath(String imgLoadPath) {
        this.imgLoadPath = imgLoadPath;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsTotalNum() {
        return goodsTotalNum;
    }

    public void setGoodsTotalNum(int goodsTotalNum) {
        this.goodsTotalNum = goodsTotalNum;
    }

    public int getGoodsLeftNum() {
        return goodsLeftNum;
    }

    public void setGoodsLeftNum(int goodsLeftNum) {
        this.goodsLeftNum = goodsLeftNum;
    }

    public BigDecimal getSubScale() {
        return subScale;
    }

    public void setSubScale(BigDecimal subScale) {
        this.subScale = subScale;
    }

    public BigDecimal getGoodsSubPrice() {
        return goodsSubPrice;
    }

    public void setGoodsSubPrice(BigDecimal goodsSubPrice) {
        this.goodsSubPrice = goodsSubPrice;
    }

    public BigDecimal getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(BigDecimal goodsCost) {
        this.goodsCost = goodsCost;
    }
}
