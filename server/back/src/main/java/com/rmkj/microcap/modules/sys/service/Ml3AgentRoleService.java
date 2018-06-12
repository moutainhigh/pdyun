package com.rmkj.microcap.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.dao.IMl3AgentRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* Created by Administrator on 2016-11-15.
*/
@Service
@Transactional
public class Ml3AgentRoleService implements IBaseService<Ml3AgentRoleBean> {
    @Autowired
    private IMl3AgentRoleDao ml3AgentRoleDao;

    @Override
    public Ml3AgentRoleBean get(String id){
        return ml3AgentRoleDao.get(id);
    }
    @Override
    public int update(Ml3AgentRoleBean ml3AgentRoleBean){
        ml3AgentRoleBean.preUpdate();
        return ml3AgentRoleDao.update(ml3AgentRoleBean);
    }
    @Override
    public int delete(String id){
        return ml3AgentRoleDao.delete(id);
    }
    @Override
    public int insert(Ml3AgentRoleBean ml3AgentRoleBean){
        ml3AgentRoleBean.preInsert();
        return ml3AgentRoleDao.insert(ml3AgentRoleBean);
    }
    @Override
    public List<Ml3AgentRoleBean> queryList(Ml3AgentRoleBean ml3AgentRoleBean){
        return ml3AgentRoleDao.queryList(ml3AgentRoleBean);
    }
}
