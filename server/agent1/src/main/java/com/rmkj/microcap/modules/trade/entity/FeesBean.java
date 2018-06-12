package com.rmkj.microcap.modules.trade.entity;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/5/25.
 */
public class FeesBean {
    private BigDecimal serviceFeeSum;
    private BigDecimal buyFeeSum;
    private BigDecimal sellFeeSum;

    public BigDecimal getServiceFeeSum() {
        return serviceFeeSum;
    }

    public void setServiceFeeSum(BigDecimal serviceFeeSum) {
        this.serviceFeeSum = serviceFeeSum;
    }

    public BigDecimal getBuyFeeSum() {
        return buyFeeSum;
    }

    public void setBuyFeeSum(BigDecimal buyFeeSum) {
        this.buyFeeSum = buyFeeSum;
    }

    public BigDecimal getSellFeeSum() {
        return sellFeeSum;
    }

    public void setSellFeeSum(BigDecimal sellFeeSum) {
        this.sellFeeSum = sellFeeSum;
    }
}
