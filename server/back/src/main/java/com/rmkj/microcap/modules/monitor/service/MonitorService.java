package com.rmkj.microcap.modules.monitor.service;

import com.rmkj.microcap.modules.monitor.dao.MonitorDao;
import com.rmkj.microcap.modules.monitor.entity.DataSummary;
import com.rmkj.microcap.modules.monitor.entity.TodayDataSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by renwp on 2016/9/27.
 */
@Service
@Transactional
public class MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    public DataSummary queryAll() {
        DataSummary dataSummary = new DataSummary();
        dataSummary.setUserMoney(monitorDao.selectUserMoney());
        dataSummary.setUserNumbers(monitorDao.selectUserNumbers());
        dataSummary.setUserProfitAndLoss(monitorDao.selectUserProfitAndLoss());
        dataSummary.setUserRecharge(monitorDao.selectUserRecharge());
        dataSummary.setUserRechargePens(monitorDao.selectUserRechargePens());
        dataSummary.setUserWithdrawalsNumbers(monitorDao.selectUserWithdrawalsNumbers());
        dataSummary.setUserWithdrawalsPens(monitorDao.selectUserWithdrawalsPens());
        dataSummary.setWithdrawalsMoney(monitorDao.selectAllWithdrawalsMoney());
        //公式拼接
        BigDecimal profitAll = monitorDao.getProfitAll();
        BigDecimal lossAll = monitorDao.getLossAll();
        if(profitAll.compareTo(new BigDecimal(0)) == 0 && lossAll.compareTo(new BigDecimal(0)) == 0){
            dataSummary.setFormula("0");
        }else{
            dataSummary.setFormula(profitAll.toString().concat("-").concat(lossAll.multiply(new BigDecimal(-1)).toString())
                    .concat("=").concat(profitAll.add(lossAll).toString()).concat("(总盈利-总亏损=差额)"));
        }
        return dataSummary;
    }

    public DataSummary queryAllSectionDate(DataSummary dataSummary) {
        dataSummary.setUserMoney(monitorDao.selectUserMoney());
        dataSummary.setUserNumbers(monitorDao.selectSectionUserNumbers(dataSummary));
        dataSummary.setUserProfitAndLoss(monitorDao.selectSectionUserProfitAndLoss(dataSummary));
        dataSummary.setUserRecharge(monitorDao.selectSectionUserRecharge(dataSummary));
        dataSummary.setUserRechargePens(monitorDao.selectSectionUserRechargePens(dataSummary));
        dataSummary.setUserWithdrawalsNumbers(monitorDao.selectSectionUserWithdrawalsNumbers(dataSummary));
        dataSummary.setUserWithdrawalsPens(monitorDao.selectSectionUserWithdrawalsPens(dataSummary));

        return dataSummary;
    }

    public TodayDataSummary queryToday() {
        TodayDataSummary todayDataSummary = new TodayDataSummary();
        todayDataSummary.setRegisterNumbers(monitorDao.selectTodayRegisterNumbers());
        todayDataSummary.setRechargeMoney(monitorDao.selectTodayRechargeMoney());
        todayDataSummary.setWithdrawalsPeoples(monitorDao.selectTodayWithdrawalsPeoples());
        todayDataSummary.setWithdrawalsMoney(monitorDao.selectTodayWithdrawalsMoney());
        todayDataSummary.setTradePens(monitorDao.selectTodayTradePens());
        todayDataSummary.setProfitAndLossMoney(monitorDao.selectTodayProfitAndLossMoney());
        //公式拼接
        BigDecimal profitToday = monitorDao.getProfitToday();
        BigDecimal lossToday = monitorDao.getLossToday();
        if(profitToday.compareTo(new BigDecimal(0)) == 0 && lossToday.compareTo(new BigDecimal(0)) == 0){
            todayDataSummary.setFormula("0");
        }else{
            todayDataSummary.setFormula(profitToday.toString().concat("-").concat(lossToday.multiply(new BigDecimal(-1)).toString()).concat("=")
                    .concat(profitToday.subtract(lossToday).toString()).concat("(总盈利-总亏损=差额)"));
        }
        return todayDataSummary;
    }

    public TodayDataSummary queryDateSection(TodayDataSummary todayDataSummary) {
        todayDataSummary.setRegisterNumbers(monitorDao.selectSectionTimeRegisterNumbers(todayDataSummary));
        todayDataSummary.setRechargeMoney(monitorDao.selectSectionRechargeMoney(todayDataSummary));
        todayDataSummary.setWithdrawalsPeoples(monitorDao.selectSectionWithdrawalsPeoples(todayDataSummary));
        todayDataSummary.setWithdrawalsMoney(monitorDao.selectSectionWithdrawalsMoney(todayDataSummary));
        todayDataSummary.setTradePens(monitorDao.selectSectionTradePens(todayDataSummary));
        todayDataSummary.setProfitAndLossMoney(monitorDao.selectSectionProfitAndLossMoney(todayDataSummary));
        return todayDataSummary;
    }
}
