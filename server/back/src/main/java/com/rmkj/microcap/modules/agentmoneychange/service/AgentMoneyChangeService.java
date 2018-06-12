package com.rmkj.microcap.modules.agentmoneychange.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.agentmoneychange.dao.IAgentMoneyChangeDao;
import com.rmkj.microcap.modules.agentmoneychange.entity.AgentMoneyChangeBean;
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
public class AgentMoneyChangeService implements IBaseService<AgentMoneyChangeBean> {
    @Autowired
    private IAgentMoneyChangeDao agentMoneyChangeDao;

    @Override
    public AgentMoneyChangeBean get(String id){
        return agentMoneyChangeDao.get(id);
    }
    @Override
    public int update(AgentMoneyChangeBean agentMoneyChangeBean){
        agentMoneyChangeBean.preUpdate();
        return agentMoneyChangeDao.update(agentMoneyChangeBean);
    }
    @Override
    public int delete(String id){
        return agentMoneyChangeDao.delete(id);
    }
    @Override
    public int insert(AgentMoneyChangeBean agentMoneyChangeBean){
        agentMoneyChangeBean.preInsert();
        return agentMoneyChangeDao.insert(agentMoneyChangeBean);
    }
    @Override
    public List<AgentMoneyChangeBean> queryList(AgentMoneyChangeBean agentMoneyChangeBean){
        return agentMoneyChangeDao.queryList(agentMoneyChangeBean);
    }
}
