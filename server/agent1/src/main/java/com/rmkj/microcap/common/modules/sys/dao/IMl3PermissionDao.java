package com.rmkj.microcap.common.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.common.modules.sys.bean.MlPermissionTreeBean;

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

    void deleteRoleMenu(List<String> delMenuIds);

    void deleteMenuList(List<String> menuIds);
    //根据名称修改href
    void updateHref(Ml3PermissionBean ml3PermissionBean);
    void updateHref1(Ml3PermissionBean ml3PermissionBean);
}
