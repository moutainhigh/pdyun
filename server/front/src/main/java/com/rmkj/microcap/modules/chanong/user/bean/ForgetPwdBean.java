package com.rmkj.microcap.modules.chanong.user.bean;

/**
 * Created by jinghao on 2018/4/25.
 */
public class ForgetPwdBean {
    private String mobile;
    private String verifyCode;
    private String newPwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
