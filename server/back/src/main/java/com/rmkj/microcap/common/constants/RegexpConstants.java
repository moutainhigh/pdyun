package com.rmkj.microcap.common.constants;

/**
 * Created by renwenpeng on 2016/6/30.
 * 常用正则表达式
 */
public interface RegexpConstants {
    /**
     * 11位手机号
     */
    String PHONE = "^[0-9]{11}$";
    /**
     * 银行卡账号
     */
    String BANK_ACCOUNT_NUMBER = "^[0-9]+$";
    /**
     * 金额
     */
    String MONEY = "^[0-9]+(\\.[0-9]{1,2})?$";

    /**
     * 英文逗号间隔的多个ID组合字符串
     * ID格式为英文大小写和数字混合
     */
    String IDS_LETTER_NUMBER = "^[0-9a-zA-Z]+(,[0-9a-zA-Z]+)*$";

    /**
     * 英文大小写和数字混合
     */
    String ID_LETTER_NUMBER = "^[0-9a-zA-Z]+$";
}
