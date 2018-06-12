package com.rmkj.microcap.modules.returnFeeMoney.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.returnFeeMoney.entity.*;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
@DataSource
public interface ReturnFeeMoneyDao {

    List<ReturnFeeAgent> queryReturnFeeMoneyByAgent(ReturnFeeAgent returnFeeAgent);

    long queryReturnFeeMoneyByAgentTotal(ReturnFeeAgent returnFeeAgent);

    /**
     * 查询会员单位手续费提现列表
     * @param entity
     * @return
     */
    List<ReturnFeeMemberUnits> queryReturnFeeMoneyByUnits(ReturnFeeMemberUnits entity);

    /**
     * 查询会员单位手续费提现列表条数
     * @param entity
     * @return
     */
    long queryReturnFeeMoneyByUnitsTotal(ReturnFeeMemberUnits entity);

    /**
     * 查询市场管理部手续费提现列表
     * @param entity
     * @return
     */
    List<ReturnFeeOperateCenter> queryReturnFeeMoneyByCenter(ReturnFeeOperateCenter entity);

    /**
     * 查询市场管理部手续费提现列表条数
     * @param entity
     * @return
     */
    long queryReturnFeeMoneyByCenterTotal(ReturnFeeOperateCenter entity);

    int saveMoneyPlatRecord(TradeReturnFeeRecord entity);
    int updatePlatMoney(TradeReturnFeeRecord entity);

    List<TradeReturnFeeRecord> moneyPlatRecord();


    FeesBean  getFeesBean();

    int countMoneyPlatRecord();

    /**
     * 筛选未处理过的订单
     * @param list
     * @return
     */
    List<TradeReturnFeeRecord> filterNoPassRecord(List<String> list);

    /**
     * 审核返手续费提现订单状态
     * @param entity
     * @return
     */
    int updateReviewStatusAndStatus(TradeReturnFeeRecord entity);

    /**
     * 审核不通过返还会员单位手续费
     * @param entity
     * @return
     */
    int returnWithDrawfeeMoneyByUnits(TradeReturnFeeRecord entity);

    /**
     * 审核不通过返还代理商手续费
     * @param entity
     * @return
     */
    int returnWithDrawfeeMoneyByAgent(TradeReturnFeeRecord entity);

    /**
     * 审核不通过返还市场管理部手续费
     * @param entity
     * @return
     */
    int returnWithDrawfeeMoneyByCenter(TradeReturnFeeRecord entity);
}
