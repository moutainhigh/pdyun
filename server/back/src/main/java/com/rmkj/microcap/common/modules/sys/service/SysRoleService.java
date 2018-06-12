package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleMenuBean;
import com.rmkj.microcap.common.modules.sys.bean.SysUserRoleBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by zhangbowen on 2015-9-8.
 */
@Service
@Transactional
public class SysRoleService implements IBaseService<SysRoleBean> {
    @Autowired
    private ISysRoleDao sysRoleDao;

    @Override
    public SysRoleBean get(String id) {
        return sysRoleDao.get(id);
    }

    @Override
    public int delete(String id) {
        //删除与用户的关系
        SysUserRoleBean userRoleBean = new SysUserRoleBean();
        userRoleBean.setRoleId(id);
        this.sysRoleDao.deleteUserRole(userRoleBean);
        //删除与按钮的关系
        sysRoleDao.deleteRoleMenu(id);

        return sysRoleDao.delete(id);

    }

    @Override
    public int insert(SysRoleBean sysRoleBean) {
        sysRoleBean.preInsert();
        return sysRoleDao.insert(sysRoleBean);
    }

    @Override
    public int update(SysRoleBean sysRoleBean) {
        sysRoleBean.preUpdate();
        return sysRoleDao.update(sysRoleBean);
    }

    @Override
    public List<SysRoleBean> queryList(SysRoleBean sysRoleBean) {
        return sysRoleDao.queryList(sysRoleBean);
    }

    public List<String> findSelectMenuByRoleId(String roldId) {
        return sysRoleDao.findSelectMenuByRoleId(roldId);
    }

    /**
     * 新增角色菜单关系
     *
     * @param roleId
     * @param sysRoleMenuBeans
     */
    public void insertRoleMenu(String roleId, List<SysRoleMenuBean> sysRoleMenuBeans) {
        //删除与按钮的关系
        sysRoleDao.deleteRoleMenu(roleId);
        //新增关系
        if (sysRoleMenuBeans.size() > 0) {
            sysRoleDao.batchInsertRoleMenu(sysRoleMenuBeans);
        }
    }
}
