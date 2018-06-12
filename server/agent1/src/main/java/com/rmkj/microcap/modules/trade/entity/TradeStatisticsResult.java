package com.rmkj.microcap.modules.trade.entity;

/**
 * Created by renwp on 2016/12/30.
 */
public class TradeStatisticsResult {

    private TradeStatisticsResult1 trade;
    private TradeStatisticsResult2 user;
    private TradeStatisticsResult3 moneyRecord;

    private TradeStatisticsResult4 holdTrade;

    public TradeStatisticsResult1 getTrade() {
        return trade;
    }

    public void setTrade(TradeStatisticsResult1 trade) {
        this.trade = trade;
    }

    public TradeStatisticsResult2 getUser() {
        return user;
    }

    public void setUser(TradeStatisticsResult2 user) {
        this.user = user;
    }

    public TradeStatisticsResult3 getMoneyRecord() {
        return moneyRecord;
    }

    public void setMoneyRecord(TradeStatisticsResult3 moneyRecord) {
        this.moneyRecord = moneyRecord;
    }

    public TradeStatisticsResult4 getHoldTrade() {
        return holdTrade;
    }

    public void setHoldTrade(TradeStatisticsResult4 holdTrade) {
        this.holdTrade = holdTrade;
    }
}
