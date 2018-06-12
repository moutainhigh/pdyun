package com.rmkj.microcap.modules.monitor.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.monitor.entity.DataSummary;
import com.rmkj.microcap.modules.monitor.entity.TodayDataSummary;
import com.rmkj.microcap.modules.user.entity.UserBean;

import java.math.BigDecimal;

/**
 * Created by renwp on 2016/9/27.
 */
@DataSource
public interface MonitorDao extends BaseDao<UserBean> {

    BigDecimal selectUserMoney();

    Long selectUserNumbers();
    Long selectSectionUserNumbers(DataSummary dataSummary);

    BigDecimal selectUserProfitAndLoss();
    BigDecimal selectSectionUserProfitAndLoss(DataSummary dataSummary);

    BigDecimal selectUserRecharge();
    BigDecimal selectSectionUserRecharge(DataSummary dataSummary);

    Long selectUserRechargePens();
    Long selectSectionUserRechargePens(DataSummary dataSummary);

    Long selectUserWithdrawalsNumbers();
    Long selectSectionUserWithdrawalsNumbers(DataSummary dataSummary);

    Long selectUserWithdrawalsPens();
    Long selectSectionUserWithdrawalsPens(DataSummary dataSummary);

    long selectTodayRegisterNumbers();
    long selectSectionTimeRegisterNumbers(TodayDataSummary todayDataSummary);

    BigDecimal selectTodayRechargeMoney();
    BigDecimal selectSectionRechargeMoney(TodayDataSummary todayDataSummary);

    long selectTodayWithdrawalsPeoples();
    long selectSectionWithdrawalsPeoples(TodayDataSummary todayDataSummary);

    BigDecimal selectTodayWithdrawalsMoney();
    BigDecimal selectSectionWithdrawalsMoney(TodayDataSummary todayDataSummary);

    long selectTodayTradePens();
    long selectSectionTradePens(TodayDataSummary todayDataSummary);

    BigDecimal selectTodayProfitAndLossMoney();
    BigDecimal selectSectionProfitAndLossMoney(TodayDataSummary todayDataSummary);

    BigDecimal getProfitAll();
    BigDecimal getLossAll();
    BigDecimal getProfitToday();
    BigDecimal getLossToday();
    BigDecimal selectAllWithdrawalsMoney();
}
