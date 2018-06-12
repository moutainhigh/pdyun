package com.rmkj.microcap.modules.tradebalancetime.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.tradebalancetime.dao.ITradeBalanceTimeDao;
import com.rmkj.microcap.modules.tradebalancetime.entity.TradeBalanceTimeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class TradeBalanceTimeService implements IBaseService<TradeBalanceTimeBean> {
    @Autowired
    private ITradeBalanceTimeDao tradeBalanceTimeDao;

    @Override
    public TradeBalanceTimeBean get(String id){
        return tradeBalanceTimeDao.get(id);
    }
    @Override
    public int update(TradeBalanceTimeBean tradeBalanceTimeBean){
        tradeBalanceTimeBean.preUpdate();
        return tradeBalanceTimeDao.update(tradeBalanceTimeBean);
    }
    @Override
    public int delete(String id){
        return tradeBalanceTimeDao.delete(id);
    }
    @Override
    public int insert(TradeBalanceTimeBean tradeBalanceTimeBean){
        tradeBalanceTimeBean.preInsert();
        return tradeBalanceTimeDao.insert(tradeBalanceTimeBean);
    }
    @Override
    public List<TradeBalanceTimeBean> queryList(TradeBalanceTimeBean tradeBalanceTimeBean){
        return tradeBalanceTimeDao.queryList(tradeBalanceTimeBean);
    }
}
