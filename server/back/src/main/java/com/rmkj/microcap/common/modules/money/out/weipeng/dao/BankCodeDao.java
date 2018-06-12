package com.rmkj.microcap.common.modules.money.out.weipeng.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.BankCodeBean;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.modules.cashcoupon.entity.CashCouponBean;

import java.util.List;

/**
 * TODO 威鹏支付-支行信息Dao
 */
@DataSource
public interface BankCodeDao extends BaseDao<CashCouponBean>{

    /**
     * TODO 根据支行名称查询支行信息
     * @param bankName
     * @return
     */
    BankCodeBean findBankCodeByName(String bankName);
}
