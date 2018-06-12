package com.rmkj.microcap.modules.user.entity;

import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/17.
 */
public class UserReport {
    private UserBean userBean;
    private long tiXianCount;
    private BigDecimal winMoney = new BigDecimal(0);//盈利
    private BigDecimal loseMoney = new BigDecimal(0);//亏损
    private BigDecimal tradeMoney = new BigDecimal(0);

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public long getTiXianCount() {
        return tiXianCount;
    }

    public void setTiXianCount(long tiXianCount) {
        this.tiXianCount = tiXianCount;
    }

    public BigDecimal getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(BigDecimal winMoney) {
        this.winMoney = winMoney;
    }

    public BigDecimal getLoseMoney() {
        return loseMoney;
    }

    public void setLoseMoney(BigDecimal loseMoney) {
        this.loseMoney = loseMoney;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }
}
