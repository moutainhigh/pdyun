package com.rmkj.microcap.common.modules.sys.bean;

/**
 * Created by zhangbowen on 2015/9/8.
 */
public class SysUserRoleBean {
    private String userId;
    private String roleId;

    public SysUserRoleBean(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public SysUserRoleBean() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
