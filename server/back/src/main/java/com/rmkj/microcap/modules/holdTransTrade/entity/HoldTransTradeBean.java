package com.rmkj.microcap.modules.holdTransTrade.entity;/**
 * Created by Administrator on 2018/5/18.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author k
 * @create -05-18-11:52
 **/

public class HoldTransTradeBean {

    private String id;

    private String holdTradeId;

    private String holdUserId;

    private String transUserId;

    private String transUserMobile;

    private String holdSerialNo;

    private int transHoldNum;

    private BigDecimal serviceFee;

    private BigDecimal buyFee;

    private BigDecimal money;

    private String goodsId;

    private String goodsName;

    private BigDecimal buyPoint;

    private Date createTime;

    private String holdFlag;

    private String oldHoldChnName;

    private String oldHoldMobile;

    private String chnName;

    private String mobile;

    //要查询的页数
    private int page;
    //每页显示多少条记录
    private Integer rows;
    //根据页数及每页显示条数计算出的当前便宜量
    private Integer start;

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOldHoldChnName() {
        return oldHoldChnName;
    }

    public void setOldHoldChnName(String oldHoldChnName) {
        this.oldHoldChnName = oldHoldChnName;
    }

    public String getOldHoldMobile() {
        return oldHoldMobile;
    }

    public void setOldHoldMobile(String oldHoldMobile) {
        this.oldHoldMobile = oldHoldMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHoldTradeId() {
        return holdTradeId;
    }

    public void setHoldTradeId(String holdTradeId) {
        this.holdTradeId = holdTradeId;
    }

    public String getHoldUserId() {
        return holdUserId;
    }

    public void setHoldUserId(String holdUserId) {
        this.holdUserId = holdUserId;
    }

    public String getTransUserId() {
        return transUserId;
    }

    public void setTransUserId(String transUserId) {
        this.transUserId = transUserId;
    }

    public String getTransUserMobile() {
        return transUserMobile;
    }

    public void setTransUserMobile(String transUserMobile) {
        this.transUserMobile = transUserMobile;
    }

    public String getHoldSerialNo() {
        return holdSerialNo;
    }

    public void setHoldSerialNo(String holdSerialNo) {
        this.holdSerialNo = holdSerialNo;
    }

    public int getTransHoldNum() {
        return transHoldNum;
    }

    public void setTransHoldNum(int transHoldNum) {
        this.transHoldNum = transHoldNum;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(BigDecimal buyFee) {
        this.buyFee = buyFee;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public BigDecimal getBuyPoint() {
        return buyPoint;
    }

    public void setBuyPoint(BigDecimal buyPoint) {
        this.buyPoint = buyPoint;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHoldFlag() {
        return holdFlag;
    }

    public void setHoldFlag(String holdFlag) {
        this.holdFlag = holdFlag;
    }
}
