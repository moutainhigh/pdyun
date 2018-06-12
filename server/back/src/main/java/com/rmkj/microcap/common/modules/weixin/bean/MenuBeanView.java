package com.rmkj.microcap.common.modules.weixin.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MenuBeanView{
    private List<MenuBeanClick> button;

    public List<MenuBeanClick> getButton() {
        return button;
    }

    public void setButton(List<MenuBeanClick> button) {
        this.button = button;
    }
}
