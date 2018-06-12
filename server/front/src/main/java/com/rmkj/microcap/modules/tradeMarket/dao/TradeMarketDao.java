package com.rmkj.microcap.modules.tradeMarket.dao;/**
 * Created by Administrator on 2017/11/27.
 */

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.trademarket.MarketPointBean;
import com.rmkj.microcap.modules.trade.bean.TradeMakeBean;
import com.rmkj.microcap.modules.trade.bean.UserTradeBean;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.tradeMarket.entity.TradeMarketBean;
import com.rmkj.microcap.modules.tradeMarket.entity.UpdateHoldBean;

import java.util.Date;
import java.util.List;

/**
 * @author k
 * @create -11-27-11:32
 **/
@DataSource
public interface TradeMarketDao {

    int make(Trade trade);

    int make3(Trade trade);

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

    List<UserTradeBean> findStopProfixOrLoss(MarketPointBean latest);

    List<UserTradeBean> findNoSellForSettlement();

    /**
     * 查询当前用户正在持仓的订单
     * @param tradeMarketBean
     * @return
     */
    UserTradeBean findHoldByUser(TradeMarketBean tradeMarketBean);

    /**
     * 修改持仓订单的止盈止损点
     * @param trade
     * @return
     */
    int updateProfitMaxOrLossMaxByUser(UserTradeBean trade);

    /**
     * 查询用户订单盈利情况
     * @return
     */
    List<UserTradeBean> queryWinProfitRecord();
}
