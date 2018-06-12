package com.rmkj.microcap.modules.moneychange.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangePageBean;

import java.util.List;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface IMoneyChangeDao extends BaseDao<MoneyChangeBean>{


    /**
     *
     * @param moneyChangePageBean
     * @return
     */
    List<MoneyChangePageBean> findMoneyChangePage(MoneyChangePageBean moneyChangePageBean);

    long getTotal(MoneyChangePageBean moneyChangePageBean);
}
