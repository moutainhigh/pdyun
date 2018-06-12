package com.rmkj.microcap.modules.trade.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface ITradeDao extends BaseDao<TradeBean>{
    //当前持仓列表
    List<TradeBean> tradeHoldList(TradeBean bean);
    //平仓明细列表
    List<TradeBean> tradeCoverList(TradeBean bean);
    //军团长管理下的返佣明细列表
    List<TradeBean> fanYongMingXiList(TradeBean bean);
    //合计返佣
    TradeBean fanyongTotal(TradeBean bean);
    //代理商管理下的返佣明细列表
    List<TradeBean> fanYongMl3AgentList(TradeBean bean);
    //会员单位管理下的返佣明细列表
    List<TradeBean> fanYongUnitsList(TradeBean bean);
    //会员单位下的合计返佣
    TradeBean fanyongUnitsTotal(String name);
    //代理商管理下的盈亏统计
    List<TradeBean> yingkuitotal(TradeBean bean);
    //代理商管理下的盈亏统计的交易总和和盈亏总和
    TradeBean yingkuiTotal(TradeBean bean);
    //会员单位管理下的盈亏统计
    List<TradeBean> yingkuiInnerUnits(TradeBean bean);
    //会员单位管理下的盈亏统计的交易总和和盈亏总和
    TradeBean yingkuiInnerUnitsTotal(TradeBean bean);
    //军团长管理下的盈亏统计
    List<TradeBean> yingkuijuntuan(TradeBean bean);
    //军团长管理下的盈亏痛就的交易总和和盈亏总和
    TradeBean yingkuijuntuanTotal(TradeBean bean);
    //代理商下的返佣明细盈亏综合
    TradeBean fanyongMl3AgentTotal(TradeBean bean);
    //会员单位下的返佣明细盈亏综合
    TradeBean fanyongUnitsTotal(TradeBean bean);

    long tradeCoverListCount(TradeBean entity);

    /**
     * 查询当前持仓总手续费，持仓总金额
     * @param tradeBean
     * @return
     */
    TradeBean queryHoldFeeAndMoney(TradeBean tradeBean);

    /**
     * 发布商品时 默认给商品所属客户认购
     * @param tradeBean
     * @return
     */
    int saveSubGoodsMake(TradeBean tradeBean);

    /**
     * 根据持仓订单id,持仓类型， 查询持仓订单信息
     * @param id
     * @param holdFlag
     * @return
     */
    TradeBean getHoldTradeById(@Param("id") String id, @Param("holdFlag") String holdFlag);

    /**
     * 持仓划转创建订单
     * @param tradeBean
     * @return
     */
    int transHold(TradeBean tradeBean);

    /**
     * 持仓转化时修改原订单的数量
     * @param tradeBean
     * @return
     */
    int updateTransHold(TradeBean tradeBean);

    /**
     * 持仓转化时订单数量为0设置订单为平仓
     * @param tradeBean
     * @return
     */
    int updateTransHoldSell(TradeBean tradeBean);

    /**
     * 查询当前持仓总数量金额
     * @param tradeBean
     * @return
     */
    int queryHoldTotal(TradeBean tradeBean);
}
