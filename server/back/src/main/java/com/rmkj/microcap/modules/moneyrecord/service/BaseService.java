package com.rmkj.microcap.modules.moneyrecord.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
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
    private IMoneyRecordDao iMoneyRecordDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private WeiXinService weiXinService;

    public void checkSuccess(MoneyRecordBean moneyRecord){
        checkSuccess(moneyRecord, true);
    }

    public void checkSuccess(MoneyRecordBean moneyRecord, boolean isSendMsg){
        iMoneyRecordDao.updateSuccess(moneyRecord.getId());
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
        // 失败
        iMoneyRecordDao.updateFailure(moneyRecord.getId());
        returnMoney2User(moneyRecord);
        if(isSendMsg){
            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                    .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
        }
    }

    /**
     *
     */
    public void sendWeiXinMsg(MoneyRecordBean moneyRecord, String... params){
        weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, moneyRecord.getUserId(), params);
    }

    /**
     * 提现失败，回滚金额
     * @param moneyRecord
     */
    public void returnMoney2User(MoneyRecordBean moneyRecord){
        try{
            // 处理失败，退钱给用户
            UserBean user = iMoneyRecordDao.queryUserMoney(moneyRecord.getUserId());
            ReturnMoney2User returnMoney2User = new ReturnMoney2User();
            returnMoney2User.setId(moneyRecord.getUserId());
            // 提现金额+手续费
            returnMoney2User.setDifMoney(moneyRecord.getMoney().add(moneyRecord.getFee()));
            returnMoney2User.setMoney(user.getMoney());

            if(iMoneyRecordDao.returnMoney2User(returnMoney2User) == 1){
                MoneyChangeBean moneyChangeBean = new MoneyChangeBean();
                moneyChangeBean.setId(Utils.uuid());
                moneyChangeBean.setUserId(returnMoney2User.getId());
                moneyChangeBean.setBeforeMoney(returnMoney2User.getMoney());
                moneyChangeBean.setDifMoney(returnMoney2User.getDifMoney());
                moneyChangeBean.setAfterMoney(returnMoney2User.getMoney().add(returnMoney2User.getDifMoney()));
                // 类型 0 充值 1 提现 2 建仓 3 平仓 4 提现失败退款
                moneyChangeBean.setType(4);
                moneyChangeBean.setRemark(moneyRecord.getSerialNo());

                iMoneyRecordDao.addMoneyChange(moneyChangeBean);
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
        MoneyRecordBean moneyRecord = null;
        for (int i=0; i < idArr.length; i++){
            moneyRecord = iMoneyRecordDao.get(idArr[i]);
            if(moneyRecord.getStatus() != null && moneyRecord.getStatus() == 0){
                // 失败
                idsArray.add(idArr[i]);
                returnMoney2User(moneyRecord);
                sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                        .concat("元的提现审核不通过，流水号：").concat(moneyRecord.getSerialNo()).concat("，原因：").concat(failureReason));
            }
        }

        idArr = idsArray.toArray(new String[idsArray.size()]);
        if(idArr.length != 0){
            Map<String, Object> map = new HashMap<>();
            map.put("ids", idArr);
            map.put("failureReason", failureReason);
            iMoneyRecordDao.nopass(map);
        }

        return "成功";
    }

}
