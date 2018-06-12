package com.rmkj.microcap.modules.subGoods.entity;

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jinghao on 2018/5/17.
 */
public class IntegralBean extends DataEntity {

    private String id;
    private String userId;
    private String type; //状态： 1-正积分 2-负积分
    private BigDecimal integral;
    private BigDecimal integralBefore;
    private BigDecimal integralAfter;
    private String createTime;
    private String remark;
    private String userName;
    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public BigDecimal getIntegralBefore() {
        return integralBefore;
    }

    public void setIntegralBefore(BigDecimal integralBefore) {
        this.integralBefore = integralBefore;
    }

    public BigDecimal getIntegralAfter() {
        return integralAfter;
    }

    public void setIntegralAfter(BigDecimal integralAfter) {
        this.integralAfter = integralAfter;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
