package com.rmkj.microcap.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.modules.sys.bean.Ml3RolePermissionBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
@DataSource
public interface IMl3RoleDao extends BaseDao<Ml3RoleBean>{
    /**
     * 删除角色与菜单权限关系
     *
     * @return
     */
    public int deleteRoleMenu(String roleId);

    /**
     * 删除用户与角色关系
     *
     * @param sysUserRoleBean
     */
    void deleteUserRole(Ml3AgentRoleBean sysUserRoleBean);

    /**
     * 查询数据列表
     *
     * @param
     * @return
     */
    public List<Ml3RoleBean> findList(Ml3RoleBean role);

    void batchInsertUserRole(List<Ml3AgentRoleBean> roleBeanList);

    List<String> findSelectMenuByRoleId(String roldId);

    void batchInsertRoleMenu(List<Ml3RolePermissionBean> sysRoleMenuBeans);
}
