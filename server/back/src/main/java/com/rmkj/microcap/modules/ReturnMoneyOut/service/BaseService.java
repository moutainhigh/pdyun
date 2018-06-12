package com.rmkj.microcap.modules.ReturnMoneyOut.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by renwp on 2017/3/20.
 */
@Service
public class BaseService {

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IMoneyChangeDao moneyChangeDao;

    @Autowired
    private WeiXinService weiXinService;

    public void checkSuccess(MoneyRecordBean moneyRecord){
        checkSuccess(moneyRecord, true);
    }

    public void checkSuccess(MoneyRecordBean moneyRecord, boolean isSendMsg){
        iReturnMoneyOutDao.updateSuccess(moneyRecord.getId());
        if(isSendMsg){
            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                    .concat("元的提现成功处理，手续费").concat(moneyRecord.getFee().toString()).concat("元，流水号：")
                    .concat(moneyRecord.getSerialNo()));
        }
    }

    public void checkFailure(MoneyRecordBean moneyRecord){
        checkSuccess(moneyRecord, true);
    }

    public void checkFailure(MoneyRecordBean moneyRecord, boolean isSendMsg){
        iReturnMoneyOutDao.updateFailure(moneyRecord.getId());
        returnMoney2User(moneyRecord);
        if(isSendMsg){
            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                    .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
        }
    }

    /**
     *
     * @param moneyRecordBean
     */
    public void sendWeiXinMsg(MoneyRecordBean moneyRecordBean, String... params){
        weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, moneyRecordBean.getUserId(), params);
    }

    /**
     * 提现失败，回滚金额
     * @param moneyRecordBean
     */
    public void returnMoney2User(MoneyRecordBean moneyRecordBean){
        try{
            // 失败
            // 处理失败，退钱给用户
            UserBean user = iReturnMoneyOutDao.queryUserMoney(moneyRecordBean.getUserId());
            ReturnMoney2User returnMoney2User = new ReturnMoney2User();
            returnMoney2User.setId(moneyRecordBean.getUserId());
            // 提现金额+手续费
            returnMoney2User.setDifMoney(moneyRecordBean.getMoney().add(moneyRecordBean.getFee()));
            returnMoney2User.setMoney(user.getReturnMoney());
            int i = iReturnMoneyOutDao.returnMoney2User(returnMoney2User);
            if(1 == i){
                UserBean userBean1 = userDao.get(user);//修改用户表余额后的用户实体

                MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
                moneyChangeBean.setId(Utils.uuid());
                moneyChangeBean.setUserId(moneyRecordBean.getUserId());
                moneyChangeBean.setDifMoney(moneyRecordBean.getMoney().add(moneyRecordBean.getFee()));//变更金额
                moneyChangeBean.setType(4);
                moneyChangeBean.setAfterMoney(returnMoney2User.getDifMoney().add(user.getReturnMoney()));//变更后的金额
                BigDecimal moneybefore = userBean1.getMoney().subtract(moneyRecordBean.getMoney().add(moneyRecordBean.getFee()));//变更前金额
                moneyChangeBean.setBeforeMoney(user.getReturnMoney());//变更前金额
                moneyChangeBean.setCreateTime(new Date());
                moneyChangeBean.setRemark(moneyRecordBean.getSerialNo());
                moneyChangeDao.insert(moneyChangeBean);//新增资金变更表
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 提现失败，回滚金额
     * @param returnMoneyOutBean
     */
    public void returnMoney2User(ReturnMoneyOutBean returnMoneyOutBean){
        try{
            // 失败
            // 处理失败，退钱给用户
            UserBean user = iReturnMoneyOutDao.queryUserMoney(returnMoneyOutBean.getUserId());
            ReturnMoney2User returnMoney2User = new ReturnMoney2User();
            returnMoney2User.setId(returnMoneyOutBean.getUserId());
            // 提现金额+手续费
            returnMoney2User.setDifMoney(returnMoneyOutBean.getMoney().add(returnMoneyOutBean.getFee()));
            returnMoney2User.setMoney(user.getReturnMoney());
            int status = iReturnMoneyOutDao.returnMoney2User(returnMoney2User);
            if(1 == status){
                UserBean userBean1 = userDao.get(user);//修改用户表余额后的用户实体

                MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
                moneyChangeBean.setId(Utils.uuid());
                moneyChangeBean.setUserId(returnMoneyOutBean.getUserId());
                moneyChangeBean.setDifMoney(returnMoneyOutBean.getMoney());//变更金额
                moneyChangeBean.setType(1);
                moneyChangeBean.setAfterMoney(userBean1.getMoney());//变更后的金额
                BigDecimal moneybefore = userBean1.getMoney().subtract(returnMoneyOutBean.getMoney().add(returnMoneyOutBean.getFee()));//变更前金额
                moneyChangeBean.setBeforeMoney(moneybefore);
                moneyChangeBean.setCreateTime(new Date());
                moneyChangeBean.setRemark(returnMoneyOutBean.getId());
                moneyChangeDao.insert(moneyChangeBean);//新增资金变更表
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 通知用户
     * @param userId
     * @param content
     */
    public void sendMessageToUser(String userId, String content){
        UserMessageBean userMessageBean = new UserMessageBean();
        userMessageBean.setId(Utils.uuid());
        userMessageBean.setUserId(userId);
        userMessageBean.setTitle("提现");
        userMessageBean.setContent(content);
        userMessageBean.setReadStatus(0);
        userMessageBean.setType(0);
        userMessageBean.setCreateTime(new Date());
        userDao.insertUserMessage(userMessageBean);
    }

    public String reviewNoPass(String ids, String failureReason){
        // 出金审核不通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }

        List<String> idsArray = new ArrayList<>();
        ReturnMoneyOutBean returnMoneyOutBean = null;
        for (int i=0; i < idArr.length; i++){
            returnMoneyOutBean = iReturnMoneyOutDao.get(idArr[i]);
            if(returnMoneyOutBean.getStatus() != null && returnMoneyOutBean.getStatus() == 0){
                idsArray.add(idArr[i]);
                returnMoney2User(returnMoneyOutBean);
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的佣金提现审核不通过，流水号：").concat(returnMoneyOutBean.getSerialNo()).concat("，原因：").concat(failureReason));
            }
        }

        idArr = idsArray.toArray(new String[idsArray.size()]);
        if(idArr.length != 0){
            Map<String, Object> map = new HashMap<>();
            map.put("ids", idArr);
            map.put("failureReason", failureReason);
            iReturnMoneyOutDao.nopass(map);
        }
        return "成功";
    }

}
