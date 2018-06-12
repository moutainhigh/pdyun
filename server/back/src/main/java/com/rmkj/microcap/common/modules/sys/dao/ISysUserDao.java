package com.rmkj.microcap.common.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@DataSource
public interface ISysUserDao extends BaseDao<SysUserBean> {
    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return
     */
    public SysUserBean getByLoginName(String loginName);

    /**
     * 更新登录信息，如：登录IP、登录时间
     *
     * @param loginIp   登录ip
     * @param loginDate 登录时间
     * @param id        用户id
     * @return
     */
    public int updateLoginInfo(@Param(value = "loginIp") String loginIp,
                               @Param(value = "loginDate") Date loginDate,
                               @Param(value = "id") String id);


    List<String> findSelectRoleByUserId(String id);

}
