package com.rmkj.microcap.modules.monitor.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renwp on 2016/9/27.
 */
public class DataSummary {

    /**
     * 客户总余额
     */
    private BigDecimal userMoney;
    /**
     * 注册人数
     */
    private Long userNumbers;
    /**
     * 客户总盈亏
     */
    private BigDecimal userProfitAndLoss;
    /**
     * 充值总额
     */
    private BigDecimal userRecharge;
    /**
     * 充值总笔数
     */
    private Long userRechargePens;
    /**
     * 提现人数
     */
    private Long userWithdrawalsNumbers;
    /**
     * 提现笔数
     */
    private Long userWithdrawalsPens;
    private BigDecimal withdrawalsMoney;
    //开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMin;
    //结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTimeMax;

    private String formula;

    public BigDecimal getWithdrawalsMoney() {
        return withdrawalsMoney;
    }

    public void setWithdrawalsMoney(BigDecimal withdrawalsMoney) {
        this.withdrawalsMoney = withdrawalsMoney;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    public Long getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(Long userNumbers) {
        this.userNumbers = userNumbers;
    }

    public BigDecimal getUserProfitAndLoss() {
        return userProfitAndLoss;
    }

    public void setUserProfitAndLoss(BigDecimal userProfitAndLoss) {
        this.userProfitAndLoss = userProfitAndLoss;
    }

    public BigDecimal getUserRecharge() {
        return userRecharge;
    }

    public void setUserRecharge(BigDecimal userRecharge) {
        this.userRecharge = userRecharge;
    }

    public Long getUserRechargePens() {
        return userRechargePens;
    }

    public void setUserRechargePens(Long userRechargePens) {
        this.userRechargePens = userRechargePens;
    }

    public Long getUserWithdrawalsNumbers() {
        return userWithdrawalsNumbers;
    }

    public void setUserWithdrawalsNumbers(Long userWithdrawalsNumbers) {
        this.userWithdrawalsNumbers = userWithdrawalsNumbers;
    }

    public Long getUserWithdrawalsPens() {
        return userWithdrawalsPens;
    }

    public void setUserWithdrawalsPens(Long userWithdrawalsPens) {
        this.userWithdrawalsPens = userWithdrawalsPens;
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
