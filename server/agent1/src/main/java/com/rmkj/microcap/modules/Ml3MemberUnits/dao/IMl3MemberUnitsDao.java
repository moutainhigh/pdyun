package com.rmkj.microcap.modules.Ml3MemberUnits.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeeRecord;

import java.util.List;

/**
* Created by Administrator on 2016-11-17.
*/
@DataSource
public interface IMl3MemberUnitsDao extends BaseDao<Ml3MemberUnitsBean>{
    void open(Ml3MemberUnitsBean entity);//启用

    void close(Ml3MemberUnitsBean entity);//停用
    List<Ml3MemberUnitsBean> muList(String centerId); //会员单位列表
    List<Ml3MemberUnitsBean> memberUnitsList(Ml3MemberUnitsBean entity); //会员单位列表

    /**
     *
     * @param opearteCenterUserId
     * @return
     */
    List<Ml3MemberUnitsBean> findMemberUnitsByAgentId(String opearteCenterUserId);

    /**
     * 修改会员单位保证金余额
     * @param ml3MemberUnitsBean
     * @return
     */
    int updateMemberUnitsMoney(Ml3MemberUnitsBean ml3MemberUnitsBean);

    /**
     * 代理商提现手续费时，修改代理商返手续费余额
     * @param entity
     * @return
     */
    int updateReturnFeeMoneyUnits(TradeReturnFeeRecord entity);
    int updateReturnServiceFeeMoneyUnits(TradeReturnFeeRecord entity);
}
