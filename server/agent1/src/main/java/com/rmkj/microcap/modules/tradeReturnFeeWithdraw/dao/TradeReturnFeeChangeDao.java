package com.rmkj.microcap.modules.tradeReturnFeeWithdraw.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeeChange;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
@DataSource
public interface TradeReturnFeeChangeDao {

    /**
     * 添加市场管理部或会员单位或代理商，返手续费提现记录
     * @param entity
     * @return
     */
    int insertWithDrawChange(TradeReturnFeeChange entity);
}
