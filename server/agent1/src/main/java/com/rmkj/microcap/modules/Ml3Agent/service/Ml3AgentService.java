package com.rmkj.microcap.modules.Ml3Agent.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.dao.IMl3AgentDao;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.parameterSet.dao.ParameterSetDao;
import com.rmkj.microcap.modules.parameterSet.entity.ParameterSet;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3AgentService implements IBaseService<Ml3AgentBean> {
    @Autowired
    private IMl3AgentDao ml3AgentDao;


    @Override
    public Ml3AgentBean get(String id){
        return ml3AgentDao.get(id);
    }
    @Override
    public int update(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preUpdate();
        return ml3AgentDao.update(ml3AgentBean);
    }
    @Override
    public int delete(String id){
        return ml3AgentDao.delete(id);
    }
    @Override
    public int insert(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        String uuid = Utils.uuid();
        ml3AgentBean.setId(uuid);
        ml3AgentBean.setSafePassword(SystemService.entryptPassword(ml3AgentBean.getSafePassword()));
        ml3AgentBean.setAgentInviteCode(Utils.InvitationCodeUtils.generateCode(6));
        ml3AgentBean.setStatus(0);
        return ml3AgentDao.insert(ml3AgentBean);
    }
    @Override
    public List<Ml3AgentBean> queryList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.queryList(ml3AgentBean);
    }
    public int updMl3AgentUserInfo(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.updMl3AgentUserInfo(ml3AgentBean);
    }

    public Ml3AgentBean getMl3AgentInfo(String id){return ml3AgentDao.getMl3AgentInfo(id);}

    //启用
    public void open(Ml3AgentBean entity) {
        ml3AgentDao.open(entity);
    }
    //停用
    public void close(Ml3AgentBean entity) {
        ml3AgentDao.close(entity);
    }
    public void updatePwd(Ml3AgentBean entity){
        ml3AgentDao.updatePwd(entity);
    }
    public List<Ml3AgentBean> userList(Ml3AgentBean bean){
        return ml3AgentDao.userList(bean);
    }
    public ArrayList<String> getAllAgent(){
        return ml3AgentDao.getAllAgent();
    }
    public ArrayList<String> getAllAgentAccount(){
        return ml3AgentDao.getAllAgentAccount();
    }
    public ArrayList<String> getPwdById(Ml3AgentBean bean){return ml3AgentDao.getPwdById(bean);}
    public List<Ml3AgentBean> getShouYiList(Ml3AgentBean bean){return ml3AgentDao.getShouYiList(bean);}
    public List<Ml3MemberUnitsBean> getOneAgent(Ml3MemberUnitsBean bean){return ml3AgentDao.getOneAgent(bean);}
    public List<Ml3AgentBean> getAgent(Ml3AgentBean bean){return ml3AgentDao.getAgent(bean);}
    public void updatePwdByAgent(Ml3AgentBean entity){ml3AgentDao.updatePwdByAgent(entity);}
    public void updateUserInviteCode(Ml3AgentBean entity){ml3AgentDao.updateUserInviteCode(entity);}
    public List<Ml3AgentBean> getMl3AgentInnerUnits(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.getMl3AgentInnerUnits(ml3AgentBean);
    }
    public ArrayList<String> getAllUserId(){
        return ml3AgentDao.getAllUserId();
    }
    public List<Ml3AgentBean> getAgentInnerUnits(Ml3AgentBean bean){
        return ml3AgentDao.getAgentInnerUnits(bean);
    }
    public List<Ml3AgentBean> getAgentOperateCenter(Ml3AgentBean bean){
        return ml3AgentDao.getAgentOperateCenter(bean);
    }
    public int updateInfo(Ml3AgentBean bean){
        return ml3AgentDao.updateInfo(bean);
    }
    //会员单位账号信息
    public List<Ml3MemberUnitsBean> getUnitsInfo(Ml3MemberUnitsBean bean){
        return ml3AgentDao.getUnitsInfo(bean);
    }

    public List<Ml3AgentBean> innerUnitsList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.innerUnitsList(ml3AgentBean);
    }
    public ArrayList<String> getAgentMobile(){
        return ml3AgentDao.getAgentMobile();
    }
    public ArrayList<String> getAgentAccount(){
        return ml3AgentDao.getAgentAccount();
    }
    public int insertMemberUser(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.insert(ml3AgentBean);
    }

    public void updateMl3AgentPercnt(Ml3AgentBean ml3AgentBean){
        ml3AgentDao.updateMl3AgentPercent(ml3AgentBean);
    }



    public List<Ml3AgentBean> findMl3AgentByUnitsId(String unitsId){
        return ml3AgentDao.findMl3AgentUserListByUnitsId(unitsId);
    }
}
