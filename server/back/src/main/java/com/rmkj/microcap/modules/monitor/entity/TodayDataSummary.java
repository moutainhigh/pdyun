package com.rmkj.microcap.modules.monitor.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renwp on 2016/9/27.
 * 当日注册人数，当日充值金额，当日提现人数，今日交易笔数，当日盈亏
 */
public class TodayDataSummary {

    private long registerNumbers;
    private BigDecimal rechargeMoney;
    private long withdrawalsPeoples;
    private BigDecimal withdrawalsMoney;
    private long tradePens;
    private BigDecimal profitAndLossMoney;
    //开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMin;
    //结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMax;
    private String formula;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public long getRegisterNumbers() {
        return registerNumbers;
    }

    public void setRegisterNumbers(long registerNumbers) {
        this.registerNumbers = registerNumbers;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public long getWithdrawalsPeoples() {
        return withdrawalsPeoples;
    }

    public void setWithdrawalsPeoples(long withdrawalsPeoples) {
        this.withdrawalsPeoples = withdrawalsPeoples;
    }

    public BigDecimal getWithdrawalsMoney() {
        return withdrawalsMoney;
    }

    public void setWithdrawalsMoney(BigDecimal withdrawalsMoney) {
        this.withdrawalsMoney = withdrawalsMoney;
    }

    public long getTradePens() {
        return tradePens;
    }

    public void setTradePens(long tradePens) {
        this.tradePens = tradePens;
    }

    public BigDecimal getProfitAndLossMoney() {
        return profitAndLossMoney;
    }

    public void setProfitAndLossMoney(BigDecimal profitAndLossMoney) {
        this.profitAndLossMoney = profitAndLossMoney;
    }

    public Date getCreateTimeMin() {
        return createTimeMin;
    }

    public void setCreateTimeMin(Date createTimeMin) {
        this.createTimeMin = createTimeMin;
    }

    public Date getCreateTimeMax() {
        return createTimeMax;
    }

    public void setCreateTimeMax(Date createTimeMax) {
        this.createTimeMax = createTimeMax;
    }
}
