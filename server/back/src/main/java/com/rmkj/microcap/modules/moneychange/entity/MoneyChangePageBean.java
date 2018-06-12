package com.rmkj.microcap.modules.moneychange.entity;/**
 * Created by Administrator on 2017/8/1.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 自定义分页
 * @author k
 * @create -08-01-17:41
 **/

public class MoneyChangePageBean {
    private String id;
    //会员用户ID
    private String userId;
    //变更金额
    private BigDecimal difMoney;
    //类型 0 充值 1 提现 2 建仓 3 平仓
    private Integer type;
    //变更前资金
    private BigDecimal beforeMoney;
    //变更后金额
    private BigDecimal afterMoney;
    //创建时间
    private Date createTime;
    //开始时间
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTimeMin;
    //结束时间
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTimeMax;
    //备注
    private String remark;
    private String uname;
    private String mobile;

    //合约代码
    private String code;
    //合约名称
    private String contractName;
    //会员单位Name
    private String unitsName;
    //代理商Name
    private String agentRealName;
    //用户姓名
    private String chnName;

    //存放多个用户id
    private List<String> userIdList;

    private Integer start;

    private Integer rows;

    private Integer page;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getCreateTimeMin() {
        return createTimeMin;
    }

    public void setCreateTimeMin(String createTimeMin) {
        this.createTimeMin = createTimeMin;
    }

    public String getCreateTimeMax() {
        return createTimeMax;
    }

    public void setCreateTimeMax(String createTimeMax) {
        this.createTimeMax = createTimeMax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getDifMoney() {
        return difMoney;
    }

    public void setDifMoney(BigDecimal difMoney) {
        this.difMoney = difMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(BigDecimal beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }

    public String getAgentRealName() {
        return agentRealName;
    }

    public void setAgentRealName(String agentRealName) {
        this.agentRealName = agentRealName;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
