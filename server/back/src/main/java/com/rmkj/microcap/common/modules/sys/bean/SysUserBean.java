package com.rmkj.microcap.common.modules.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmkj.microcap.common.bean.DataEntity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
public class SysUserBean extends DataEntity {
    // 登录名
    private String loginName;

    // 密码
    private String password;

    // 工号
    private String no;

    // 姓名
    private String name;

    // 邮箱
    private String email;

    // 电话
    private String phone;

    // 手机
    private String mobile;

    //用户头像
    private String photo;

    // 最后登陆IP
    private String loginIp;

    // 最后登陆日期
    private Date loginDate;

    // 是否允许登陆
    private String loginFlag = "1";

    // 新密码
    private String newPassword;

    // 原登录名
    private String oldLoginName;

    // 上次登陆IP
    private String oldLoginIp;

    // 上次登陆日期
    private Date oldLoginDate;

    //关联店铺id
    private String shopId;

    //角色列表数组
    private String[] roleAttr;

    // 拥有角色列表
    private List<SysRoleBean> roleList = new ArrayList<>();

    public SysUserBean() {
        super();
    }

    public SysUserBean(String id) {
        super(id);
    }

    public SysUserBean(String id, String loginName) {
        super(id);
        this.loginName = loginName;
    }

    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }

    public String[] getRoleAttr() {
        return roleAttr;
    }

    public void setRoleAttr(String[] roleAttr) {
        this.roleAttr = roleAttr;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @JsonIgnore
    public List<SysRoleBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoleBean> roleList) {
        this.roleList = roleList;
    }

    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = new ArrayList<>();
        for (SysRoleBean role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        roleList = new ArrayList<>();
        for (String roleId : roleIdList) {
            SysRoleBean role = new SysRoleBean();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        return StringUtils.join(roleList, ",");
    }

    public boolean isAdmin() {
        return isAdmin(this.getId());
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    public String getOldLoginIp() {
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    public Date getOldLoginDate() {
        return oldLoginDate;
    }

    public void setOldLoginDate(Date oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return getId();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
