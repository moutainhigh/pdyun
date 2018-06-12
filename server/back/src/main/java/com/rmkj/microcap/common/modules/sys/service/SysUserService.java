package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.bean.SysUserRoleBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysRoleDao;
import com.rmkj.microcap.common.modules.sys.dao.ISysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangbowen on 2015-9-8.
 */
@Service
@Transactional
public class SysUserService implements IBaseService<SysUserBean> {
    @Autowired
    private ISysUserDao sysUserDao;
    @Autowired
    private ISysRoleDao sysRoleDao;

    @Override
    public SysUserBean get(String id) {
        return sysUserDao.get(id);
    }

    @Override
    public int delete(String id) {
        //删除所有关系
        SysUserRoleBean userRoleBean = new SysUserRoleBean();
        userRoleBean.setUserId(id);
        this.sysRoleDao.deleteUserRole(userRoleBean);
        return sysUserDao.delete(id);
    }

    @Override
    public int insert(SysUserBean sysUserBean) {
        sysUserBean.preInsert();
        sysUserBean.setPassword(SystemService.entryptPassword(sysUserBean.getPassword()));
        this.batchInsertRoles(sysUserBean);
        return sysUserDao.insert(sysUserBean);
    }

    /**
     * 批量插入用户角色关系
     *
     * @param sysUserBean
     */
    private void batchInsertRoles(SysUserBean sysUserBean) {
        String[] roleIds = sysUserBean.getRoleAttr();
        if (roleIds != null && roleIds.length > 0) {
            List<SysUserRoleBean> roleBeanList = new ArrayList<>();
            for (String roleId : roleIds) {
                roleBeanList.add(new SysUserRoleBean(sysUserBean.getId(), roleId));
            }
            sysRoleDao.batchInsertUserRole(roleBeanList);
        }
    }

    @Override
    public int update(SysUserBean sysUserBean) {
        sysUserBean.preUpdate();
        if (!StringUtils.isEmpty(sysUserBean.getPassword())) {
            sysUserBean.setPassword(SystemService.entryptPassword(sysUserBean.getPassword()));
        }
        //删除所有关系
        SysUserRoleBean userRoleBean = new SysUserRoleBean();
        userRoleBean.setUserId(sysUserBean.getId());
        this.sysRoleDao.deleteUserRole(userRoleBean);
        //新增关系
        this.batchInsertRoles(sysUserBean);

        return sysUserDao.update(sysUserBean);
    }

    @Override
    public List<SysUserBean> queryList(SysUserBean sysUserBean) {
        return sysUserDao.queryList(sysUserBean);
    }

    public List<String> findSelectRoleByUserId(String id) {
        return this.sysUserDao.findSelectRoleByUserId(id);
    }
}
