package com.rmkj.microcap.modules.subRecord.entity;

/**
 * Created by jinghao on 2018/4/27.
 */
public class SubParamBean {
    private int subTimes;
    private String agentName;
    private String unitsName;
    private String centerName;
    private String userName;

    public int getSubTimes() {
        return subTimes;
    }

    public void setSubTimes(int subTimes) {
        this.subTimes = subTimes;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
