package com.rmkj.microcap.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * 数据实体bean
 *
 * @author zhangbowen
 * @since 2015-12-22 10:21:12
 */
@JsonIgnoreProperties(value = {"currentUser","createBy","updateBy"})
public abstract class DataEntity {
    private String id;
    //当前用户
    private SysUserBean currentUser;
    // 备注
    private String remarks;
    // 创建者
    private SysUserBean createBy;
    // 创建日期
    private Date createDate;
    // 更新者
    private SysUserBean updateBy;
    // 更新日期
    private Date updateDate;
    // 删除标记（0：正常；1：删除；）
    private String delFlag;
    //要查询的页数
    private int page;
    //每页显示多少条记录
    private Integer rows;
    //根据页数及每页显示条数计算出的当前便宜量
    private Integer start;

    public DataEntity() {
    }

    public DataEntity(String id) {
        this.id = id;
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert() {
        SysUserBean user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())) {
            this.updateBy = user;
            this.createBy = user;
        }
        this.updateDate = new Date();
        this.createDate = this.updateDate;
        this.id = Utils.uuid();
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate() {
        SysUserBean user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())) {
            this.updateBy = user;
        }
        this.updateDate = new Date();
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public SysUserBean getCreateBy() {
        return createBy;
    }

    public void setCreateBy(SysUserBean createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public SysUserBean getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(SysUserBean updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    @XmlTransient
    public SysUserBean getCurrentUser() {
        if (currentUser == null) {
            currentUser = UserUtils.getUser();
        }
        return currentUser;
    }

    public void setCurrentUser(SysUserBean currentUser) {
        this.currentUser = currentUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
