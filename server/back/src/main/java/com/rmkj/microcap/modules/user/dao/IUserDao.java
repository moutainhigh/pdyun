package com.rmkj.microcap.modules.user.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.user.entity.SubMoney;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;

import javax.jws.soap.SOAPBinding;
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
    //线下充值
    int recharge(UserBean us);
    //提现审核失败时，资金再回到用户表
    int moneyBack(UserBean us);
    //新增代金券表同时，修改用户表的代金券余额
    int userCouponMoney(UserBean us);
    int insertUserMessage(UserMessageBean bean);
    BigDecimal winMoney(String id); //盈利金额
    BigDecimal loseMoney(String id);//亏损金额
    //军团长列表
    List<UserBean> juntuanList(UserBean entity);
    //当前军团长下的军团的成员列表
    List<UserBean> getJunTuanList(UserBean entity);
    void insertMl3Agent(Ml3AgentBean ml3AgentBean);
    //修改军团长的密码
    void updateJunTuanPwd(UserBean userBean);
    //军团营销管理下的炮兵团列表
    List<UserBean> paobingList(UserBean userBean);
    //军团营销管理下的骑兵团列表
    List<UserBean> qibingList(UserBean userBean);
    //军团营销管理下的步兵团列表
    List<UserBean> bubingList(UserBean userBean);

    int addControl(String userId);

    int delControl(String userId);
    void updatePwd(SysUserBean entity);//修改密码

    User findUserById(String userId);

    BigDecimal queryUserTotalTradeMoney(UserBean entity);

    BigDecimal querySingleUserTotalTradeMoney(String id);

    long juntuanListTotal(UserBean entity);

    List<SubGoods> getSubGoodsList();

    List<SubGoods> getGoodsList();

    /**
     * 平移客户
     * @param user
     * @return
     */
    int updateUserAgentInviteCode(UserBean userBean);

    /**
     * 返还出金失败的佣金
     * @param userBean
     * @return
     */
    int userReturnMoney(UserBean userBean);

    /**
     * 选择性查询用户
     * @param userBean
     * @return
     */
    List<UserBean> findUserSelective(UserBean userBean);

    int subMoney(SubMoney subMoney);

    /**
     * 根据手机号查询客户
     * @param mobile
     * @return
     */
    UserBean queryUserByMobile(String mobile);

    /**
     * 根据客户id查询上级代理  代理商，会员单位，市场管理部
     * @param id 用户id
     * @return
     */
    UserBean queryUserLevelById(String id);

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    UserBean getUserByMobile(String mobile);
}
