package com.rmkj.microcap.common.modules.weixin.bean;

import java.util.List;

/**
 * Created by zhangbowen on 2016/6/7.
 */
public class CreateMenuBean {
    private List<MenuBean> button;
    public List<MenuBean> getButton() {
        return button;
    }

    public void setButton(List<MenuBean> button) {
        this.button = button;
    }
}
