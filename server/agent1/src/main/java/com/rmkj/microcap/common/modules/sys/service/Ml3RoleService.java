package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RolePermissionBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3RoleDao;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-11-15.
*/
@Service
@Transactional
public class Ml3RoleService implements IBaseService<Ml3RoleBean> {
    @Autowired
    private IMl3RoleDao ml3RoleDao;

    @Override
    public Ml3RoleBean get(String id){
        return ml3RoleDao.get(id);
    }
    @Override
    public int update(Ml3RoleBean ml3RoleBean){
        ml3RoleBean.preUpdate();
        return ml3RoleDao.update(ml3RoleBean);
    }
    @Override
    public int delete(String id){
        //删除与用户的关系
        Ml3AgentRoleBean userRoleBean = new Ml3AgentRoleBean();
        userRoleBean.setRoleId(id);
        this.ml3RoleDao.deleteUserRole(userRoleBean);
        //删除与按钮的关系
        ml3RoleDao.deleteRoleMenu(id);

        return ml3RoleDao.delete(id);
    }
    @Override
    public int insert(Ml3RoleBean ml3RoleBean){
        ml3RoleBean.preInsert();
        return ml3RoleDao.insert(ml3RoleBean);
    }
    @Override
    public List<Ml3RoleBean> queryList(Ml3RoleBean ml3RoleBean){
        return ml3RoleDao.queryList(ml3RoleBean);
    }
    public List<String> findSelectMenuByRoleId(String roldId) {
        return ml3RoleDao.findSelectMenuByRoleId(roldId);
    }

    /**
     * 新增角色菜单关系
     *
     * @param roleId
     * @param sysRoleMenuBeans
     */
    public void insertRoleMenu(String roleId, List<Ml3RolePermissionBean> sysRoleMenuBeans) {
        //删除与按钮的关系
        ml3RoleDao.deleteRoleMenu(roleId);
        //新增关系
        if (sysRoleMenuBeans.size() > 0) {
            ml3RoleDao.batchInsertRoleMenu(sysRoleMenuBeans);
        }
    }
   public Ml3RoleBean selectRoleIdByName(Ml3RoleBean role){
        return ml3RoleDao.selectRoleIdByName(role);
    }
}
