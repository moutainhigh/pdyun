package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by jinghao on 2018/5/8.
 */
public class ReturnFeesChange {
    private String id;
    private BigDecimal totalFee;//'返佣金额'
    private BigDecimal beforMoney;
    private BigDecimal afterMoney;
    private BigDecimal returnFeePercent;//'返佣比例'
    private int type;  //类型: 1统计 2提现 3服务费返佣  4手续费返佣'
    private String centerId;
    private String unitsId;
    private String agentId;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getBeforMoney() {
        return beforMoney;
    }

    public void setBeforMoney(BigDecimal beforMoney) {
        this.beforMoney = beforMoney;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public BigDecimal getReturnFeePercent() {
        return returnFeePercent;
    }

    public void setReturnFeePercent(BigDecimal returnFeePercent) {
        this.returnFeePercent = returnFeePercent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(String unitsId) {
        this.unitsId = unitsId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
