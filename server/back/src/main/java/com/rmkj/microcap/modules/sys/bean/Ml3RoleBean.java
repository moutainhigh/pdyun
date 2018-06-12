package com.rmkj.microcap.modules.sys.bean;

import com.rmkj.microcap.common.bean.DataEntity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
public class Ml3RoleBean extends DataEntity {
    //角色名称
    private String name;
    //英文名称
    private String enname;
    //是否可用
    private String useable = "1";

	// 根据用户ID查询角色列表
	private Ml3AgentBean user;

	private List<Ml3PermissionBean> menuList = new ArrayList<>(); // 拥有菜单列表

	public Ml3RoleBean() {
	}

	public Ml3RoleBean(String id) {
		super(id);
	}

	public Ml3RoleBean(Ml3AgentBean user) {
		this();
		this.user = user;
	}


	public Ml3AgentBean getUser() {
		return user;
	}

	public void setUser(Ml3AgentBean user) {
		this.user = user;
	}

	public List<Ml3PermissionBean> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Ml3PermissionBean> menuList) {
		this.menuList = menuList;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setEnname(String enname){
		this.enname=enname;
	}
	public String getEnname(){
		return this.enname;
	}
	public void setUseable(String useable){
		this.useable=useable;
	}
	public String getUseable(){
		return this.useable;
	}

	public List<String> getMenuIdList() {
		List<String> menuIdList = new ArrayList<>();
		for (Ml3PermissionBean menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = new ArrayList<>();
		for (String menuId : menuIdList) {
			Ml3PermissionBean menu = new Ml3PermissionBean();
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
		for (Ml3PermissionBean menu : menuList) {
			if (menu.getPermission() != null && !"".equals(menu.getPermission())) {
				permissions.add(menu.getPermission());
			}
		}
		return permissions;
	}


}
