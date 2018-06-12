package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinghao on 2018/4/27.
 */
public class SubGoodsBean {
    private String id;
    private String imgLoadPath;
    private String goodsName;
    private int goodsTotalNum;//商品总数量
    private int goodsLeftNum;//商品剩余数量
    private BigDecimal subScale;//认购比例
    private BigDecimal goodsSubPrice;//商品原始价
    private BigDecimal goodsCost;//商品价格
    private String subTotalNum;
    private String subMakeNum;
    private String subSendNum;
    private List<String> specList = new ArrayList<>();
    private int subGoodsNum;        //可以认购的总数量
    private int subGoodsLeftNum;    //可以认购的剩余数量
    private int subDays;
    private String subGoodsSpec;    //规格
    private int subBuyNum;//规格买入的数量
    private String goodsCode;

    //买卖街最低价
    private BigDecimal buyPointMin;
    //买卖街最新成交价
    private BigDecimal sellPointNew;

    public BigDecimal getBuyPointMin() {
        return buyPointMin;
    }

    public void setBuyPointMin(BigDecimal buyPointMin) {
        this.buyPointMin = buyPointMin;
    }

    public BigDecimal getSellPointNew() {
        return sellPointNew;
    }

    public void setSellPointNew(BigDecimal sellPointNew) {
        this.sellPointNew = sellPointNew;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getSubBuyNum() {
        return subBuyNum;
    }

    public void setSubBuyNum(int subBuyNum) {
        this.subBuyNum = subBuyNum;
    }

    public String getSubGoodsSpec() {
        return subGoodsSpec;
    }

    public void setSubGoodsSpec(String subGoodsSpec) {
        this.subGoodsSpec = subGoodsSpec;
    }

    public BigDecimal getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(BigDecimal goodsCost) {
        this.goodsCost = goodsCost;
    }

    public int getSubDays() {
        return subDays;
    }

    public void setSubDays(int subDays) {
        this.subDays = subDays;
    }

    public int getSubGoodsNum() {
        return subGoodsNum;
    }

    public void setSubGoodsNum(int subGoodsNum) {
        this.subGoodsNum = subGoodsNum;
    }

    public int getSubGoodsLeftNum() {
        return subGoodsLeftNum;
    }

    public void setSubGoodsLeftNum(int subGoodsLeftNum) {
        this.subGoodsLeftNum = subGoodsLeftNum;
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

    public String getSubTotalNum() {
        return subTotalNum;
    }

    public void setSubTotalNum(String subTotalNum) {
        this.subTotalNum = subTotalNum;
    }

    public String getSubMakeNum() {
        return subMakeNum;
    }

    public void setSubMakeNum(String subMakeNum) {
        this.subMakeNum = subMakeNum;
    }

    public String getSubSendNum() {
        return subSendNum;
    }

    public void setSubSendNum(String subSendNum) {
        this.subSendNum = subSendNum;
    }

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }
}
