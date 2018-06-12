package com.rmkj.microcap.modules.user.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.user.entity.UserBean;

import java.math.BigDecimal;
import java.util.List;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface IUserDao extends BaseDao<UserBean>{
    //提现次数
    long selectTiXianCount(String id);

    //充值提现记录
    List<MoneyRecordBean> queryMoneyRecordList(MoneyRecordBean bean);

    //线下提现
    int getCash(UserBean us);
    //提现审核失败时，资金再回到用户表
    int moneyBack(UserBean us);
    //新增代金券表同时，修改用户表的代金券余额
    int userCouponMoney(UserBean us);
//    int insertUserMessage(UserMessageBean bean);
    BigDecimal winMoney(String id); //盈利金额
    BigDecimal loseMoney(String id);//亏损金额
    List<UserBean> userlist(UserBean bean);
    //军团长列表
    List<UserBean> juntuanList(UserBean entity);
    //当前军团长下的军团的成员列表
    List<UserBean> getJunTuanList(UserBean entity);
    //军团营销管理下的炮兵团列表
    List<UserBean> paobingList(UserBean userBean);
    //军团营销管理下的骑兵团列表
    List<UserBean> qibingList(UserBean userBean);
    //军团营销管理下的步兵团列表
    List<UserBean> bubingList(UserBean userBean);
    List<UserBean> queryCustomerList(UserBean bean);
    UserBean queryCustomerTotal(UserBean bean);
    //会员单位下的客户列表：通过邀请码关联
    List<UserBean> userListInnerUnits(UserBean bean);
    //市场管理部下的客户列表
    List<UserBean>  userListOperateCenter(UserBean bean);
    UserBean queryInnerUnitsTotal(UserBean bean);
    UserBean queryOperateCenterTotal(UserBean bean);
    //会员单位下的军团长列表
    List<UserBean> juntuanInnerUnitsList(UserBean bean);
    //新增军团长为代理商
    void insertMl3Agent(Ml3AgentBean ml3AgentBean);
    //会员单位下的炮兵团列表
    List<UserBean> paobingInnerUnitsList(UserBean userBean);
    //会员单位下的骑兵团列表
    List<UserBean> qibingInnerUnitsList(UserBean userBean);
    //会员单位下的步兵团列表
    List<UserBean> bubingInnerUnitsList(UserBean userBean);

    int updateAgentInviteCode(UserBean userBean);

    BigDecimal querySingleUserTotalTradeMoney(String id);

    BigDecimal queryUserTotalTradeMoneyAgent(UserBean userBean);
    BigDecimal queryUserTotalTradeMoneyUnits(UserBean userBean);
    BigDecimal queryUserTotalTradeMoneyCenter(UserBean userBean);
}
