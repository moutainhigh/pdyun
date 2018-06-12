package com.rmkj.microcap.modules.money.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.money.entity.MoneyChange;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.money.entity.ReturnMoneyOut;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by renwp on 2016/10/19.
 */
@DataSource
public interface MoneyDao {
    /**
     * 记录用户资金变更
     * @param moneyChange
     * @return
     */
    int recordChangeMoney(MoneyChange moneyChange);

    /**
     * 充值或者提现
     * @param moneyRecord
     * @return
     */
    int record(MoneyRecord moneyRecord);

    int updateRecord(MoneyRecord moneyRecord);

    MoneyRecord findMoneyRecordBySerialNoWithLock(String serialNo);

    int deletePrePayMoneyRecord(Map<String, String> map);

    int returnMoneyOut(ReturnMoneyOut returnMoneyOut);

    /*
     * TODO 查询用户交易提现记录
     * @param userId 用户id
     */
    List<MoneyRecord> findUserMoneyRecord(String userId);

    //查询某个时间段用户充值处理充的交易记录
    List<MoneyRecord> selChargeResult();

    MoneyRecord findMoneyRecordByThirdSerialNoWithLock(String thirdSerialNo);

    /**
     * 查询用户充提记录
     * @param moneyRecord
     * @return
     */
    List<MoneyRecord> findUserPayMoneyRecord(MoneyRecord moneyRecord);
}
