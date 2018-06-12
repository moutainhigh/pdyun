package com.rmkj.microcap.modules.tradeMarket.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Created by renwp on 2016/10/19.
 */
public class UpdateHoldBean {
    /**
     * id : 140d5967766043d2941dfde964efec33
     */

    @NotBlank
    private String id;

    //@NotBlank
    private String tradePassword;

    @Range(min = 100, max = 300, message = "止盈比例在100-300之间")
    private Integer profitMax;
    @Range(min = 0, max = 70, message = "止损比例在0-70之间")
    private Integer lossMax;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }
}
