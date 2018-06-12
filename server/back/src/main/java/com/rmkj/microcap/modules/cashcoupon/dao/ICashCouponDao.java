package com.rmkj.microcap.modules.cashcoupon.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.modules.cashcoupon.entity.CashCouponBean;

import java.util.List;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface ICashCouponDao extends BaseDao<CashCouponBean>{
    int saveSysdict(SysDictBean bean);
    List<SysDictBean> dictList();
}
