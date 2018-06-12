package com.rmkj.microcap.modules.Ml3Agent.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeeRecord;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Administrator on 2016-11-17.
*/
@DataSource
public interface IMl3AgentDao extends BaseDao<Ml3AgentBean>{
    void open(Ml3AgentBean entity);//启用

    void close(Ml3AgentBean entity);//停用

    void updatePwd(Ml3AgentBean entity);//修改密码
    List<Ml3AgentBean> userList(Ml3AgentBean bean);//代理列表下的查看客户列表页面
    //查询所有的代理用户的手机号
    ArrayList<String> getAllAgent();
    //查询所有的代理用户的账号
    ArrayList<String> getAllAgentAccount();
    //查询当前登录用户的密码
    ArrayList<String> getPwdById(Ml3AgentBean bean);
    List<Ml3AgentBean> getShouYiList(Ml3AgentBean bean);
    //根据当前id，查询当前账户信息 会员单位的
    List<Ml3MemberUnitsBean> getOneAgent(Ml3MemberUnitsBean bean);
    //根据当前id，查询代理商账户信息
    List<Ml3AgentBean> getAgent(Ml3AgentBean bean);
    void updatePwdByAgent(Ml3AgentBean entity);//修改代理商的密码

    //修改用户表的邀请码
    void updateUserInviteCode(Ml3AgentBean entity);
    //会员单位下的代理商列表、
    List<Ml3AgentBean> getMl3AgentInnerUnits(Ml3AgentBean ml3AgentBean);
    //市场管理部下的代理商列表
    List<Ml3AgentBean> getMl3OperateCenterAgent(Ml3AgentBean ml3AgentBean);
    //获取所有代理商的用户ID
    ArrayList<String> getAllUserId();
    //根据当前id，查询会员单位账户信息
    List<Ml3AgentBean> getAgentInnerUnits(Ml3AgentBean bean);
    //根据当前id，查询市场管理部账户信息
    List<Ml3AgentBean> getAgentOperateCenter(Ml3AgentBean bean);
    //代理商/代理商修改账户信息
    int updateInfo(Ml3AgentBean bean);
    //会员单位账号信息
    List<Ml3MemberUnitsBean> getUnitsInfo(Ml3MemberUnitsBean bean);
    //查询所有会员单位用户
    List<Ml3AgentBean> innerUnitsList(Ml3AgentBean ml3AgentBean);
    ArrayList<String> getAgentMobile();
    ArrayList<String> getAgentAccount();

    /**
     * 代理商提现手续费时，修改代理商返手续费余额
     * @param entity
     * @return
     */
    int updateReturnFeeMoneyAgent(TradeReturnFeeRecord entity);

    int updateReturnServiceFeeMoneyAgent(TradeReturnFeeRecord entity);

    int updateMl3AgentPercent(Ml3AgentBean ml3AgentBean);

    List<Ml3AgentBean> findMl3AgentUserListByUnitsId(String unitsId);

    Ml3AgentBean getMl3AgentInfo(String id);

    int updMl3AgentUserInfo(Ml3AgentBean ml3AgentBean);

    List<Ml3AgentBean> findAgentList();

}
