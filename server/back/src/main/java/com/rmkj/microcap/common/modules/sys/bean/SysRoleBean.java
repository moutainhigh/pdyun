package com.rmkj.microcap.common.modules.sys.bean;


import com.rmkj.microcap.common.bean.DataEntity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
public class SysRoleBean extends DataEntity {

    // 角色名称
    private String name;

    // 英文名称
    private String enname;

    //是否是可用
    private String useable = "1";

    // 根据用户ID查询角色列表
    private SysUserBean user;

    private List<SysMenuBean> menuList = new ArrayList<>(); // 拥有菜单列表

    public SysRoleBean() {
    }

    public SysRoleBean(String id) {
        super(id);
    }

    public SysRoleBean(SysUserBean user) {
        this();
        this.user = user;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public List<SysMenuBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenuBean> menuList) {
        this.menuList = menuList;
    }

    public List<String> getMenuIdList() {
        List<String> menuIdList = new ArrayList<>();
        for (SysMenuBean menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        menuList = new ArrayList<>();
        for (String menuId : menuIdList) {
            SysMenuBean menu = new SysMenuBean();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = new ArrayList<>();
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");
            setMenuIdList(Arrays.asList(ids));
        }
    }

    /**
     * 获取权限字符串列表
     */
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        for (SysMenuBean menu : menuList) {
            if (menu.getPermission() != null && !"".equals(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }

    public SysUserBean getUser() {
        return user;
    }

    public void setUser(SysUserBean user) {
        this.user = user;
    }

}
