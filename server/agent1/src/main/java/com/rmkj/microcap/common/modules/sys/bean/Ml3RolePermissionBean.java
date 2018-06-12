package com.rmkj.microcap.common.modules.sys.bean;

import com.rmkj.microcap.common.bean.DataEntity;

/**
* Created by Administrator on 2016-11-15.
*/
public class Ml3RolePermissionBean extends DataEntity {
    //角色编号
    private String roleId;
    //权限编号
    private String permissionId;

	public void setRoleId(String roleId){
		this.roleId=roleId;
	}
	public String getRoleId(){
		return this.roleId;
	}
	public void setPermissionId(String permissionId){
		this.permissionId=permissionId;
	}
	public String getPermissionId(){
		return this.permissionId;
	}

}
