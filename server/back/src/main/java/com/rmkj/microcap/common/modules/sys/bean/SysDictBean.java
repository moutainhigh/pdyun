package com.rmkj.microcap.common.modules.sys.bean;


import com.rmkj.microcap.common.bean.DataEntity;

/**
 * Created by zhangbowen on 2015-8-11.
 * 字典
 */
public class SysDictBean extends DataEntity {
    //数据值
    private String value;
    //标签名
    private String label;
    //类型
    private String type;
    //描述
    private String description;
    //排序（升序)
    private int sort;
    //父级编号
    private String parentId;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
