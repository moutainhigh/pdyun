package com.rmkj.microcap.modules.Ml3MemberUnits.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3MemberUnits.dao.IMl3MemberUnitsDao;
import com.rmkj.microcap.modules.Ml3MemberUnitisMoneyChange.dao.Ml3MemberUnitisMoneyChangeDao;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.dao.Ml3MemberUnitsMoneyRecordDao;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnitisMoneyChange.entity.Ml3MemberUnitsMoneyChange;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.trade.entity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3MemberUnitsService implements IBaseService<Ml3MemberUnitsBean> {
    @Autowired
    private IMl3MemberUnitsDao ml3MemberUnitsDao;
    @Autowired
    private Ml3AgentService ml3AgentService;

    @Autowired
    private Ml3MemberUnitsMoneyRecordDao ml3MemberUnitsMoneyRecordDao;

    @Autowired
    private Ml3MemberUnitisMoneyChangeDao ml3MemberUnitsMoneyChangeDao;

    @Override
    public Ml3MemberUnitsBean get(String id){
        return ml3MemberUnitsDao.get(id);
    }
    @Override
    public int update(Ml3MemberUnitsBean ml3MemberUnitsBean){
        ml3MemberUnitsBean.preUpdate();
        return ml3MemberUnitsDao.update(ml3MemberUnitsBean);
    }
    @Override
    public int delete(String id){
        return ml3MemberUnitsDao.delete(id);
    }
    @Override
    public int insert(Ml3MemberUnitsBean ml3MemberUnitsBean){
        ml3MemberUnitsBean.preInsert();
        return ml3MemberUnitsDao.insert(ml3MemberUnitsBean);
    }
    @Override
    public List<Ml3MemberUnitsBean> queryList(Ml3MemberUnitsBean ml3MemberUnitsBean){
        return ml3MemberUnitsDao.queryList(ml3MemberUnitsBean);
    }
    //启用
    public void open(Ml3MemberUnitsBean entity) {
        ml3MemberUnitsDao.open(entity);
    }
    //停用
    public void close(Ml3MemberUnitsBean entity) {
        ml3MemberUnitsDao.close(entity);
    }
    public List<Ml3MemberUnitsBean> muList(String centerId){
        return ml3MemberUnitsDao.muList(centerId);
    }
    public List<Ml3MemberUnitsBean> memberUnitsList(Ml3MemberUnitsBean entity){
        return ml3MemberUnitsDao.memberUnitsList(entity);
    }

    /**
     * 根据当前登录市场管理部查询下面的会员单位
     * @return
     */
    public List<Ml3MemberUnitsBean> findMemberUnitsByAgentId(){
        return ml3MemberUnitsDao.findMemberUnitsByAgentId(UserUtils.getUser().getId());
    }

    /**
     * 查询当前会员单位信息
     * @param model
     */
    public void getMemberUnits(Model model){
        Ml3AgentBean user = UserUtils.getUser();
        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsDao.get(user.getUnitsId());
        model.addAttribute("memberUnits", ml3MemberUnitsBean);
    }

    /**
     * 会员单位出金记录
     * @param entity
     * @return
     */
    public ExecuteResult unitsMoneyWithdraw(Ml3MemberUnitsMoneyRecord entity){
        //验证提现时间
        if(!validateWithdrawTime()){
            return new ExecuteResult(StatusCode.FAILED, "请在工作日下午六点之前提现");
        }

        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsDao.get(UserUtils.getUser().getUnitsId());

        Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord = new Ml3MemberUnitsMoneyRecord();
        ml3MemberUnitsMoneyRecord.setUnitsId(ml3MemberUnitsBean.getId());
        ml3MemberUnitsMoneyRecord.setUnitsName(ml3MemberUnitsBean.getName());
        ml3MemberUnitsMoneyRecord.setType(Constants.MONEY_RECORD_TYPE.WITHDRAW);
        ml3MemberUnitsMoneyRecord.setMoney(entity.getMoney());
        ml3MemberUnitsMoneyRecord.setFee(entity.getMoney().multiply(new BigDecimal(0.01)));
        ml3MemberUnitsMoneyRecord.setBeforeMoney(ml3MemberUnitsBean.getMoney());//未体现前余额
        ml3MemberUnitsMoneyRecord.setAfterMoney(ml3MemberUnitsBean.getMoney().subtract(entity.getMoney().add(ml3MemberUnitsMoneyRecord.getFee())));//出金金额 + 手续费
        ml3MemberUnitsMoneyRecord.setBankName(ml3MemberUnitsBean.getBankName());
        ml3MemberUnitsMoneyRecord.setOpenBankName(entity.getOpenBankName());
        ml3MemberUnitsMoneyRecord.setBankAccount(entity.getBankAccount());
        ml3MemberUnitsMoneyRecord.setRealName(ml3MemberUnitsBean.getRealName());
        ml3MemberUnitsMoneyRecord.setCreateTime(new Date());
        ml3MemberUnitsMoneyRecord.preInsert();
        //添加出金记录
        Ml3MemberUnitsMoneyChange ml3MemberUnitsMoneyChange = new Ml3MemberUnitsMoneyChange();
        ml3MemberUnitsMoneyChange.setUnitsId(ml3MemberUnitsBean.getId());
        ml3MemberUnitsMoneyChange.setUnitsName(ml3MemberUnitsBean.getName());
        ml3MemberUnitsMoneyChange.setType(3);
        ml3MemberUnitsMoneyChange.setDifMoney(entity.getMoney().add(ml3MemberUnitsMoneyRecord.getFee()));
        ml3MemberUnitsMoneyChange.setBeforeMoney(ml3MemberUnitsMoneyRecord.getBeforeMoney());
        ml3MemberUnitsMoneyChange.setAfterMoney(ml3MemberUnitsMoneyRecord.getAfterMoney());
        ml3MemberUnitsMoneyChange.setCreateTime(new Date());
        ml3MemberUnitsMoneyChange.preInsert();
        //验证保证金保留的余额
        if(ml3MemberUnitsMoneyChange.getAfterMoney().compareTo(new BigDecimal(0)) == -1){
            return new ExecuteResult(StatusCode.FAILED, "保证金余额最低保留0元！");
        }
        /*if(ml3MemberUnitsMoneyChange.getAfterMoney().compareTo(ml3MemberUnitsBean.getMoneyLimit()) == -1){
            return new ExecuteResult(StatusCode.FAILED, Utils.formatStr("保证金余额最低保留{0}元！", ml3MemberUnitsBean.getMoneyLimit().toString()));
        }*/

        if(ml3MemberUnitsMoneyRecordDao.insert(ml3MemberUnitsMoneyRecord) != 1 || ml3MemberUnitsMoneyChangeDao.insert(ml3MemberUnitsMoneyChange) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ExecuteResult(StatusCode.FAILED, "申请出金失败！");
        }

        ml3MemberUnitsBean.setMoneyAddFee(entity.getMoney().add(ml3MemberUnitsMoneyRecord.getFee()));

        if(ml3MemberUnitsDao.updateMemberUnitsMoney(ml3MemberUnitsBean) != 1){
            return new ExecuteResult(StatusCode.FAILED, "申请出金失败！");
        }

        return new ExecuteResult(StatusCode.OK, "申请出金成功！");
    }

    /**
     * 周一至周五下午四点之前提现
     * @return
     */
    public boolean validateWithdrawTime(){
        Date date = new Date();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(date);
        if(nowCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || nowCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return false;
        }
        if(nowCalendar.compareTo(getStartWithdrawTime(date)) == -1 || nowCalendar.compareTo(getEndWithdrawTime(date)) == 1){
            return false;
        }
        return true;
    }

    /**
     * TODO 获取提现开始时间
     * @param date
     * @return
     */
    public Calendar getStartWithdrawTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,30);
        return calendar;
    }

    /**
     * TODO 提现结束时间
     * @param date
     * @return
     */
    public Calendar getEndWithdrawTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,00);
        return calendar;
    }
}
