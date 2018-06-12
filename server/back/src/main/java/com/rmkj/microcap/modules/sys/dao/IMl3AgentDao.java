package com.rmkj.microcap.modules.sys.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* Created by Administrator on 2016-11-15.
*/
@DataSource
public interface IMl3AgentDao extends BaseDao<Ml3AgentBean>{
    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return
     */
    public Ml3AgentBean getByLoginName(String loginName);

    /**
     * 更新登录信息，如：登录IP、登录时间
     *
     * @param loginIp   登录ip
     * @param loginDate 登录时间
     * @param id        用户id
     * @return
     */
    public int updateLoginInfo(@Param(value = "lastLoginIp") String loginIp,
                               @Param(value = "lastLoginTime") Date loginDate,
                               @Param(value = "id") String id);


    List<String> findSelectRoleByUserId(String id);

}
