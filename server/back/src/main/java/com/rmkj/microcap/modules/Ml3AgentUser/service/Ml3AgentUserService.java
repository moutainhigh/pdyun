package com.rmkj.microcap.modules.Ml3AgentUser.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.Ml3AgentUser.dao.IMl3AgentUserDao;
import com.rmkj.microcap.modules.Ml3AgentUser.entity.Ml3AgentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3AgentUserService implements IBaseService<Ml3AgentUserBean> {
    @Autowired
    private IMl3AgentUserDao ml3AgentUserDao;

    @Override
    public Ml3AgentUserBean get(String id){
        return ml3AgentUserDao.get(id);
    }
    @Override
    public int update(Ml3AgentUserBean ml3AgentUserBean){
        ml3AgentUserBean.preUpdate();
        return ml3AgentUserDao.update(ml3AgentUserBean);
    }
    @Override
    public int delete(String id){
        return ml3AgentUserDao.delete(id);
    }
    @Override
    public int insert(Ml3AgentUserBean ml3AgentUserBean){
        ml3AgentUserBean.preInsert();
        return ml3AgentUserDao.insert(ml3AgentUserBean);
    }
    @Override
    public List<Ml3AgentUserBean> queryList(Ml3AgentUserBean ml3AgentUserBean){
        return ml3AgentUserDao.queryList(ml3AgentUserBean);
    }
}
