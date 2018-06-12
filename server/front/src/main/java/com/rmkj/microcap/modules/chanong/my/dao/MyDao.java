package com.rmkj.microcap.modules.chanong.my.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.chanong.my.bean.*;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.trade.entity.Trade;

import java.util.List;

@DataSource
public interface MyDao {
    MyStatisticBean  getMyStatistic(String userId);

    int addMyAddress(MyAddressBean myAddressBean);

    int updAddress(MyAddressBean myAddressBean);

    List<MyAddressBean> getMyAddress(String userId);

    int setDefaultAddress(String id);

    int setOtherAddress(String id);

    int deleteMyAddress(String id);

    List<Trade> getHoldTrade(MyTradeBean myTradeBean);

    List<Trade> getSellTrade(MyTradeBean myTradeBean);

    List<MoneyRecord> getUserMoneyRecord(MoneyBean moneyBean);

    int addBankCard(BankCardBean bankCardBean);

    int updateBankCard(BankCardBean bankCardBean);

    BankCardBean alreadyHasCard(String userId);

    BankCardBean getBankCard(String userId);
}