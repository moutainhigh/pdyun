package com.rmkj.microcap.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.modules.sys.bean.MlPermissionTreeBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
@DataSource
public interface IMl3PermissionDao extends BaseDao<Ml3PermissionBean>{
    /**
     * 查询用户的菜单
     *
     * @param menu
     * @return
     */
    List<Ml3PermissionBean> findByUserId(Ml3PermissionBean menu);

    /**
     * 所有菜单
     *
     * @return
     */
    List<MlPermissionTreeBean> findAllMenu();
    List<MlPermissionTreeBean> findAllMenuById(String id);

    void deleteRoleMenu(List<String> delMenuIds);

    void deleteMenuList(List<String> menuIds);
}
