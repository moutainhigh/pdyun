package com.rmkj.microcap.modules.trade.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.trade.entity.*;
import com.rmkj.microcap.modules.user.entity.RoleBean;
import com.rmkj.microcap.modules.user.entity.UserBean;

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

    List<RoleBean> findUserRoleById(String id);

    TradeStatisticsResult4 tradeHoldStatistics(TradeStatisticsParams tradeStatisticsParams);

    long findTradesTotal(TradeStatisticsParams tradeStatisticsParams);

    /**
     * 根据会员单位id查询全部代理
     * @param unitsId
     * @return
     */
    List<Ml3AgentBean> findMl3AgentList(String unitsId);

    BigDecimal queryRechargeSum(TradeStatisticsParams tradeStatisticsParams);
}
