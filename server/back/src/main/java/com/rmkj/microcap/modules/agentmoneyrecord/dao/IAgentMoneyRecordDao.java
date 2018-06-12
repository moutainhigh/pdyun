package com.rmkj.microcap.modules.agentmoneyrecord.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.agentmoneyrecord.entity.AgentMoneyRecordBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-7.
*/
@DataSource
public interface IAgentMoneyRecordDao extends BaseDao<AgentMoneyRecordBean>{
    int outPast(AgentMoneyRecordBean bean);//提现申请审核不通过时
    int outPastIn(AgentMoneyRecordBean bean);//提现申请审核通过时
}
