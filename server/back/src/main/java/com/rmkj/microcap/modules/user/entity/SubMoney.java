package com.rmkj.microcap.modules.user.entity;/**
 * Created by Administrator on 2017/12/20.
 */

import java.math.BigDecimal;

/**
 * @author k
 * @create -12-20-0:10
 **/

public class SubMoney {

    private String userId;

    private BigDecimal money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
