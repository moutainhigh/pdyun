package com.rmkj.microcap.common.modules.sys.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class MlPermissionTreeBean {
    private String id;
    private String text;
    private String parentId;
    private List<MlPermissionTreeBean> children = new ArrayList<>();

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

    public List<MlPermissionTreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<MlPermissionTreeBean> children) {
        this.children = children;
    }

    public void addChildren(MlPermissionTreeBean bean) {
        this.children.add(bean);
    }
}
