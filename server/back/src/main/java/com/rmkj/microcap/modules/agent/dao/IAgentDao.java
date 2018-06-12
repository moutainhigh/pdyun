package com.rmkj.microcap.modules.agent.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.agent.entity.AgentBean;
import com.rmkj.microcap.modules.agentreviewrecord.entity.AgentReviewRecordBean;
import com.rmkj.microcap.modules.agentuser.entity.AgentUserBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-4.
*/
@DataSource
public interface IAgentDao extends BaseDao<AgentBean>{
    List<AgentUserBean> getAgentUser(AgentUserBean bean);//邀请用户记录集合
    List<AgentReviewRecordBean> getAgentReview(AgentReviewRecordBean bean);//审核记录集合
    int outPast(AgentBean bean);//经纪人审核不通过时
    int outPastIn(AgentBean bean);//经纪人审核通过时
    void open(AgentBean entity);//启用用户
    void close(AgentBean entity);//停用用户
}
