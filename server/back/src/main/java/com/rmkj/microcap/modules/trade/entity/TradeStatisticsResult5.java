package com.rmkj.microcap.modules.trade.entity;

import java.math.BigDecimal;

/**
 * Created by renwp on 2017/1/17.
 */
public class TradeStatisticsResult5 {
    //总后台返手续费之后剩余手续费金额
    private BigDecimal winTradeFee;

    //返手续费总额
    private BigDecimal totalFee;

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getWinTradeFee() {
        return winTradeFee;
    }

    public void setWinTradeFee(BigDecimal winTradeFee) {
        this.winTradeFee = winTradeFee;
    }
}
