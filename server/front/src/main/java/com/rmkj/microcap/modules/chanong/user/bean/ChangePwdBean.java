package com.rmkj.microcap.modules.chanong.user.bean;

/**
 * Created by jinghao on 2018/5/9.
 */
public class ChangePwdBean {
    private String userId;
    private String oldPwd;
    private String newPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
