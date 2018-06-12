package com.rmkj.microcap.modules.ml3MemberUnitisMoneyChange.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.ml3MemberUnitisMoneyChange.entity.Ml3MemberUnitsMoneyChange;

/**
 * Created by Administrator on 2017/4/27.
 */
@DataSource
public interface Ml3MemberUnitisMoneyChangeDao {

    int insert(Ml3MemberUnitsMoneyChange ml3MemberUnitsMoneyChange);
}
