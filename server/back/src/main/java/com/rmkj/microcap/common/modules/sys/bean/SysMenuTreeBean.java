package com.rmkj.microcap.common.modules.sys.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbowen on 2015/9/9.
 */
public class SysMenuTreeBean {
    private String id;
    private String text;
    private String parentId;
    private List<SysMenuTreeBean> children = new ArrayList<>();

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SysMenuTreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenuTreeBean> children) {
        this.children = children;
//        this.children = generateChildren(this.id, children);
    }

    public void addChildren(SysMenuTreeBean bean) {
        this.children.add(bean);
    }

//    private List<SysMenuTreeBean> generateChildren(String id, List<SysMenuTreeBean> children) {
//        List<SysMenuTreeBean> resultChild = new ArrayList<SysMenuTreeBean>();
//        for (SysMenuTreeBean sysMenuBean : children) {
//            if (id.equals(sysMenuBean.getParentId())) {
//                sysMenuBean.setChildren(children);
//                resultChild.add(sysMenuBean);
//            }
//        }
//        return resultChild;
//    }
}
