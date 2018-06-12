package com.rmkj.microcap.common.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuBean;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuTreeBean;

import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@DataSource
public interface ISysMenuDao extends BaseDao<SysMenuBean> {

    /**
     * 查询用户的菜单
     *
     * @param menu
     * @return
     */
    List<SysMenuBean> findByUserId(SysMenuBean menu);

    /**
     * 所有菜单
     *
     * @return
     */
    List<SysMenuTreeBean> findAllMenu();

    void deleteRoleMenu(List<String> delMenuIds);

    void deleteMenuList(List<String> menuIds);
}
