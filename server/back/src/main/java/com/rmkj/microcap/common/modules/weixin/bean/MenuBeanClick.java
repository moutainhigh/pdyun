package com.rmkj.microcap.common.modules.weixin.bean;


/**
 * Created by Administrator on 2016/12/1.
 */
public class MenuBeanClick{
    private String name;
    private MenuBeanClick[] sub_button;//子级菜单

    public MenuBeanClick[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(MenuBeanClick[] sub_button) {
        this.sub_button = sub_button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
