package com.rmkj.microcap.common.modules.sys.bean;



import com.rmkj.microcap.common.bean.DataEntity;

import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
public class SysMenuBean extends DataEntity {
    //父id
    private String parentId;
    private String parentIds;
    // 名称
    private String name;

    // 链接
    private String href;

    // 图标
    private String icon;

    // 排序
    private Integer sort;

    // 是否在菜单中显示（1：显示；0：不显示）
    private String isShow;

    // 权限标识
    private String permission;

    private String userId;
    private List<SysMenuBean> children;
    private boolean leaf;
    //节点状态，开启或关闭
    private String state = "closed";

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public SysMenuBean() {
        super();
    }

    public SysMenuBean(String id) {
        super(id);
    }

    public List<SysMenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuBean> children) {
        this.children = children;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
