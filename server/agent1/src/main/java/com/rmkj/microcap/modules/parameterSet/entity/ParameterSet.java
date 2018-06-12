package com.rmkj.microcap.modules.parameterSet.entity;

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;

/**
 * TODO 参数配置类
 */
public class ParameterSet extends DataEntity{
    private String id;

    private Integer holdCount;

    private BigDecimal holdMoney;

    private BigDecimal cashMoney;

    private BigDecimal cashMoneyRation;

    private Integer cashMoneyCount;

    //推广二维码源url
    private String qrCodeUrl;

    //微信菜单url
    private String qrCodeMenuUrl;

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeMenuUrl() {
        return qrCodeMenuUrl;
    }

    public void setQrCodeMenuUrl(String qrCodeMenuUrl) {
        this.qrCodeMenuUrl = qrCodeMenuUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getHoldCount() {
        return holdCount;
    }

    public void setHoldCount(Integer holdCount) {
        this.holdCount = holdCount;
    }

    public BigDecimal getHoldMoney() {
        return holdMoney;
    }

    public void setHoldMoney(BigDecimal holdMoney) {
        this.holdMoney = holdMoney;
    }

    public BigDecimal getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(BigDecimal cashMoney) {
        this.cashMoney = cashMoney;
    }

    public BigDecimal getCashMoneyRation() {
        return cashMoneyRation;
    }

    public void setCashMoneyRation(BigDecimal cashMoneyRation) {
        this.cashMoneyRation = cashMoneyRation;
    }

    public Integer getCashMoneyCount() {
        return cashMoneyCount;
    }

    public void setCashMoneyCount(Integer cashMoneyCount) {
        this.cashMoneyCount = cashMoneyCount;
    }
}