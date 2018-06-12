package com.rmkj.microcap.modules.trade.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.trade.entity.TradeBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-22.
*/
@DataSource
public interface ITradeDao extends BaseDao<TradeBean>{
    List<TradeBean> tradeList(TradeBean bean);
    List<TradeBean> holdList(TradeBean bean);
    //合计返佣
    TradeBean fanyongTotal(String id);
    //代理商下的合计返佣
    TradeBean fanyongMl3Agent(String id);
    //当前持仓列表
    List<TradeBean> tradeHoldList(TradeBean bean);
    //返佣合计下的军团返佣列表数据
    List<TradeBean> fanYongMingXiList(TradeBean bean);
    //返佣合计下的代理返佣列表数据
    List<TradeBean> fanYongMl3AgentList(TradeBean bean);
    //会员单位下的客户持仓列表
    List<TradeBean> tradeHoldInnerUnitsList(TradeBean bean);
    //市场管理部下的客户持仓列表
    List<TradeBean> tradeHoldOperateCenter(TradeBean bean);
    //会员单位下的军团返佣列表数据
    List<TradeBean> fanyongInnerUnits(TradeBean bean);
    //会员单位下的代理返佣列表数据
    List<TradeBean> fanyongMl3AgentUnits(TradeBean bean);
    //会员单位下的会员单位返佣列表数据
    List<TradeBean> fanyong(TradeBean bean);
    //代理商下的返佣明细盈亏综合
    TradeBean fanyongMl3AgentTotal(TradeBean bean);
    //会员单位下的的盈亏统计代理统计盈亏总和
    TradeBean fanyongUnitsTotal(TradeBean bean);
    //会员单位下的会员单位统计的盈亏综合
    TradeBean fanyongTotal01(TradeBean bean);
}
