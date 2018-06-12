package com.rmkj.microcap.modules.moneyrecord.entity;

import java.math.BigDecimal;

/**
 * Created by renwp on 2017/1/4.
 */
public class ReturnMoney2User {
    private String id;
    private BigDecimal money;
    private BigDecimal difMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getDifMoney() {
        return difMoney;
    }

    public void setDifMoney(BigDecimal difMoney) {
        this.difMoney = difMoney;
    }
}
