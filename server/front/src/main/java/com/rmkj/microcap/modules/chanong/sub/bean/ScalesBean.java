package com.rmkj.microcap.modules.chanong.sub.bean;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/4/28.
 */
public class ScalesBean {
    private BigDecimal subServiceScale;
    private BigDecimal subFeeScale;
    private BigDecimal integralReturnScale;
    private BigDecimal percentBuBing;
    private BigDecimal percentQiBing;

    public BigDecimal getPercentBuBing() {
        return percentBuBing;
    }

    public void setPercentBuBing(BigDecimal percentBuBing) {
        this.percentBuBing = percentBuBing;
    }

    public BigDecimal getPercentQiBing() {
        return percentQiBing;
    }

    public void setPercentQiBing(BigDecimal percentQiBing) {
        this.percentQiBing = percentQiBing;
    }

    public BigDecimal getIntegralReturnScale() {
        return integralReturnScale;
    }

    public void setIntegralReturnScale(BigDecimal integralReturnScale) {
        this.integralReturnScale = integralReturnScale;
    }

    public BigDecimal getSubServiceScale() {
        return subServiceScale;
    }

    public void setSubServiceScale(BigDecimal subServiceScale) {
        this.subServiceScale = subServiceScale;
    }

    public BigDecimal getSubFeeScale() {
        return subFeeScale;
    }

    public void setSubFeeScale(BigDecimal subFeeScale) {
        this.subFeeScale = subFeeScale;
    }
}
