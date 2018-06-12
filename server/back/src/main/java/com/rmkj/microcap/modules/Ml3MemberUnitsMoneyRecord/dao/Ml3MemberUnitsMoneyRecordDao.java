package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@DataSource
public interface Ml3MemberUnitsMoneyRecordDao {

    int insert(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 查询会员单位全部出金记录
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    List<Ml3MemberUnitsMoneyRecord> findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    Ml3MemberUnitsMoneyRecord get(String id);

    /**
     * 根据id选择性修改会员单位出金记录
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    int updateByPrimaryKeySelective(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 查询出金总额
     */
    BigDecimal unitsMoneySum(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 查询会员单位未出金总额
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    BigDecimal unitsNoMoneyOutTotal(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 查询会员单位已出金额
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    BigDecimal unitsMoneyOutTotal(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 修改出金审核状态为通过
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    int review(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 修改状态为成功
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    int updateSuccess(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);
}
