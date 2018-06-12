package com.rmkj.microcap.modules.chanong.sub.bean;

import java.util.Date;

/**
 * Created by jinghao on 2018/5/16.
 */
public class StoreGoodsInfo {
    private Date storeTime;
    private String imgLoadPath;
    private String goodsName;
    private String goodsCode;
    private String status;
    private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
