package com.rmkj.microcap.modules.tradeMarket.entity;/**
 * Created by Administrator on 2017/12/13.
 */

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;

/**
 * @author k
 * @create -12-13-11:36
 **/

public class TradeMarketBean extends DataEntity{

    private String userId;

    private Integer profitMax;

    private Integer lossMax;

    private BigDecimal profitMaxPoint;

    private BigDecimal loss_max_point;

    public BigDecimal getProfitMaxPoint() {
        return profitMaxPoint;
    }

    public void setProfitMaxPoint(BigDecimal profitMaxPoint) {
        this.profitMaxPoint = profitMaxPoint;
    }

    public BigDecimal getLoss_max_point() {
        return loss_max_point;
    }

    public void setLoss_max_point(BigDecimal loss_max_point) {
        this.loss_max_point = loss_max_point;
    }

    public Integer getProfitMax() {
        return profitMax;
    }

    public void setProfitMax(Integer profitMax) {
        this.profitMax = profitMax;
    }

    public Integer getLossMax() {
        return lossMax;
    }

    public void setLossMax(Integer lossMax) {
        this.lossMax = lossMax;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
