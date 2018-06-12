package com.rmkj.microcap.modules.Ml3MemberUnits.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;

import java.util.List;

/**
* Created by Administrator on 2016-11-17.
*/
@DataSource
public interface IMl3MemberUnitsDao extends BaseDao<Ml3MemberUnitsBean>{
    void open(Ml3MemberUnitsBean entity);//启用

    void close(Ml3MemberUnitsBean entity);//停用
    List<Ml3MemberUnitsBean> muList(); //会员单位列表
    List<Ml3MemberUnitsBean> memberUnitsList(Ml3MemberUnitsBean entity); //会员单位列表

    /**
     * 追加会员单位保证金
     * @param ml3MemberUnitsBean
     * @return
     */
    int addUnitsBondMoney(Ml3MemberUnitsBean ml3MemberUnitsBean);

    /**
     * 返还会员单位提现保证金金额
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    int returnUnitsMOney(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);
}
