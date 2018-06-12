package com.rmkj.microcap.modules.chanong.sub.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.chanong.sub.bean.*;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@DataSource
public interface SubDao {
    List<SubGoodsBean> getSubGoods(@Param("status") String status, @Param("goodsTypeId")  String goodsTypeId);

    List<SubGoodsBean> getStoreGoodsList(String userId);

    int updGoodsSubLeftDays();

    int updGoodsStatus();

    GoodsBean getSubGoodsById(String id);

    ScalesBean getScales();

    int subMake(SubMakeBean subMakeBean);

    int updGoodsSubLeftNum(Map<String,Object> map);

    int updGoodsStatusById(Map<String,Object> map);

    List<SubMakeBean> getHangGoods(String goodsId);

    int updUserSubFlag(Map<String,Object> map);

    SubMakeBean getSubTradeById(String id);

    SubMakeBean getSubTradeBySerialNo2(String serialNo,String status);

    SubMakeBean getSubTradeBySerialNo(String serialNo);

    int subSell(Map<String,Object> map);

    int updUserIntegral(Map<String,Object> map);

    List<GoodsBean> getDaysEndGoods();

    int updUserHoldTrade(Map<String,Object> map);

    List<User> getUserIntegralList();

    int updUserReturnIntegral(User user);

    BigDecimal getUserIntegral(String userId);

    int addIntegral(IntegralBean integralBean);

    List<IntegralBean> getUserIntegralDetail(String userId);

    SubGoodsSpec getSubGoodsSpec(String goodsId);

    int updUserReturnMoney(List<Map<String,Object>> list);

    ReturnFeesBean getReturnFees(String userId);

    int updAgentReturnMoney(Map<String,Object> map);

    int addReturnFeeChange(ReturnFeesChange returnFeesChange);

    int updUnitsReturnMoney(Map<String,Object> map);

    int updCenterReturnMoney(Map<String,Object> map);

    int updAgentReturnFeeMoney(Map<String,Object> map);

    int updUnitsReturnFeeMoney(Map<String,Object> map);

    int updCenterReturnFeeMoney(Map<String,Object> map);

    int insertTakeGoods(TakeGoodsBean takeGoodsBean);

    int updateHoldTrade(SubMakeBean subMakeBean);

    int cancelHangTrade(SubMakeBean subMakeBean);

    int cancel(SubMakeBean subMakeBean);

    List<SubMakeBean> getHangTradeList();

    List<HangGoodsDetail> getHangList(String goodsId);

    GoodStatistics getStatistice(String goodsId);

    int getHangNum(Map<String,Object> map);

    List<SubMakeBean> getHangGoodsTradeList(Map<String,Object> map);

    int seperateHangTrade(SubMakeBean subMakeBean);

    int goodsStore(StoreBean storeBean);

    int goodsStoreCancel(Map<String,Object> map);

    List<StoreGoodsInfo> getMyGoodsStore(String userId);

    int judgeGoodsStore(String userId, String goodsId);

    String getGoodsDetailImg(String goodsId);

    OpenTime openWeekAndTime();

    int updPlatServiceMoney(BigDecimal platServiceMoney);

    int updPlatFeeMoney(BigDecimal platFeeMoney);

    String getGoodsName(String id);

    int converUserMoney(String id);

    /**
     * 查询商品库存总量 商品所属人持仓+认购持仓
     * @param goodsId
     * @return
     */
    int countTradeHoldNum(String goodsId);
}