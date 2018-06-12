package com.rmkj.microcap.modules.agentmoneyrecord.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.agentmoneyrecord.dao.IAgentMoneyRecordDao;
import com.rmkj.microcap.modules.agentmoneyrecord.entity.AgentMoneyRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-11-7.
*/
@Service
@Transactional
public class AgentMoneyRecordService implements IBaseService<AgentMoneyRecordBean> {
    @Autowired
    private IAgentMoneyRecordDao agentMoneyRecordDao;

    @Override
    public AgentMoneyRecordBean get(String id){
        return agentMoneyRecordDao.get(id);
    }
    @Override
    public int update(AgentMoneyRecordBean agentMoneyRecordBean){
        agentMoneyRecordBean.preUpdate();
        return agentMoneyRecordDao.update(agentMoneyRecordBean);
    }
    @Override
    public int delete(String id){
        return agentMoneyRecordDao.delete(id);
    }
    @Override
    public int insert(AgentMoneyRecordBean agentMoneyRecordBean){
        agentMoneyRecordBean.preInsert();
        return agentMoneyRecordDao.insert(agentMoneyRecordBean);
    }
    @Override
    public List<AgentMoneyRecordBean> queryList(AgentMoneyRecordBean agentMoneyRecordBean){
        return agentMoneyRecordDao.queryList(agentMoneyRecordBean);
    }

    //提现申请审核通过时
    public ExecuteResult outPastIn(String id, Integer s){
        AgentMoneyRecordBean bean = new AgentMoneyRecordBean();
        bean.setId(id);
        bean.setStatus(s);
        return agentMoneyRecordDao.outPastIn(bean) == 1?new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }
    //经纪人审核没通过时
    public ExecuteResult outPast(String id,Integer s,String failureReason) {
        AgentMoneyRecordBean bean = new AgentMoneyRecordBean();
        bean.setId(id);
        bean.setStatus(s);
        bean.setFailureReason(failureReason);
        System.out.println("失败原因-----"+failureReason);
        return agentMoneyRecordDao.outPast(bean) == 1 ? new ExecuteResult(StatusCode.OK) : new ExecuteResult(StatusCode.HAS_PAST);
    }



}
