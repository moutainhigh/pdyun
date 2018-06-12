package com.rmkj.microcap.common.modules.sys.bean;

import com.rmkj.microcap.common.bean.DataEntity;

import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
public class Ml3PermissionBean extends DataEntity {
    //父级编号
    private String parentId;
    //所有父级编号
    private String parentIds;
    //名称
    private String name;
    //排序
    private Integer sort;
    //链接
    private String href;
    //图标
    private String icon;
    //是否在菜单中显示
    private String isShow;
    //权限标识
    private String permission;

	private String userId;
	private List<Ml3PermissionBean> children;
	private boolean leaf;
	//节点状态，开启或关闭
	private String state = "closed";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Ml3PermissionBean> getChildren() {
		return children;
	}

	public void setChildren(List<Ml3PermissionBean> children) {
		this.children = children;
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

	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	public String getParentId(){
		return this.parentId;
	}
	public void setParentIds(String parentIds){
		this.parentIds=parentIds;
	}
	public String getParentIds(){
		return this.parentIds;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setSort(Integer sort){
		this.sort=sort;
	}
	public Integer getSort(){
		return this.sort;
	}
	public void setHref(String href){
		this.href=href;
	}
	public String getHref(){
		return this.href;
	}
	public void setIcon(String icon){
		this.icon=icon;
	}
	public String getIcon(){
		return this.icon;
	}
	public void setIsShow(String isShow){
		this.isShow=isShow;
	}
	public String getIsShow(){
		return this.isShow;
	}
	public void setPermission(String permission){
		this.permission=permission;
	}
	public String getPermission(){
		return this.permission;
	}

}
