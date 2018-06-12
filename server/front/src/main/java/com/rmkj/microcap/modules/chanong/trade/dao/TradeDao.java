package com.rmkj.microcap.modules.chanong.trade.dao;/**
 * Created by Administrator on 2018/5/2.
 */

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.chanong.sub.bean.SubMakeBean;
import com.rmkj.microcap.modules.chanong.trade.entity.TradeBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author k
 * @create -05-02-9:13
 **/
@DataSource
public interface TradeDao {

    /**
     * 查询认购中的商品数量
     * @param userId
     * @param id
     * @return
     */
    SubMakeBean getTradeHoldNum(String userId, String id);

    /**
     * 添加挂单
     * @param subMakeBean
     * @return
     */
    int saveEntryOrder(SubMakeBean subMakeBean);

    /**
     * 修改认购订单商品数量 数量为0时下架订单
     * @param subMakeBean
     * @return
     */
    int updateEntryOrderHoldNum(SubMakeBean subMakeBean);

    /**
     * 查询当日订单最新成交价格
     * @param goodsId
     * @return
     */
    TradeBean getTodaySellPoint(String goodsId);

    /**
     * 查询当日全部商品的最低卖出价
     * @param goodsId
     * @return
     */
    List<TradeBean> findGroupTodayBuyPointMin(String goodsId);

    /**
     * 查询当日全部商品的最新成交价
     * @param goodsId
     * @return
     */
    List<TradeBean> findGroupTodaySellPoint(String goodsId);

    BigDecimal getUpAndDownPercent();
}
