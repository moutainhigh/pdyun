package com.rmkj.microcap.modules.Ml3MemberUnitisMoneyChange.entity;/**
 * Created by Administrator on 2017/4/25.
 */

import com.rmkj.microcap.common.bean.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员单位保证金浮动记录表
 * @author k
 * @create -04-25-16:55
 **/

public class Ml3MemberUnitsMoneyChange extends DataEntity{
    //会员单位id
    private String unitsId;
    //会员单位名称
    private String unitsName;
    //盈亏金额
    private BigDecimal difMoney;
    //计算前金额
    private BigDecimal beforeMoney;
    //计算后金额
    private BigDecimal afterMoney;
    //计算开始时间
    private Date createTime;
    //计算结束时间
    private Date endTime;
    //备注
    private String remark;
    //类型： 1浮动金额 2追加金额 3提现
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(String unitsId) {
        this.unitsId = unitsId;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }

    public BigDecimal getDifMoney() {
        return difMoney;
    }

    public void setDifMoney(BigDecimal difMoney) {
        this.difMoney = difMoney;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
