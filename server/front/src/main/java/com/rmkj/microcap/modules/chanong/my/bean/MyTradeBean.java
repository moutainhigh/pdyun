package com.rmkj.microcap.modules.chanong.my.bean;

/**
 * Created by jinghao on 2018/4/24.
 */
public class MyTradeBean {
    private String userId;
    private String status;//状态：1 持仓  2挂单 3 平仓（交割） 4 下架
    private String goodsName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
