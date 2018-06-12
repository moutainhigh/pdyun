package com.rmkj.microcap.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.sys.bean.Ml3RolePermissionBean;
import com.rmkj.microcap.modules.sys.dao.IMl3RolePermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* Created by Administrator on 2016-11-15.
*/
@Service
@Transactional
public class Ml3RolePermissionService implements IBaseService<Ml3RolePermissionBean> {
    @Autowired
    private IMl3RolePermissionDao ml3RolePermissionDao;

    @Override
    public Ml3RolePermissionBean get(String id){
        return ml3RolePermissionDao.get(id);
    }
    @Override
    public int update(Ml3RolePermissionBean ml3RolePermissionBean){
        ml3RolePermissionBean.preUpdate();
        return ml3RolePermissionDao.update(ml3RolePermissionBean);
    }
    @Override
    public int delete(String id){
        return ml3RolePermissionDao.delete(id);
    }
    @Override
    public int insert(Ml3RolePermissionBean ml3RolePermissionBean){
        ml3RolePermissionBean.preInsert();
        return ml3RolePermissionDao.insert(ml3RolePermissionBean);
    }
    @Override
    public List<Ml3RolePermissionBean> queryList(Ml3RolePermissionBean ml3RolePermissionBean){
        return ml3RolePermissionDao.queryList(ml3RolePermissionBean);
    }
}
