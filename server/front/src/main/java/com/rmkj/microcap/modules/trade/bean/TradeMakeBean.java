package com.rmkj.microcap.modules.trade.bean;

import com.rmkj.microcap.common.constants.RegexpConstants;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 微盘模式
 * Created by renwp on 2016/10/19.
 */
public class TradeMakeBean {

    /**
     * code : 合约代码
     * type : 类型 0 资金 1 代金券
     * buyUpDown : 买涨买跌 0 买涨 1 买跌
     * money : 购买金额
     * profitMax : 止盈值
     * lossMax : 止损值
     */
    @NotBlank
    private String code;
//    @NotBlank
    private String type;
    @NotBlank
    private String buyUpDown;
    @NotNull
    @Min(1)
    @Max(100000)
    private BigDecimal money;
    //@NotNull
    //@Pattern(regexp = RegexpConstants.WIN_lOSS_PERCENT, message = "止盈错误")
    @Min(value = 1, message = "止盈1-1000(%)")
    @Max(value = 1000, message = "止盈1-1000(%)")
    private Integer profitMax;
    //@NotNull
    //@Pattern(regexp = RegexpConstants.WIN_lOSS_PERCENT, message = "止损错误")
    @Min(value = 1, message = "止损1-1000(%)")
    @Max(value = 1000, message = "止损1-1000(%)")
    private Integer lossMax;

    //@NotNull
    private String offPoint;

    private String couponId;
//    @NotNull
    private String tradePassword;
    @Min(value = 1, message = "最小持仓单位1手")
    @Max(value = 100, message = "最大持仓单位100手")
    private Integer holdCount;

    public Integer getHoldCount() {
        return holdCount;
    }

    public void setHoldCount(Integer holdCount) {
        this.holdCount = holdCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyUpDown() {
        return buyUpDown;
    }

    public void setBuyUpDown(String buyUpDown) {
        this.buyUpDown = buyUpDown;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public String getOffPoint() {
        return offPoint;
    }

    public void setOffPoint(String offPoint) {
        this.offPoint = offPoint;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }
}
