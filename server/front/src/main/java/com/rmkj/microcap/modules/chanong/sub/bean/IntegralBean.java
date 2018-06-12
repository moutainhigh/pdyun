package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jinghao on 2018/5/2.
 */
public class IntegralBean {
    private String id;
    private String userId;
    private String type;
    private BigDecimal integral;
    private BigDecimal integralBefore;
    private BigDecimal integralAfter;
    private Date createTime;
    private String remark;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
