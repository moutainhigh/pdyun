package com.rmkj.microcap.common.modules.pay.mingfu.bean;/**
 * Created by Administrator on 2018/1/15.
 */

import com.rmkj.microcap.common.constants.RegexpConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 支付参数
 * @author k
 * @create -01-15-9:08
 **/

public class MingFuQuickBean {

    private String card_no;

    private String phone_no;

    private String card_name;

    private String msg_no;

    private String id_no;

    @NotNull
    @Pattern(regexp = RegexpConstants.MONEY, message = "金额不合法")
    private String money;

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getMsg_no() {
        return msg_no;
    }

    public void setMsg_no(String msg_no) {
        this.msg_no = msg_no;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
