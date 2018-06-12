package com.rmkj.microcap.modules.subRecord.entity;

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;

/**
 * Created by jinghao on 2018/4/26.
 */
public class SubRecordBean extends DataEntity{
    private String id;
    private String subRoleId; //认购者id
    private String subRole;  //认购角色:  1-vip客户， 2-高级客户， 3-合伙人 ,4-玩家
    private String subRoleName; //认购名字
    private BigDecimal subMoney; //认购金额
    private int subTotalNum; // 认购数量
    private String goodsId;  // 认购商品id
    private String goodsName; //认购商品名称
    private String createTime; //认购时间
    private String centerId;
    private String unitsId;
    private String agentId;
    private String userId;//用户id
    private String agentName;
    private String unitsName;
    private String centerName;

    public String getSubRoleName() {
        return subRoleName;
    }

    public void setSubRoleName(String subRoleName) {
        this.subRoleName = subRoleName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubRoleId() {
        return subRoleId;
    }

    public void setSubRoleId(String subRoleId) {
        this.subRoleId = subRoleId;
    }

    public String getSubRole() {
        return subRole;
    }

    public void setSubRole(String subRole) {
        this.subRole = subRole;
    }

    public BigDecimal getSubMoney() {
        return subMoney;
    }

    public void setSubMoney(BigDecimal subMoney) {
        this.subMoney = subMoney;
    }

    public int getSubTotalNum() {
        return subTotalNum;
    }

    public void setSubTotalNum(int subTotalNum) {
        this.subTotalNum = subTotalNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(String unitsId) {
        this.unitsId = unitsId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "SubRecordBean{" +
                "id='" + id + '\'' +
                ", subRoleId='" + subRoleId + '\'' +
                ", subRole='" + subRole + '\'' +
                ", subMoney=" + subMoney +
                ", subTotalNum=" + subTotalNum +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", centerId='" + centerId + '\'' +
                ", unitsId='" + unitsId + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
