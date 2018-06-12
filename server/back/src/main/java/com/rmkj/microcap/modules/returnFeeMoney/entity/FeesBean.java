package com.rmkj.microcap.modules.returnFeeMoney.entity;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/5/25.
 */
public class FeesBean {
    private BigDecimal serviceFeeMoney;
    private BigDecimal serviceFeeMoneyTotal;
    private BigDecimal feeMoney;
    private BigDecimal feeMoneyTotal;

    public BigDecimal getServiceFeeMoney() {
        return serviceFeeMoney;
    }

    public void setServiceFeeMoney(BigDecimal serviceFeeMoney) {
        this.serviceFeeMoney = serviceFeeMoney;
    }

    public BigDecimal getServiceFeeMoneyTotal() {
        return serviceFeeMoneyTotal;
    }

    public void setServiceFeeMoneyTotal(BigDecimal serviceFeeMoneyTotal) {
        this.serviceFeeMoneyTotal = serviceFeeMoneyTotal;
    }

    public BigDecimal getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(BigDecimal feeMoney) {
        this.feeMoney = feeMoney;
    }

    public BigDecimal getFeeMoneyTotal() {
        return feeMoneyTotal;
    }

    public void setFeeMoneyTotal(BigDecimal feeMoneyTotal) {
        this.feeMoneyTotal = feeMoneyTotal;
    }
}
