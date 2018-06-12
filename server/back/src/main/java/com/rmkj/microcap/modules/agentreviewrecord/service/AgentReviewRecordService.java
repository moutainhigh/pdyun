package com.rmkj.microcap.modules.agentreviewrecord.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.agentreviewrecord.dao.IAgentReviewRecordDao;
import com.rmkj.microcap.modules.agentreviewrecord.entity.AgentReviewRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-11-7.
*/
@Service
@Transactional
public class AgentReviewRecordService implements IBaseService<AgentReviewRecordBean> {
    @Autowired
    private IAgentReviewRecordDao agentReviewRecordDao;

    @Override
    public AgentReviewRecordBean get(String id){
        return agentReviewRecordDao.get(id);
    }
    @Override
    public int update(AgentReviewRecordBean agentReviewRecordBean){
        agentReviewRecordBean.preUpdate();
        return agentReviewRecordDao.update(agentReviewRecordBean);
    }
    @Override
    public int delete(String id){
        return agentReviewRecordDao.delete(id);
    }
    @Override
    public int insert(AgentReviewRecordBean agentReviewRecordBean){
        agentReviewRecordBean.preInsert();
        return agentReviewRecordDao.insert(agentReviewRecordBean);
    }
    @Override
    public List<AgentReviewRecordBean> queryList(AgentReviewRecordBean agentReviewRecordBean){
        return agentReviewRecordDao.queryList(agentReviewRecordBean);
    }
}
