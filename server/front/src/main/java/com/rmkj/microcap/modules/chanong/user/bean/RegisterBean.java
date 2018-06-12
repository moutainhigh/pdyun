package com.rmkj.microcap.modules.chanong.user.bean;

import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.RegexpConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by renwp on 2016/10/18.
 */
public class RegisterBean {
    /**
     * mobile : 手机号
     * password : 密码
     * verifyCode : 验证码
     * refereePhone : 推荐人手机号
     */
    private String mobile;
    private String password;
    private String verifyCode;
    private String refereePhone;
    private String agentInviteCode;
    private String userId;
    private String terminal = Constants.Terminal.TERMINAL_WAP;
    private boolean thirdReg = false;
    //用户注册类型: 1.品道注册 2.商城注册
    private String regType;

    private String chnName;

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }

    public String getAgentInviteCode() {
        return agentInviteCode;
    }

    public void setAgentInviteCode(String agentInviteCode) {
        this.agentInviteCode = agentInviteCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public boolean isThirdReg() {
        return thirdReg;
    }

    public void setThirdReg(boolean thirdReg) {
        this.thirdReg = thirdReg;
    }

}
