package com.rmkj.microcap.common.modules.money.out;

import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;

/**
 * Created by renwp on 2017/3/22.
 *
 */
public interface MoneyOutServiceInterface {

    /**
     *
     * @param moneyRecord
     */
    void review(WithdrawalsBean moneyRecord);

    /**
     *
     * @param moneyRecord
     */
    void success(WithdrawalsBean moneyRecord);

    /**
     *
     * @param moneyRecord
     */
    void failure(WithdrawalsBean moneyRecord);

    WithdrawalsBean find(String serialNo);
}
