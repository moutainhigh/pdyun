package com.rmkj.microcap.modules.Ml3MemberUnitisMoneyChange.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3MemberUnitisMoneyChange.entity.Ml3MemberUnitsMoneyChange;

/**
 * 会员单位保证金浮动记录
 * Created by Administrator on 2017/5/9.
 */
@DataSource
public interface Ml3MemberUnitisMoneyChangeDao {

    int insert(Ml3MemberUnitsMoneyChange ml3MemberUnitsMoneyChange);
}
