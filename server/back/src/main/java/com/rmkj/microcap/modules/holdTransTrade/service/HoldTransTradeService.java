package com.rmkj.microcap.modules.holdTransTrade.service;/**
 * Created by Administrator on 2018/5/18.
 */

import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.modules.holdTransTrade.dao.HoldTransTradeDao;
import com.rmkj.microcap.modules.holdTransTrade.entity.HoldTransTradeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author k
 * @create -05-18-17:50
 **/
@Service
public class HoldTransTradeService {

    @Autowired
    private HoldTransTradeDao holdTransTradeDao;

    public GridPager queryList(HoldTransTradeBean entity){
        GridPager pager = new GridPager();
        pager.setRows(holdTransTradeDao.queryList(entity));
        pager.setTotal((int) holdTransTradeDao.queryListTotal(entity));
        return pager;
    }
}
