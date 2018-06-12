package com.rmkj.microcap.modules.moneyrecord.entity;

/**
 * Created by renwp on 2017/1/4.
 */
public class MoneyRecordForOutBean extends MoneyRecordBean {

    private String openBankName;

    private String province;

    private String city;

    private String lianHangNo;

    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLianHangNo() {
        return lianHangNo;
    }

    public void setLianHangNo(String lianHangNo) {
        this.lianHangNo = lianHangNo;
    }
}
