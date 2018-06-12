package com.rmkj.microcap.modules.money.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.money.entity.CashCoupon;

import java.util.Date;
import java.util.List;

/**
 * Created by renwp on 2016/11/24.
 */
@DataSource
public interface CashCouponDao {

    CashCoupon findCashCouponById(String couponId);

    int useCashCoupon(CashCoupon cashCoupon);

    int toExpired(Date date);

    List<CashCoupon> findUserCashCouponCanUse(String userId);

    List<CashCoupon> findCashCouponByUserId(String userId);

    int giveCashCoupon(CashCoupon cashCoupon);

}
