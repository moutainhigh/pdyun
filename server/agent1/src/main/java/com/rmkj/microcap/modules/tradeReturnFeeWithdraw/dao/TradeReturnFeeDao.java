package com.rmkj.microcap.modules.tradeReturnFeeWithdraw.dao;/**
 * Created by Administrator on 2017/9/21.
 */

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.ReturnFeeAgent;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.ReturnFeeMemberUnits;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeePage;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeeRecord;

import java.util.List;

/**
 * @author k
 * @create -09-21-17:24
 **/
@DataSource
public interface TradeReturnFeeDao extends BaseDao{

    int insertTradeFeeWithDraw(TradeReturnFeeRecord returnFeeRecord);

    /**
     * 根据登录角色查询当前角色自己的，返手续提现记录
     * @param entity
     * @return
     */
    List<TradeReturnFeeRecord> findRetrunFeeMoneyRecordByRole(TradeReturnFeePage entity);

    /**
     * 根据登录角色查询当前角色自己的，返手续提现记录总条数
     * @param entity
     * @return
     */
    long findRetrunFeeMoneyRecordByRoleTotal(TradeReturnFeePage entity);

    /**
     * 查询市场管理部下会员单位的手续费出金记录
     * @param entity
     * @return
     */
    List<ReturnFeeMemberUnits> queryReturnFeeMoneyByUnits(TradeReturnFeeRecord entity);

    long queryReturnFeeMoneyByUnitsTotal(TradeReturnFeeRecord entity);

    /**
     * 查询会员单位下代理商的手续费出金
     * @param entity
     * @return
     */
    List<ReturnFeeAgent> queryReturnFeeMoneyByAgent(TradeReturnFeeRecord entity);

    long queryReturnFeeMoneyByAgentTotal(TradeReturnFeeRecord entity);

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


}
