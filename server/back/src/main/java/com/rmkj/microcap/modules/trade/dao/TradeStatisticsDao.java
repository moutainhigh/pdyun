package com.rmkj.microcap.modules.trade.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.trade.entity.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by renwp on 2016/12/30.
 */
@DataSource
public interface TradeStatisticsDao {
    List<TradeStatistics> findTrades(TradeStatisticsParams tradeStatisticsParams);

    TradeStatisticsResult findTradesResult(TradeStatisticsParams tradeStatisticsParams);

    TradeStatisticsResult1 tradeStatistics(TradeStatisticsParams tradeStatisticsParams);

    FeesBean getFeesCount(TradeStatisticsParams tradeStatisticsParams);

    TradeStatisticsResult2 userStatistics(TradeStatisticsParams tradeStatisticsParams);

    TradeStatisticsResult3 moneyRecordStatistics(TradeStatisticsParams tradeStatisticsParams);

    String findUserIdByMobile(String mobile);

    String findAgentIdByMobile(String agentMobile);

    List<Units> findUnits();

    TradeStatisticsResult4 tradeHoldStatistics(TradeStatisticsParams tradeStatisticsParams);

    long findTradesTotal(TradeStatisticsParams tradeStatisticsParams);

    /**
     * 查询交易返佣之后,应得手续费金额
     * @param tradeStatisticsParams
     * @return
     */
    TradeStatisticsResult5 findExChangeWinFee(TradeStatisticsParams tradeStatisticsParams);

    /**
     * 查询市场管理部返手续费总额
     * @param tradeStatisticsParams
     * @return
     */
    TradeStatisticsResult5 findTradeReturnChangeByCenterId(TradeStatisticsParams tradeStatisticsParams);

    /**
     * 查询充值成功总额
     * @param tradeStatisticsParams
     * @return
     */
    BigDecimal queryRechargeSum(TradeStatisticsParams tradeStatisticsParams);
}
