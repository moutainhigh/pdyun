package com.rmkj.microcap.modules.Ml3Agent.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.dao.IMl3AgentDao;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.dao.IMl3OperateCenterDao;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.dao.IMl3AgentRoleDao;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.entity.UserShiftMl3Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.awt.image.BufferedImage;
import java.util.*;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3AgentService implements IBaseService<Ml3AgentBean> {
    @Autowired
    private IMl3AgentDao ml3AgentDao;

    @Autowired
    private IMl3OperateCenterDao ml3OperateCenterDao;

    @Autowired
    private IMl3AgentRoleDao ml3AgentRoleDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public Ml3AgentBean get(String id){
        return ml3AgentDao.get(id);
    }
    @Override
    public int update(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preUpdate();
        return ml3AgentDao.update(ml3AgentBean);
    }
    public int updateUnits(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preUpdate();
        ml3AgentBean.setRoleType(1);
        return ml3AgentDao.update(ml3AgentBean);
    }
    public int updateOc(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preUpdate();
        ml3AgentBean.setRoleType(2);
        return ml3AgentDao.update(ml3AgentBean);
    }
    @Override
    public int delete(String id){
        return ml3AgentDao.delete(id);
    }
    @Override
    public int insert(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        return ml3AgentDao.insert(ml3AgentBean);
    }
    @Override
    public List<Ml3AgentBean> queryList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.queryList(ml3AgentBean);
    }
    //启用
    public void open(Ml3AgentBean entity) {
        ml3AgentDao.open(entity);
    }
    //停用
    public void close(Ml3AgentBean entity) {
        entity.setStatus(1);
        ml3AgentDao.close(entity);
    }
    public List<Ml3AgentBean> unitsList(Ml3AgentBean ml3AgentBean){return ml3AgentDao.unitsList(ml3AgentBean);}//会员单位用户列表
    public List<Ml3AgentBean> centerList(Ml3AgentBean ml3AgentBean){return ml3AgentDao.centerList(ml3AgentBean);}//市场管理部用户列表
    public int insertUnits(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        ml3AgentBean.setId(Utils.uuid());
        ml3AgentBean.setRoleType(1);
        return ml3AgentDao.insert(ml3AgentBean);
    }
    public int insertOc(Ml3AgentBean ml3AgentBean){
        ml3AgentBean.preInsert();
        ml3AgentBean.setId(Utils.uuid());
        ml3AgentBean.setRoleType(2);
        return ml3AgentDao.insert(ml3AgentBean);
    }
    public void updPwd(Ml3AgentBean bean){
        ml3AgentDao.updPwd(bean);
    }
    public ArrayList<String> getAgentMobile(){
        return ml3AgentDao.getAgentMobile();
    }
    public ArrayList<String> getAgentAccount(){
        return ml3AgentDao.getAgentAccount();
    }
    public ArrayList<String> getAllAgent(){
        return ml3AgentDao.getAllAgent();
    }
    public ArrayList<String> getAllAgentAccount(){
        return ml3AgentDao.getAllAgentAccount();
    }
    public List<UserBean> dailishangList(UserBean userBean){
        return ml3AgentDao.dailishangList(userBean);
    }
    public Integer queryTotalUserByUnits(UserBean userBean){
        return ml3AgentDao.queryTotalUserByUnits(userBean);
    }
    public void updMl3AgentPwd(Ml3AgentBean bean){
        ml3AgentDao.updMl3AgentPwd(bean);
    }
    public Ml3AgentBean getMl3AgentByUserId(String userid){
        return ml3AgentDao.getMl3AgentByUserId(userid);
    }
    public List<Ml3AgentBean> innerUnitsList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.innerUnitsList(ml3AgentBean);
    }
    public void close1(Ml3AgentBean entity){
        ml3AgentDao.close1(entity);
    }

    /**
     * 查询市场管理部
     * @param ml3AgentBean
     * @return
     */
    public List<Ml3AgentBean> queryOperationCenterUserList(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.queryOperationCenterUserList(ml3AgentBean);
    }

    public ExecuteResult saveOperateCenterUser(Ml3AgentBean ml3AgentBean){
        //验证市场管理部是否存在
        Ml3OperateCenterBean operateCenterBean = ml3OperateCenterDao.get(ml3AgentBean.getCenterId());
        if(null == operateCenterBean){
            return new ExecuteResult(StatusCode.FAILED);
        }
        //验证市场管理部用户账户
        Ml3AgentBean agentBean = new Ml3AgentBean();
        agentBean.setAccount(ml3AgentBean.getAccount());
        agentBean.setMobile(ml3AgentBean.getMobile());
        List<Ml3AgentBean> operateCenterCheck = ml3AgentDao.checkOperationCenterUserList(agentBean);
        if(null != operateCenterCheck && !operateCenterCheck.isEmpty()){
            return new ExecuteResult(StatusCode.OPERATECENTERUSER_EXIST);
        }
        ml3AgentBean.setSafePassword(Utils.entryptPassword(ml3AgentBean.getSafePassword()));
        ml3AgentBean.setCreateTime(new Date());
        ml3AgentBean.setStatus(0);
        ml3AgentBean.preInsert();
        ml3AgentDao.insert(ml3AgentBean);

        Ml3AgentRoleBean ml3AgentRoleBean = new Ml3AgentRoleBean();
        ml3AgentRoleBean.setAgentId(ml3AgentBean.getId());
        ml3AgentRoleBean.setRoleId(Constants.ML3_AGENT_ROLE.OCID);
        if(1 != ml3AgentRoleDao.insert(ml3AgentRoleBean)){
            return new ExecuteResult(StatusCode.FAILED);
        }
        return new ExecuteResult(StatusCode.OK);
    }

    public void updateOperateCenterUser(Ml3AgentBean ml3AgentBean){
        ml3AgentDao.updateOperateCenterUser(ml3AgentBean);
    }

    /**
     * 查询全部代理商
     * @param model
     */
    public void findMl3AgentUserList(Model model){
        model.addAttribute("ml3AgentUser", ml3AgentDao.findMl3AgentUserList());
    }

    /**
     * 平移客户到代理下
     * @param entity
     * @return
     */
    public ExecuteResult updateUserAgentInviteCode(UserShiftMl3Agent entity){
        Ml3AgentBean ml3AgentBean = ml3AgentDao.get(entity.getAgentId());
        if(ml3AgentBean == null){
            return new ExecuteResult(StatusCode.FAILED, "代理商信息有误");
        }
        if(entity.getIds().indexOf(",") == -1){
            ExecuteResult result = updateUser(entity.getIds(), ml3AgentBean);
            if(result.getCode() != 0){
                return new ExecuteResult(StatusCode.FAILED, "平移客户失败");
            }else{
                return new ExecuteResult(StatusCode.OK, "平移成功");
            }
        }else{
            int success = 0, faild = 0;
            String[] split = entity.getIds().split(",");
            for (int i = 0; i < split.length; i++){
                ExecuteResult result = updateUser(split[i], ml3AgentBean);
                if(result.getCode() != 0){
                    faild++;
                }else{
                    success++;
                }
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("成功平移：").append(success).append("个客户,").append("失败：").append(faild).append("个客户");
            return new ExecuteResult(StatusCode.OK, buffer.toString());
        }
    }

    /**
     * 修改客户邀请码
     * @param userId
     * @param ml3AgentBean
     * @return
     */
    public ExecuteResult updateUser(String userId,Ml3AgentBean ml3AgentBean){
        UserBean userBean = new UserBean();
        userBean.setId(userId);
        userBean.setAgentInviteCode(ml3AgentBean.getAgentInviteCode());
        int result = userDao.updateUserAgentInviteCode(userBean);
        if(result == 1){
            return new ExecuteResult(StatusCode.OK);
        }else {
            return new ExecuteResult(StatusCode.FAILED);
        }
    }

    public Map<String, Object> findMl3AgentByUnitsId(String unitsId){
        Map<String, Object> result = new HashMap<>();
        result.put("agentList", ml3AgentDao.findMl3AgentUserListByUnitsId(unitsId));
        return result;
    }

    public ExecuteResult updateReturnPercent(Ml3AgentBean ml3AgentBean){
        if(ml3AgentDao.updateReturnPercent(ml3AgentBean) == 1){
            return new ExecuteResult(StatusCode.OK, "修改成功");
        }
        return new ExecuteResult(StatusCode.FAILED, "修改失败");
    }

    public int updMl3AgentUserInfo(Ml3AgentBean ml3AgentBean){
        return ml3AgentDao.updMl3AgentUserInfo(ml3AgentBean);
    }

    public Ml3AgentBean getMl3AgentInfo(String id){return ml3AgentDao.getMl3AgentInfo(id);}

    public List<Ml3AgentBean> findAgentList(){
        return ml3AgentDao.findMl3AgentUserList();
    }
}
