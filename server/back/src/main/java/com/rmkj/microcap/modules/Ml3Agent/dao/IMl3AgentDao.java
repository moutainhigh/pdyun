package com.rmkj.microcap.modules.Ml3Agent.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.user.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016-11-17.
*/
@DataSource
public interface IMl3AgentDao extends BaseDao<Ml3AgentBean> {
    void open(Ml3AgentBean entity);//启用

    void close(Ml3AgentBean entity);//停用
    List<Ml3AgentBean> unitsList(Ml3AgentBean ml3AgentBean);//会员单位用户列表
    List<Ml3AgentBean> centerList(Ml3AgentBean ml3AgentBean);//市场管理部用户列表
    void updPwd(Ml3AgentBean bean);//修改用户密码
    ArrayList<String> getAgentMobile();
    ArrayList<String> getAgentAccount();
    //查询所有的代理用户的手机号
    ArrayList<String> getAllAgent();
    //查询所有的代理用户的账号
    ArrayList<String> getAllAgentAccount();
    //代理商列表--军团的
    List<UserBean> dailishangList(UserBean userBean);

    //代理商列表--查询会员单位代理下用户总数量
    Integer queryTotalUserByUnits(UserBean userBean);
    void updMl3AgentPwd(Ml3AgentBean bean);//修改代理商密码
    Ml3AgentBean getMl3AgentByUserId(String userid);
    //查询所有会员单位用户
    List<Ml3AgentBean> innerUnitsList(Ml3AgentBean ml3AgentBean);
    void close1(Ml3AgentBean entity);//停用

    /**
     * 查询市场管理部
     * @param ml3AgentBean
     * @return
     */
    List<Ml3AgentBean> queryOperationCenterUserList(Ml3AgentBean ml3AgentBean);

    /**
     * 检查市场管理部用户是否有相同的信息
     * @param ml3AgentBean
     * @return
     */
    List<Ml3AgentBean> checkOperationCenterUserList(Ml3AgentBean ml3AgentBean);

    /**
     * 修改市场管理部信息
     * @param ml3AgentBean
     * @return
     */
    int updateOperateCenterUser(Ml3AgentBean ml3AgentBean);

    /**
     * 查询全部代理商
     * @return
     */
    List<Ml3AgentBean> findMl3AgentUserList();

    List<Ml3AgentBean> findMl3AgentUserListByUnitsId(String unitsId);

    /**
     * 修改返手续费，返服务费比例
     * @param ml3AgentBean
     * @return
     */
    int updateReturnPercent(Ml3AgentBean ml3AgentBean);


    Ml3AgentBean getMl3AgentInfo(String id);

    int updMl3AgentUserInfo(Ml3AgentBean ml3AgentBean);
}
