package com.rmkj.microcap.common.modules.pay.zhinengcloud.entity;/**
 * Created by Administrator on 2017/12/15.
 */

import com.rmkj.microcap.common.constants.RegexpConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author k
 * @create -12-15-15:48
 **/

public class ZhiNengCloudPayBean {

    @NotNull
    @Pattern(regexp = RegexpConstants.MONEY, message = "金额不合法")
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
