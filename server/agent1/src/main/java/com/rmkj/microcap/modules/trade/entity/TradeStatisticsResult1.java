package com.rmkj.microcap.modules.trade.entity;

import java.math.BigDecimal;

/**
 * Created by renwp on 2016/12/30.
 */
public class TradeStatisticsResult1 {
    String allPens;
    String validMoney;
    String sellPens;
    String winAndLoseMoney;
    String winPens;
    String drawPens;
    String losePens;
    String extensionMoney;
    //交易管理费  有效流水*2%
    String tradeManageMoney;
    //红利  总盈亏-交易管理费
    String bonus;

    BigDecimal serviceFeeSum;
    BigDecimal feeBuySum;
    BigDecimal feeSellSum;

    public BigDecimal getServiceFeeSum() {
        return serviceFeeSum;
    }

    public void setServiceFeeSum(BigDecimal serviceFeeSum) {
        this.serviceFeeSum = serviceFeeSum;
    }

    public BigDecimal getFeeBuySum() {
        return feeBuySum;
    }

    public void setFeeBuySum(BigDecimal feeBuySum) {
        this.feeBuySum = feeBuySum;
    }

    public BigDecimal getFeeSellSum() {
        return feeSellSum;
    }

    public void setFeeSellSum(BigDecimal feeSellSum) {
        this.feeSellSum = feeSellSum;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getTradeManageMoney() {
        return tradeManageMoney;
    }

    public void setTradeManageMoney(String tradeManageMoney) {
        this.tradeManageMoney = tradeManageMoney;
    }

    public String getAllPens() {
        return allPens;
    }

    public void setAllPens(String allPens) {
        this.allPens = allPens;
    }

    public String getValidMoney() {
        return validMoney;
    }

    public void setValidMoney(String validMoney) {
        this.validMoney = validMoney;
    }

    public String getSellPens() {
        return sellPens;
    }

    public void setSellPens(String sellPens) {
        this.sellPens = sellPens;
    }

    public String getWinAndLoseMoney() {
        return winAndLoseMoney;
    }

    public void setWinAndLoseMoney(String winAndLoseMoney) {
        this.winAndLoseMoney = winAndLoseMoney;
    }

    public String getWinPens() {
        return winPens;
    }

    public void setWinPens(String winPens) {
        this.winPens = winPens;
    }

    public String getDrawPens() {
        return drawPens;
    }

    public void setDrawPens(String drawPens) {
        this.drawPens = drawPens;
    }

    public String getLosePens() {
        return losePens;
    }

    public void setLosePens(String losePens) {
        this.losePens = losePens;
    }

    public String getExtensionMoney() {
        return extensionMoney;
    }

    public void setExtensionMoney(String extensionMoney) {
        this.extensionMoney = extensionMoney;
    }
}
