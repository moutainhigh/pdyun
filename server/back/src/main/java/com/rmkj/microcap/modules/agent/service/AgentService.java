package com.rmkj.microcap.modules.agent.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.agent.dao.IAgentDao;
import com.rmkj.microcap.modules.agent.entity.AgentBean;
import com.rmkj.microcap.modules.agentreviewrecord.dao.IAgentReviewRecordDao;
import com.rmkj.microcap.modules.agentreviewrecord.entity.AgentReviewRecordBean;
import com.rmkj.microcap.modules.agentuser.entity.AgentUserBean;
import com.rmkj.microcap.modules.syslog.dao.ISysLogDao;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-11-4.
*/
@Service
@Transactional
public class AgentService implements IBaseService<AgentBean> {
    @Autowired
    private IAgentDao agentDao;
    @Autowired
    private ISysLogDao sysLogDao;
    @Autowired
    private IAgentReviewRecordDao iAgentReviewRecordDao;
    @Override
    public AgentBean get(String id){
        return agentDao.get(id);
    }
    @Override
    public int update(AgentBean agentBean){
        agentBean.preUpdate();
        AgentBean agentBean1 = agentDao.get(agentBean.getId());
        //记录日志
        SysLogBean sysLogBean =  new SysLogBean();
        String uuid = Utils.uuid();
        sysLogBean.setId(uuid);//id
        sysLogBean.setmId(UserUtils.getUser().getId());//管理员用户id
        sysLogBean.setlContent(UserUtils.getUser().getName()+"修改经纪人 "+agentBean1.getRealName()+" 的密码为"+agentBean.getSafePassword()+"!");//日志内容
        sysLogBean.setlCreateTime(new Date());
        sysLogDao.insert(sysLogBean);//新增日志管理表
        return agentDao.update(agentBean);
    }
    @Override
    public int delete(String id){
        return agentDao.delete(id);
    }
    @Override
    public int insert(AgentBean agentBean){
        agentBean.preInsert();
        return agentDao.insert(agentBean);
    }
    @Override
    public List<AgentBean> queryList(AgentBean agentBean){
        return agentDao.queryList(agentBean);
    }

    //经纪人邀请用户列表
    public List<AgentUserBean> getAgentUser(AgentUserBean userBean){
        return agentDao.getAgentUser(userBean);
    }
    //审核记录
    public List<AgentReviewRecordBean> getAgentReview(AgentReviewRecordBean bean){
        return agentDao.getAgentReview(bean);
    }
    //经纪人审核通过时
    public ExecuteResult outPastIn(String id, Integer s){
        AgentBean bean = new AgentBean();
        bean.setId(id);
        bean.setReviewStatus(s);
        //审核通过同时新增经纪人审核记录表
        AgentReviewRecordBean agentReviewRecordBean = new AgentReviewRecordBean();
        agentReviewRecordBean.setId(Utils.uuid());
        agentReviewRecordBean.setAgentId(id);
        agentReviewRecordBean.setSysUserId(UserUtils.getUser().getId());
        agentReviewRecordBean.setResultStatus(1);
        agentReviewRecordBean.setReadStatus(0);
        agentReviewRecordBean.setCreateTime(new Date());
        iAgentReviewRecordDao.insert(agentReviewRecordBean);

        return agentDao.outPastIn(bean) == 1?new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }
    //经纪人审核没通过时
    public ExecuteResult outPast(String id, Integer s, String failureReason) {
        AgentBean bean = new AgentBean();
        bean.setId(id);
        bean.setReviewStatus(s);
        //审核不通过同时新增经纪人审核记录表
        AgentReviewRecordBean agentReviewRecordBean = new AgentReviewRecordBean();
        agentReviewRecordBean.setId(Utils.uuid());
        agentReviewRecordBean.setAgentId(id);
        agentReviewRecordBean.setSysUserId(UserUtils.getUser().getId());
        agentReviewRecordBean.setResultStatus(2);
        agentReviewRecordBean.setReadStatus(0);
        agentReviewRecordBean.setRemark(failureReason);
        agentReviewRecordBean.setCreateTime(new Date());
        iAgentReviewRecordDao.insert(agentReviewRecordBean);
        return agentDao.outPast(bean) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }

    //开启用户
    public void open(AgentBean entity) {
        //开启经纪人同时记录操作日志
        AgentBean agentBean1 = agentDao.get(entity.getId());
        SysLogBean sysLogBean = new SysLogBean();
        sysLogBean.setId(Utils.uuid());
        sysLogBean.setmId(UserUtils.getUser().getId());//管理员用户id
        sysLogBean.setlContent(UserUtils.getUser().getName()+"修改经纪人 "+agentBean1.getRealName()+" 的状态为开启！");//日志内容
        sysLogBean.setlCreateTime(new Date());
        sysLogDao.insert(sysLogBean);
        agentDao.open(entity);
    }
    //停用用户
    public void close(AgentBean entity) {
        AgentBean agentBean1 = agentDao.get(entity.getId());
        SysLogBean sysLogBean = new SysLogBean();
        sysLogBean.setId(Utils.uuid());
        sysLogBean.setmId(UserUtils.getUser().getId());//管理员用户id
        sysLogBean.setlContent(UserUtils.getUser().getName()+"修改经纪人 "+agentBean1.getRealName()+" 的状态为停用！");//日志内容
        sysLogBean.setlCreateTime(new Date());
        sysLogDao.insert(sysLogBean);
        agentDao.close(entity);
    }
}
