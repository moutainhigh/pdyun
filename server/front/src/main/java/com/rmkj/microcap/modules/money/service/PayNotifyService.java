package com.rmkj.microcap.modules.money.service;

import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.modules.money.MoneyInHelper;
import com.rmkj.microcap.modules.money.dao.MoneyDao;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * Created by renwp on 2017/5/27.
 */
@Service
@Transactional
public class PayNotifyService implements NotifyInterface {

    @Autowired
    MoneyService moneyService;

    @Autowired
    private MoneyDao moneyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    MoneyInHelper moneyInHelper;

    @Override
    public boolean success(String serialNo, String thirdSerialNo) {
        return doWith(serialNo, thirdSerialNo, "SUCCESS");
    }

    @Override
    public boolean failure(String serialNo, String thirdSerialNo) {
        return doWith(serialNo, thirdSerialNo, "FAILURE");
    }

    private boolean doWith(String serialNo, String thirdSerialNo, String status){
        MoneyRecord m;
        if(StringUtils.isBlank(serialNo)){
            m = moneyDao.findMoneyRecordByThirdSerialNoWithLock(thirdSerialNo);
        }else{
            m = moneyDao.findMoneyRecordBySerialNoWithLock(serialNo);
        }
        if(m == null){
            return false;
        }
        // 如果已经处理直接返回成功
        if(Constants.Money.MONEY_RECORD_STATUS_SUCCESS.equals(m.getStatus())
                || Constants.Money.MONEY_RECORD_STATUS_FAIL.equals(m.getStatus())){
            return true;
        }
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setThirdSerialNo(thirdSerialNo);
        moneyRecord.setSerialNo(serialNo);
        moneyRecord.setCompleteTime(new Date());
        if("SUCCESS".equals(status)){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_SUCCESS);
        }else if("FAILURE".equals(status)){
            moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);
            moneyRecord.setFailureReason("支付失败");
        }else {
            return false;
        }

        User user = userDao.findUserById(m.getUserId());

        /*if("SUCCESS".equals(status) && userDao.findOutMoneyList(user).intValue() == 0){
        }*/

        if(moneyDao.updateRecord(moneyRecord) != 1
                || ("SUCCESS".equals(status) && !moneyService.changeMoney(m.getUserId(), m.getMoney().subtract(m.getFee()), user.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN, serialNo))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }


}
