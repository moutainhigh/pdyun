package com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity;/**
 * Created by Administrator on 2017/9/18.
 */

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 返佣金额记录
 * @author k
 * @create -09-18-12:10
 **/

public class TradeReturnFeeChange extends DataEntity{

    private BigDecimal totalFee;

    private BigDecimal beforMoney;

    private BigDecimal afterMoney;

    //类型 1统计 2提现
    private Integer type;

    private String centerId;

    private String unitsId;

    private String agentId;

    private Date createTime;

    //返佣比例
    private BigDecimal returnFeePercent;

    public BigDecimal getReturnFeePercent() {
        return returnFeePercent;
    }

    public void setReturnFeePercent(BigDecimal returnFeePercent) {
        this.returnFeePercent = returnFeePercent;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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
