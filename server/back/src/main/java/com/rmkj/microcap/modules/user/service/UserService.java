package com.rmkj.microcap.modules.user.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.service.Ml3AgentRoleService;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.SubMoney;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.entity.UserReport;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class UserService implements IBaseService<UserBean> {
    private final Logger Log = Logger.getLogger(UserService.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IMoneyChangeDao moneyChangeDao;
    @Autowired
    private IMoneyRecordDao moneyRecordDao;
    @Autowired
    private Ml3AgentRoleService ml3AgentRoleService;

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
    public UserBean queryUserTotal(UserBean userBean){
        return userDao.queryUserTotal(userBean);
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
        BigDecimal bigDecimal = userDao.querySingleUserTotalTradeMoney(userBean.getId());
        userReport.setTradeMoney(bigDecimal == null ? new BigDecimal(0.00) : bigDecimal);
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
                List<UserBean> list = userDao.queryList(userBean);
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
    /**
     * 线下提现
     * @param userBean
     * @return
     */
    public ExecuteResult getCash(UserBean userBean) {

        //System.out.println(userBean.getThirdSerialNo());
        UserBean userBean1 = userDao.get(userBean.getId());//提现前的实体类
        int c = userDao.getCash(userBean);
        if(c != 1){
            return new ExecuteResult(StatusCode.OVER_MONEY_GET_CASH);
        }
        UserBean userBean2 = userDao.get(userBean.getId());//提现后的实体类
        //线下提现同时，新增资金变更表和资金记录表
        MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
        String id = Utils.uuid();
        moneyChangeBean.setId(id);
        moneyChangeBean.setDifMoney(userBean.getMoney().multiply(new BigDecimal(-1)));//变更金额
        moneyChangeBean.setType(1);//变更类型：提现
        moneyChangeBean.setBeforeMoney(userBean1.getMoney());//变更前的资金
        moneyChangeBean.setAfterMoney(userBean2.getMoney());//变更后的资金
        moneyChangeBean.setCreateTime(new Date());//创建时间
        moneyChangeBean.setUserId(userBean.getId());//会员用户id;
        moneyChangeBean.setRemark(userBean.getRemarks());
        moneyChangeDao.insert(moneyChangeBean);//新增资金变更表

        MoneyRecordBean moneyRecordBean = new MoneyRecordBean();//资金记录表
        moneyRecordBean.setId(id);
        moneyRecordBean.setSerialNo(id);
        moneyRecordBean.setUserId(userBean.getId());
        moneyRecordBean.setMoney(userBean.getMoney());//金额
        moneyRecordBean.setFee(BigDecimal.valueOf(0));//手续费
        moneyRecordBean.setType(1);//提现
        moneyRecordBean.setStatus(1);//成功
        moneyRecordBean.setCreateTime(new Date());//创建时间
        moneyRecordBean.setCompleteTime(new Date());//完成时间
        moneyRecordBean.setChnName(userBean.getChnName());//姓名
        moneyRecordBean.setRemark(userBean.getRemarks());
        moneyRecordBean.setThirdSerialNo(userBean.getThirdSerialNo());
        moneyRecordDao.insert(moneyRecordBean);//新增资金记录表
        return new ExecuteResult(StatusCode.OK);
    }
    //
    //
    //线下充值
    public ExecuteResult recharge(UserBean userBean) {
        //UserBean userBean1 = userDao.get(userBean.getId());//充值前的实体类
        UserBean userBean1 = userDao.getUserByMobile(userBean.getMobile());
        if(null == userBean1){
            return new ExecuteResult(StatusCode.USER_NOT_EXIST);
        }
        userBean.setId(userBean1.getId());
        int c = userDao.recharge(userBean);
        if(c != 1){
            return new ExecuteResult(StatusCode.OVER_MONEY_GET_CASH);
        }
        UserBean userBean2 = userDao.get(userBean.getId());//充值后的实体类
        //线下充值同时，新增资金变更表和资金记录表
        MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
        String id = Utils.uuid();
        moneyChangeBean.setId(id);
        moneyChangeBean.setDifMoney(userBean.getMoney());//变更金额
        moneyChangeBean.setType(0);//变更类型：充值
        moneyChangeBean.setBeforeMoney(userBean1.getMoney());//变更前的资金
        moneyChangeBean.setAfterMoney(userBean2.getMoney());//变更后的资金
        moneyChangeBean.setCreateTime(new Date());//创建时间
        moneyChangeBean.setUserId(userBean.getId());//会员用户id;
        moneyChangeBean.setRemark(userBean.getRemarks());
        moneyChangeDao.insert(moneyChangeBean);//新增资金变更表

        MoneyRecordBean moneyRecordBean = new MoneyRecordBean();//资金记录表
        moneyRecordBean.setId(id);
        moneyRecordBean.setSerialNo(id);
        moneyRecordBean.setUserId(userBean.getId());
        moneyRecordBean.setMoney(userBean.getMoney());//金额
        moneyRecordBean.setFee(BigDecimal.valueOf(0));//手续费
        moneyRecordBean.setType(0);//提现
        moneyRecordBean.setStatus(1);//成功
        moneyRecordBean.setChannel("线下充值");//渠道
        moneyRecordBean.setCreateTime(new Date());//创建时间
        moneyRecordBean.setCompleteTime(new Date());//完成时间
        moneyRecordBean.setChnName(userBean2.getChnName());//姓名
        moneyRecordBean.setRemark(userBean.getRemarks());
        moneyRecordBean.setThirdSerialNo(userBean.getThirdSerialNo());
        moneyRecordDao.insert(moneyRecordBean);//新增资金记录表
        return new ExecuteResult(StatusCode.OK);
    }
    //提现审核失败后，资金再返给用户
    public ExecuteResult moneyBack(UserBean us){
        int c = userDao.moneyBack(us);
        if(c != 1){
            return new ExecuteResult(StatusCode.OVER_MONEY_GET_CASH);
        }
        return new ExecuteResult(StatusCode.OK);
    }
    //新增代金券表
    public  ExecuteResult sendCoupon(UserBean us, UserMessageBean userMessageBean){
        String uuid = Utils.uuid();
        us.setCouponMoney(us.getSendMoney());
        userDao.userCouponMoney(us);//修改用户表的代金券余额
        userMessageBean.setId(uuid);
        userMessageBean.setUserId(us.getId());
        userMessageBean.setTitle("代金券");
        userMessageBean.setContent(us.getContent());
        userMessageBean.setReadStatus(0);
        userMessageBean.setType(0);
        userMessageBean.setCreateTime(new Date());
        userDao.insertUserMessage(userMessageBean);//新增用户信息表
        return new ExecuteResult(StatusCode.OK);
    }
    //军团长列表
    public List<UserBean> juntuanList(UserBean entity){
        return userDao.juntuanList(entity);
    }
    //获取当前军团长下的军团的成员列表
    public List<UserBean> getJunTuanList(UserBean entity){
        return userDao.getJunTuanList(entity);
    }
    public void insertMl3Agent(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        ml3AgentBean.setSafePassword(SystemService.entryptPassword(ml3AgentBean.getSafePassword()));
        ml3AgentBean.setAgentInviteCode(Utils.InvitationCodeUtils.generateCode(6));
        ml3AgentBean.setStatus(0);
        Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
        ml3AgentRoleBean.setAgentId(ml3AgentBean.getId());
        ml3AgentRoleBean.setRoleId("4");
        ml3AgentRoleService.insert(ml3AgentRoleBean);
        userDao.insertMl3Agent(ml3AgentBean);
    }
    public void updateJunTuanPwd(UserBean userBean){
        userDao.updateJunTuanPwd(userBean);
    }
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

    /**
     * 风控用户
     * @param userId
     * @return
     */
    public ExecuteResult controlIt(String userId) {
        userDao.delControl(userId);
        return userDao.addControl(userId) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.FAILED);
    }

    /**
     * 取消风控用户
     * @param userId
     * @return
     */
    public ExecuteResult cancelControlIt(String userId) {
        return userDao.delControl(userId) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.FAILED);
    }
    public void updatePwd(SysUserBean entity){
        userDao.updatePwd(entity);
    }

    public BigDecimal queryUserTotalTradeMoney(UserBean entity) {
        BigDecimal tradeMoney = userDao.queryUserTotalTradeMoney(entity);
        return tradeMoney == null ? new BigDecimal(0) : tradeMoney;
    }

    public List<SubGoods> getSubGoodsList(){
        return userDao.getSubGoodsList();
    }

    public String subMoney(){
        String[] userId = {"02de4b2ddf8b47948eca65c23343a9d6","0425d99bc7274e039db60dda7514b491","0a2d37eecf0d48beb9c621c367955b40",
                "0bf2b2c2970a433bb603387916a54892",
                "1a0f85736c0f42879830ad5e32ffa00a",
                "2201243028674ba684206c1f7bf8e4c3",
                "23be647ec42b4a98bcabd7b54e8256d4",
                "2d01e3cc9ce343258c33e745dbcb150d",
                "303383f5c0094cadb8d413a6871731d6",
                "37fa65cc4d634ebdbad7a8b2c2c7313e",
                "38e6298643e4444c89c1572ce9480c85",
                "3ced94479d4b4927bf39b0860ee6116b",
                "431eab7544bb4d788019c0b8b790826b",
                "52cf0e332a414e9aa05f38ce4c63df22",
                "5822e5d64ac046c184d9e4c78ce3a85f",
                "58b6ba74f4944681b3f8c783956d9cdd",
                "70431388b6cd48c19ac0e31f9d894be0",
                "8208f2487b0c4307aecbf699e3211bce",
                "82520e57bab04652be441f959b0f3ded",
                "a1a62cae11ea4f1ba680234db20a4946",
                "cfb16bca081d4b42b00db786ad6967d7",
                "d15b6acfec4e47f783cc474c947b41aa",
                "d90ce39878224201a3df39921e9f83b2",
                "e63744b0033f47708ada98ec940a1880"};
        String[] money = {"6745.00", "105.00",
                "125.00","45.00","100.00","2775.00","2865.00", "175.00",
                "90.00","160.00","70.00","60.00","1245.00","15.00","90.00","305.00","45.00","75.00","30.00","495.00","3915.00","10240.00",
                "780.00", "150.00"};

        List<String> userList= Arrays.asList(userId);
        List<String> moneyList = Arrays.asList(money);
        Log.info("修改参数:" + userList.size() + " " + moneyList.size());

        int c = 0;
        for (int i = 0; i < userList.size(); i++){
            SubMoney subMoney = new SubMoney();
            subMoney.setUserId(userList.get(i));
            subMoney.setMoney(new BigDecimal(moneyList.get(i)));
            if(1 == userDao.subMoney(subMoney)){
                c++;
            }
        }
        if(c == userList.size()){
            return "全部修改成功";
        }
        return "存在未成功的";

    }

    public List<SubGoods> getGoodsList(){
        return userDao.getGoodsList();
    }
}
