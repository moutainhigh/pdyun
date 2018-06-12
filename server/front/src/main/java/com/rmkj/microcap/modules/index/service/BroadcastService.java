package com.rmkj.microcap.modules.index.service;

import com.rmkj.microcap.common.bean.PagerBean;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.index.dao.BroadcastDao;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.trade.bean.UserTradeBean;
import com.rmkj.microcap.modules.tradeMarket.dao.TradeMarketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by renwp on 2016/10/18.
 */
@Service
public class BroadcastService {

    @Autowired
    private BroadcastDao broadcastDao;

    @Autowired
    private TradeMarketDao tradeMarketDao;

    public List<Broadcast> findList() {
        return broadcastDao.findList();
    }

    public Broadcast findById(String id) {
        return broadcastDao.findById(id);
    }

    public List<UserTradeBean> queryWinProfitRecord(){
        return tradeMarketDao.queryWinProfitRecord();
    }

    public PagerBean queryBroadcastList(Broadcast broadcast){
        List<Broadcast> broadcasts = broadcastDao.queryBroadcastList(broadcast);
        return new PagerBean<Broadcast>(broadcasts, MybatisPagerInterceptor.getTotal());
    }
}
