
package com.rmkj.microcap.modules.user.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.bean.annotation.Config;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.common.modules.sys.dao.IMl3AgentRoleDao;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentService;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.dao.IMl3AgentDao;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.entity.UserReport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.SuppressForbidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.AuthContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class UserService implements IBaseService<UserBean> {
    @Autowired
    private IUserDao userDao;
//    @Autowired
//    private IMoneyChangeDao moneyChangeDao;
//    @Autowired
//    private IMoneyRecordDao moneyRecordDao;
    @Autowired
    private IMl3AgentDao ml3AgentDao;
    @Autowired
    private IMl3AgentRoleDao ml3AgentRoleDao;

    @Override
    public UserBean get(String id){
        return userDao.get(id);
    }
    @Override
    public int update(UserBean userBean){
        userBean.preUpdate();
        return userDao.update(userBean);
    }
    @Override
    public int delete(String id){
        return userDao.delete(id);
    }
    @Override
    public int insert(UserBean userBean){
        userBean.preInsert();
        return userDao.insert(userBean);
    }
    @Override
    public List<UserBean> queryList(UserBean userBean){
        return userDao.queryList(userBean);
    }

    //充值提现记录
    public List<MoneyRecordBean> queryMoneyRecordList(MoneyRecordBean bean){
        return userDao.queryMoneyRecordList(bean);
    }

    //查询统计 客户相关信息
    public UserReport info(UserBean userBean) {
        UserReport userReport = new UserReport();
        UserBean bean = userDao.get(userBean.getId());
        if(bean != null) {
            userReport.setUserBean(bean);
        }
        userReport.setTiXianCount(userDao.selectTiXianCount(userBean.getId()));
        userReport.setWinMoney(userDao.winMoney(userBean.getId()));
        userReport.setLoseMoney(userDao.loseMoney(userBean.getId()));
        userReport.setTradeMoney(userDao.querySingleUserTotalTradeMoney(userBean.getId()));
        // TODO 盈亏结算
        return userReport;
    }


    //导出到Excel
    public HSSFWorkbook exportExcel(UserBean userBean) {
        userBean.setPage(1);
        userBean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"微信openid","中文姓名","手机号","交易密码","资金","累计充值资金","用户状态","注册时间","最后一次登录时间","最后一次登录地址"};
        String[] headerColumn = new String[]{"openId","chnName","mobile","tradePassword","money","rechargeMoney","status","registerTime","lastLoginTime","lastLoginIp"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("status", (Object obj) -> {
            UserBean u = (UserBean)obj;
            return u.getStatus() == 0 ? "正常" : "停用";
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<UserBean>(){

            private int page = 1;

            @Override
            public List<UserBean> find() {
                List<UserBean> list = userDao.userListInnerUnits(userBean);
                userBean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long)Math.ceil(MybatisPagerInterceptor.getTotal()/userBean.getRows().doubleValue());
            }

        }, UserBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }
    public List<UserBean> userlist(UserBean bean){
        return userDao.userlist(bean);
    }
    //军团长列表
    public List<UserBean> juntuanList(UserBean entity){
        return userDao.juntuanList(entity);
    }
    //当前军团长下的军团的成员列表
    public List<UserBean> getJunTuanList(UserBean entity){
        return userDao.getJunTuanList(entity);
    }
    //炮兵团列表
    public List<UserBean> paobingList(UserBean userBean){
        return userDao.paobingList(userBean);
    }
    //军团营销管理下的骑兵团列表
    public List<UserBean> qibingList(UserBean userBean){
        return userDao.qibingList(userBean);
    }
    //军团营销管理下的步兵团列表
    public List<UserBean> bubingList(UserBean userBean){
        return userDao.bubingList(userBean);
    }

    public List<UserBean> queryCustomerList(UserBean bean){
        bean.setId(UserUtils.getUser().getId());
        return userDao.queryCustomerList(bean);
    }
    public UserBean queryCustomerTotal(UserBean bean){
        bean.setId(UserUtils.getUser().getId());
        return userDao.queryCustomerTotal(bean);
    }
    public List<UserBean> userListInnerUnits(UserBean bean){
        return userDao.userListInnerUnits(bean);
    }
    public List<UserBean> userListOperateCenter(UserBean bean){
        return userDao.userListOperateCenter(bean);
    }
    public UserBean queryInnerUnitsTotal(UserBean bean){
        return userDao.queryInnerUnitsTotal(bean);
    }
    public UserBean queryOperateCenterTotal(UserBean bean){
        return userDao.queryOperateCenterTotal(bean);
    }
    public List<UserBean> juntuanInnerUnitsList(UserBean bean){
        return userDao.juntuanInnerUnitsList(bean);
    }
    public void insertMl3Agent(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        Ml3AgentBean ml3AgentBean1 = ml3AgentDao.get(UserUtils.getUser().getId());
        ml3AgentBean.setId(Utils.uuid());
        ml3AgentBean.setCenterId(ml3AgentBean1.getCenterId());
        ml3AgentBean.setUnitsId(ml3AgentBean1.getUnitsId());
        ml3AgentBean.setSafePassword(SystemService.entryptPassword(ml3AgentBean.getSafePassword()));
        ml3AgentBean.setAgentInviteCode(Utils.InvitationCodeUtils.generateCode(6));
        //代理商默认停用
        ml3AgentBean.setStatus(Integer.valueOf(Constants.AGENT_STATUS.DISABLE));
        ml3AgentBean.setRoleType(0);
        ml3AgentBean.setSubTimes(8);
        ml3AgentBean.setCreateTime(new Date());
        Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
        ml3AgentRoleBean.setAgentId(ml3AgentBean.getId());
        ml3AgentRoleBean.setRoleId("4");
        ml3AgentRoleDao.insert(ml3AgentRoleBean);
        userDao.insertMl3Agent(ml3AgentBean);
//
//        UserBean userBean = new UserBean();
//        userBean.setId(ml3AgentBean.getUserId());
//        userBean.setAgentInviteCode(ml3AgentBean.getAgentInviteCode());
//        userDao.updateAgentInviteCode(userBean);
    }
    //会员单位下的炮兵团列表
    public List<UserBean> paobingInnerUnitsList(UserBean userBean){
        return userDao.paobingInnerUnitsList(userBean);
    }
    //会员单位下的骑兵团列表
    public List<UserBean> qibingInnerUnitsList(UserBean userBean){
        return userDao.qibingInnerUnitsList(userBean);
    }
    //会员单位下的步兵团列表
    public List<UserBean> bubingInnerUnitsList(UserBean userBean){
        return userDao.bubingInnerUnitsList(userBean);
    }
}
