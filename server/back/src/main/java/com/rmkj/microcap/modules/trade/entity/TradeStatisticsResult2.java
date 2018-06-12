package com.rmkj.microcap.modules.trade.entity;

/**
 * Created by renwp on 2016/12/30.
 */
public class TradeStatisticsResult2 {
    String inMoney;
    String money;
    //累计注册金额
    String registerMoney;

    public String getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }

    public String getInMoney() {
        return inMoney;
    }

    public void setInMoney(String inMoney) {
        this.inMoney = inMoney;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
