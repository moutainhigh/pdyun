package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@DataSource
public interface Ml3MemberUnitsMoneyRecordDao {

    int insert(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

    /**
     * 查询全部会员单位出金记录
     * @param ml3MemberUnitsMoneyRecord
     * @return
     */
    List<Ml3MemberUnitsMoneyRecord> findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord);

}
