package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3AgentDao;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.Object;
import java.util.ArrayList;
import java.util.List;


/**
* Created by Administrator on 2016-11-15.
*/
@Service
@Transactional
public class Ml3AgentService implements IBaseService<Ml3AgentBean> {
    @Autowired
    private IMl3AgentDao ml3AgentDao;
    @Autowired
    private IMl3RoleDao ml3RoleDao;

    @Override
    public Ml3AgentBean get(String id){
        return ml3AgentDao.get(id);
    }
    @Override
    public int update(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preUpdate();
        if (!StringUtils.isEmpty(ml3AgentBean.getPassword())) {
            ml3AgentBean.setPassword(SystemService.entryptPassword(ml3AgentBean.getPassword()));
        }
        //删除所有关系
        Ml3AgentRoleBean userRoleBean = new Ml3AgentRoleBean();
        userRoleBean.setAgentId(ml3AgentBean.getId());
        this.ml3RoleDao.deleteUserRole(userRoleBean);
        //新增关系
        this.batchInsertRoles(ml3AgentBean);

        return ml3AgentDao.update(ml3AgentBean);
    }
    @Override
    public int delete(String id){
        //删除所有关系
        Ml3AgentRoleBean userRoleBean = new Ml3AgentRoleBean();
        userRoleBean.setAgentId(id);
        this.ml3RoleDao.deleteUserRole(userRoleBean);
        return ml3AgentDao.delete(id);
    }
    @Override
    public int insert(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        ml3AgentBean.setPassword(SystemService.entryptPassword(ml3AgentBean.getPassword()));
        this.batchInsertRoles(ml3AgentBean);
        return ml3AgentDao.insert(ml3AgentBean);
    }
    /**
     * 批量插入用户角色关系
     *
     * @param sysUserBean
     */
    private void batchInsertRoles(Ml3AgentBean sysUserBean) {
        String[] roleIds = sysUserBean.getRoleAttr();
        if (roleIds != null && roleIds.length > 0) {
            List<Ml3AgentRoleBean> roleBeanList = new ArrayList<>();
            for (String roleId : roleIds) {
                roleBeanList.add(new Ml3AgentRoleBean(sysUserBean.getId(), roleId));
            }
            ml3RoleDao.batchInsertUserRole(roleBeanList);
        }
    }
    @Override
    public List<Ml3AgentBean> queryList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.queryList(ml3AgentBean);
    }
    public List<String> findSelectRoleByUserId(String id) {
        return this.ml3AgentDao.findSelectRoleByUserId(id);
    }
}
