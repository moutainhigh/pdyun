package com.rmkj.microcap.modules.subGoods.entity;/**
 * Created by Administrator on 2018/4/26.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author k
 * @create -04-26-12:24
 **/

public class SubGoods {

    private String id;
    private String imgPath;
    private String imgLoadPath;
    private String imgDetailPath;
    private String imgDetailLoadPath;
    private String goodsName;
    private int goodsTotalNum;
    private int goodsLeftNum;
    private BigDecimal subScale;
    private BigDecimal goodsSubPrice;
    private BigDecimal goodsCost;
    private String status;
    private Date createTime;
    private String subDays;
    private String goodsCode;
    private String goodsType;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
    /* 认购商品规格, 相同字段以“，”分割 */

    //认购总数
    private String subTotalNum;
    //认购持仓数量
    private String subMakeNum;
    //发货数量
    private String subSendNum;
    private String goodsSpecId;
    private String userId;
    private String userMobile;

    //要查询的页数
    private int page;
    //每页显示多少条记录
    private Integer rows;
    //根据页数及每页显示条数计算出的当前便宜量
    private Integer start;

    public String getImgDetailPath() {
        return imgDetailPath;
    }

    public void setImgDetailPath(String imgDetailPath) {
        this.imgDetailPath = imgDetailPath;
    }

    public String getImgDetailLoadPath() {
        return imgDetailLoadPath;
    }

    public void setImgDetailLoadPath(String imgDetailLoadPath) {
        this.imgDetailLoadPath = imgDetailLoadPath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(String goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
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

    public String getImgLoadPath() {
        return imgLoadPath;
    }

    public void setImgLoadPath(String imgLoadPath) {
        this.imgLoadPath = imgLoadPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSubDays() {
        return subDays;
    }

    public void setSubDays(String subDays) {
        this.subDays = subDays;
    }
}
