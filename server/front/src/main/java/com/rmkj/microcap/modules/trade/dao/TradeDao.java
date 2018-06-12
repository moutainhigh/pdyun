package com.rmkj.microcap.modules.trade.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.trademarket.MarketPointBean;
import com.rmkj.microcap.modules.index.entity.ParameterSet;
import com.rmkj.microcap.modules.trade.bean.TradeHistoryBean;
import com.rmkj.microcap.modules.trade.bean.UserTradeBean;
import com.rmkj.microcap.modules.trade.entity.Agent3Trade;
import com.rmkj.microcap.modules.trade.entity.Contract;
import com.rmkj.microcap.modules.trade.entity.Trade;

import java.util.Date;
import java.util.List;

/**
 * Created by renwp on 2016/10/19.
 */
@DataSource
public interface TradeDao {
    Contract findContractByCode(String code);

    Contract findContractById(String code);

    int make(Trade trade);

    int make3(Agent3Trade agent3Trade);

    int sell(Trade trade);

    UserTradeBean findTradeById(String id);

    List<Trade> tradeListByUserId(String userId);

    List<Trade> tradeHistory(TradeHistoryBean tradeHistoryBean);
    /**
     * 查询今天的结算数据
     * @param userId
     * @return
     */
    List<Trade> toDayTradeListByUserId(String userId);

    List<Contract> contractList();

    /**
     * 查询需要止盈止损平仓的数据
     * @param latest
     * @return
     */
    List<UserTradeBean> findStopProfixOrLoss(MarketPointBean latest);

    List<UserTradeBean> findNoSellForSettlement();

    List<UserTradeBean> findOffTimeTrade(Date now);

    List<UserTradeBean> findOffPointTrade(List<MarketPointBean> latests);

    List<UserTradeBean> findNotOnTime(Date date);

    /**
     * TODO 查询用户持仓数量
     * @param userId
     * @return
     */
    List<Trade> findUserTradeCount(String userId);

    /**
     * 移动平仓数据
     * @return
     */
    int moveTrade();

    /**
     * 删掉已经移动的数据
     * @param time 在tb_trade表中大于这个时间点的数据中，搜索已经存在
     * @return
     */
    int deleteHasMove(Date time);

    /**
     * 分组查询会员单位交易统计总盈亏
     * @return
     */
    List<Trade> findUnitsMoneySumList();

    /**
     * 根据会员单位id查询会员单位下用户交易金额总量
     * @return
     */
    Trade findTradeSumGroupByUnitsId(Trade trade);
}
