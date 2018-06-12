package com.rmkj.microcap.modules.user.entity;

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renwp on 2016/10/12.
 */
public class User extends DataEntity {

    private String openId;
    private String userHeader;
    private String chnName;
    private String mobile;
    private String tradePassword;

    private BigDecimal money;
    private BigDecimal rechargeMoney;
    private String tradeCount;
    private BigDecimal couponMoney;
    private BigDecimal outMoney;

    private String agentInviteCode;
    private Integer status;
    private Date registerTime;
    private Date lastLoginTime;
    private String lastLoginIp;

    private String parent1Id;
    private String parent2Id;
    private String parent3Id;
    private BigDecimal returnMoney;
    private BigDecimal returnMoneyTotal;

    private String idCard;
    private String corpsType;

    private String ticket;

    private String token;

    private String twoLevelDomain;

    private String groupId;

    private String autoPoint;

    private BigDecimal return2Ratio;

    private BigDecimal return3Ratio;

    private String subFlag;

    private BigDecimal integralNeg;
    private BigDecimal integralPos;
    private BigDecimal integralUnpos;
    private String agentId;
    private String unitsId;
    private String centerId;
    //用户注册类型: 1.品道注册 2.商城注册
    private String regType;

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(String unitsId) {
        this.unitsId = unitsId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public BigDecimal getIntegralNeg() {
        return integralNeg;
    }

    public void setIntegralNeg(BigDecimal integralNeg) {
        this.integralNeg = integralNeg;
    }

    public BigDecimal getIntegralPos() {
        return integralPos;
    }

    public void setIntegralPos(BigDecimal integralPos) {
        this.integralPos = integralPos;
    }

    public BigDecimal getIntegralUnpos() {
        return integralUnpos;
    }

    public void setIntegralUnpos(BigDecimal integralUnpos) {
        this.integralUnpos = integralUnpos;
    }

    public String getSubFlag() {
        return subFlag;
    }

    public void setSubFlag(String subFlag) {
        this.subFlag = subFlag;
    }

    public BigDecimal getReturn2Ratio() {
        return return2Ratio;
    }

    public void setReturn2Ratio(BigDecimal return2Ratio) {
        this.return2Ratio = return2Ratio;
    }

    public BigDecimal getReturn3Ratio() {
        return return3Ratio;
    }

    public void setReturn3Ratio(BigDecimal return3Ratio) {
        this.return3Ratio = return3Ratio;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAutoPoint() {
        return autoPoint;
    }

    public void setAutoPoint(String autoPoint) {
        this.autoPoint = autoPoint;
    }

    public String getTwoLevelDomain() {
        return twoLevelDomain;
    }

    public void setTwoLevelDomain(String twoLevelDomain) {
        this.twoLevelDomain = twoLevelDomain;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(String tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public String getAgentInviteCode() {
        return agentInviteCode;
    }

    public void setAgentInviteCode(String agentInviteCode) {
        this.agentInviteCode = agentInviteCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getParent1Id() {
        return parent1Id;
    }

    public void setParent1Id(String parent1Id) {
        this.parent1Id = parent1Id;
    }

    public String getParent2Id() {
        return parent2Id;
    }

    public void setParent2Id(String parent2Id) {
        this.parent2Id = parent2Id;
    }

    public String getParent3Id() {
        return parent3Id;
    }

    public void setParent3Id(String parent3Id) {
        this.parent3Id = parent3Id;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public BigDecimal getReturnMoneyTotal() {
        return returnMoneyTotal;
    }

    public void setReturnMoneyTotal(BigDecimal returnMoneyTotal) {
        this.returnMoneyTotal = returnMoneyTotal;
    }

    public String getCorpsType() {
        return corpsType;
    }

    public void setCorpsType(String corpsType) {
        this.corpsType = corpsType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
