package com.rmkj.microcap.modules.money;

import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.modules.money.dao.MoneyDao;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.money.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by renwp on 2017/5/27.
 */
@Service
public final class MoneyInHelper {

    private final static AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private MoneyDao moneyDao;

    @Autowired
    MoneyService moneyService;

    /**
     * 获取流水号
     * @return
     */
    public static String serialNo1(){
        if(atomicInteger.get() < 0){
            atomicInteger.set(0);
        }
        return "MO" + new Date().getTime()+""+String.format("%1$05d", atomicInteger.incrementAndGet()%100000).replace("-", "1");
    }

    private static final Random random = new Random();

    private static String serialNo(){
        return "MO" + new Date().getTime()+""+String.format("%1$03d", random.nextInt(1000));
    }

    public static MoneyRecord moneyRecord(BigDecimal money, BigDecimal fee, String channel){
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setUserId(AuthContext.getUserId());
        moneyRecord.setSerialNo(serialNo());
        moneyRecord.setCreateTime(new Date());
        moneyRecord.setChannel(channel);
        moneyRecord.setMoney(money);
        moneyRecord.setFee(fee);
        moneyRecord.setType(Constants.Money.MONEY_RECORD_TYPE_IN);
        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_DEFAULT);
        moneyRecord.preInsert();
        return moneyRecord;
    }

//    public void addMoney(BigDecimal money, String userId, String channel){
//        MoneyRecord moneyRecord = MoneyInHelper.moneyRecord(money, null, channel);
//        moneyRecord.setUserId(userId);
//        moneyRecord.setCompleteTime(new Date());
//        moneyRecord.setStatus(Constants.Money.MONEY_RECORD_STATUS_SUCCESS);
//        moneyDao.record(moneyRecord);
//        moneyService.changeMoney(moneyRecord.getUserId(), moneyRecord.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_MONEY_IN, moneyRecord.getSerialNo());
//    }
}
