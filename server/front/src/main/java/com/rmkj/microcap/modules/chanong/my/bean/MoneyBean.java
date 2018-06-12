package com.rmkj.microcap.modules.chanong.my.bean;

/**
 * Created by jinghao on 2018/4/24.
 */
public class MoneyBean {
    private String userId;
    private String type;//  '类型： NULL-全部，0-充值 1-提现 '

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
