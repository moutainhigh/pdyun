package com.rmkj.microcap.modules.usermessage.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.usermessage.dao.IUserMessageDao;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
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
public class UserMessageService implements IBaseService<UserMessageBean> {
    @Autowired
    private IUserMessageDao userMessageDao;

    @Override
    public UserMessageBean get(String id){
        return userMessageDao.get(id);
    }
    @Override
    public int update(UserMessageBean userMessageBean){
        userMessageBean.preUpdate();
        return userMessageDao.update(userMessageBean);
    }
    @Override
    public int delete(String id){
        return userMessageDao.delete(id);
    }
    @Override
    public int insert(UserMessageBean userMessageBean){
        userMessageBean.preInsert();
        return userMessageDao.insert(userMessageBean);
    }
    @Override
    public List<UserMessageBean> queryList(UserMessageBean userMessageBean){
        return userMessageDao.queryList(userMessageBean);
    }
}
