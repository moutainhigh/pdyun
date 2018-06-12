package com.rmkj.microcap.common.modules.sys.dao;


import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleMenuBean;
import com.rmkj.microcap.common.modules.sys.bean.SysUserRoleBean;

import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@DataSource
public interface ISysRoleDao extends BaseDao<SysRoleBean> {

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
    void deleteUserRole(SysUserRoleBean sysUserRoleBean);

    /**
     * 查询数据列表
     *
     * @param
     * @return
     */
    public List<SysRoleBean> findList(SysRoleBean role);

    void batchInsertUserRole(List<SysUserRoleBean> roleBeanList);

    List<String> findSelectMenuByRoleId(String roldId);

    void batchInsertRoleMenu(List<SysRoleMenuBean> sysRoleMenuBeans);
}
