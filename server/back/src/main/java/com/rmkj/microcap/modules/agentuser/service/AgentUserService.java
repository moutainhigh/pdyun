package com.rmkj.microcap.modules.agentuser.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.agentuser.dao.IAgentUserDao;
import com.rmkj.microcap.modules.agentuser.entity.AgentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-11-4.
*/
@Service
@Transactional
public class AgentUserService implements IBaseService<AgentUserBean> {
    @Autowired
    private IAgentUserDao agentUserDao;

    @Override
    public AgentUserBean get(String id){
        return agentUserDao.get(id);
    }
    @Override
    public int update(AgentUserBean agentUserBean){
        agentUserBean.preUpdate();
        return agentUserDao.update(agentUserBean);
    }
    @Override
    public int delete(String id){
        return agentUserDao.delete(id);
    }
    @Override
    public int insert(AgentUserBean agentUserBean){
        agentUserBean.preInsert();
        return agentUserDao.insert(agentUserBean);
    }
    @Override
    public List<AgentUserBean> queryList(AgentUserBean agentUserBean){
        return agentUserDao.queryList(agentUserBean);
    }
}
