package com.rmkj.microcap.modules.holdTransTrade.dao;/**
 * Created by Administrator on 2018/5/18.
 */

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.holdTransTrade.entity.HoldTransTradeBean;

import java.util.List;

/**
 * @author k
 * @create -05-18-11:57
 **/
@DataSource
public interface HoldTransTradeDao {

    List<HoldTransTradeBean> queryList(HoldTransTradeBean holdTransTradeBean);

    long queryListTotal(HoldTransTradeBean holdTransTradeBean);

    int insert(HoldTransTradeBean entity);
}
