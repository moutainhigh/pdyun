package com.rmkj.microcap.common.modules.money.out;

import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;

import java.util.List;

/**
 * Created by renwp on 2017/3/20.
 */
public interface MoneyOut {

    /**
     * 成功处理{0}笔提现！
     * 其他全为失败
     * @param list
     * @param moneyOutService
     * @return
     */
    String batchOut(List<WithdrawalsBean> list, MoneyOutServiceInterface moneyOutService);

    /**
     * 0 成功 1 失败 2 处理中 -1 未知
     * @param moneyRecord
     * @param moneyOutService
     * @return
     */
    int queryResult(WithdrawalsBean moneyRecord, MoneyOutServiceInterface moneyOutService);
}
