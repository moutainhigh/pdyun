package com.rmkj.microcap.common.modules.pay.huanxun.entity;/**
 * Created by Administrator on 2018/5/2.
 */

import com.rmkj.microcap.common.constants.RegexpConstants;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author k
 * @create -05-02-17:49
 **/

public class HuanXunMoneyVO {

    @Pattern(regexp = RegexpConstants.MONEY, message = "金额格式错误")
    private String money;
    //银行卡号
    @NotBlank
    private String bankAccount;
    //银行类型
    @NotBlank
    private String bankType;
    //姓名
    @NotBlank
    private String chnName;
    //身份证号码
    @NotBlank
    private String idCard;
    //手机号
    @NotBlank
    private String mobile;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
