package com.rmkj.microcap.modules.subGoods.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.subGoods.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/26.
 */
@DataSource
public interface SubGoodsDao {

    List<SubGoods> queryList(SubGoods subGoods);

    long queryListTotal(SubGoods subGoods);

    List<GoodsType> getGoodsType();

    int insert(SubGoods subGoods);

    SubGoods getById(String id);

    /**
     * 修改商品信息
     * @param subGoods
     * @return
     */
    int update(SubGoods subGoods);

    /**
     * 修改商品状态
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(String id, String status);

    List<TakeGoodsBean> takeGoodsList(TakeGoodsParam takeGoodsParam);

    int countTakeGoodsList(TakeGoodsParam takeGoodsParam);

    SubTradeBean getSubTrade(String id);

    TakeGoodsBean getTakeGoods(String id);

    int goodsOperate(Map<String,Object> map);

    int returnOldSubTrade(SubTradeBean subTradeBean);

    int createNewTrade(SubTradeBean subTradeBean);

    List<IntegralBean> getIntegralList(String mobile);

    int countIntegral(String mobile);
}
