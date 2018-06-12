package com.rmkj.microcap.modules.moneyrecord.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.yizhifu.CheckUtils;
import com.rmkj.microcap.common.modules.money.out.yizhifu.MoneyService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by renwp on 2017/1/4.
 */
@Service
public class MoneyOutService {

    private final Logger Log = Logger.getLogger(MoneyOutService.class);

    @Autowired
    private IMoneyRecordDao iMoneyRecordDao;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private IUserDao userDao;

    /**
     * 主动定频查询提现结果
     * 不开事务
     * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
     */
    //Scheduled(initialDelay = 10000, fixedDelay = 30000)
    public void checkOutResult(){
        List<MoneyRecordBean> list = iMoneyRecordDao.findNoResult2Check();
        if(ProjectConstants.PRO_DEBUG){
            return;
        }
        list.forEach(moneyRecord -> {
            // 0-未处理， 1-已成功 2-处理中 3-已失败 4-待处理（自动处理程序状态） 8-没有该用户标识对应的代付记录 9-查询失败 提现结果查询
            String result = moneyService.queryResult(moneyRecord.getId());
            if("1".equals(result)){
                // 成功
                iMoneyRecordDao.updateSuccess(moneyRecord.getId());
                sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                        .concat("元的提现成功处理，手续费").concat(moneyRecord.getFee().toString()).concat("元，流水号：")
                        .concat(moneyRecord.getSerialNo()));
            }else if("3".equals(result)){
                returnMoney2User(moneyRecord);
                sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                        .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
            }else{

            }
        });
    }

    /**
     * 通知用户
     * @param userId
     * @param content
     */
    private void sendMessageToUser(String userId, String content){
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

    /**
     * 提现失败，回滚金额
     * @param moneyRecord
     */
    private void returnMoney2User(MoneyRecordBean moneyRecord){
        try{
            // 失败
            iMoneyRecordDao.updateFailure(moneyRecord.getId());
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
     * 走三方提现接口
     * id用英文逗号连接的字符串
     * @return
     */
    public String batchOut(String[] idArr){
        final int[] count = {0};
        if(idArr.length != 0){
            List<WithdrawalsBean> list = iMoneyRecordDao.queryInfoForOut(Arrays.asList(idArr));
            StringBuilder sb = new StringBuilder();

            double[] totalMoney = new double[1];
            // 手续费怎么扣除
            //
            list.forEach(moneyRecord -> {
                sb.append("$").append(moneyRecord.getBankAccount())
                        .append("|").append(moneyRecord.getChnName())
                        .append("|").append(moneyRecord.getOpenBankName())
                        .append("|").append(moneyRecord.getProvince())
                        .append("|").append(moneyRecord.getCity())
                        .append("|").append(String.format("%1.02f", moneyRecord.getMoney()))
                        .append("|").append(moneyRecord.getId())
                        .append("|").append(moneyRecord.getLianHangNo() == null ? CheckUtils.getLianHangHao(moneyRecord.getBankName()) : moneyRecord.getLianHangNo());
                count[0]++;
                totalMoney[0] += moneyRecord.getMoney().doubleValue();
            });
            String overMoney = moneyService.queryOverMoney();
            if(overMoney == null){
                return "代付余额查询失败！";
            }
            if(Double.parseDouble(overMoney) < count[0]*2+totalMoney[0]){
                return "余额不足，剩余资金为".concat(overMoney).concat("元！");
            }
            sb.insert(0, "".concat(count[0] +"").concat("|").concat(String.format("%1.02f", totalMoney[0])).concat("|").concat(moneyService.batchNo()));
            if(!ProjectConstants.PRO_DEBUG){
                String[] results = moneyService.batchOut(sb.toString());
                if(!"0".equals(results[0])){
//                    list.forEach(moneyRecord -> {
//                        returnMoney2User(moneyRecord);
//                        sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
//                                .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
//                    });
                    return "处理失败！".concat(results[1]);
                }
            }
        }
        return "成功处理".concat(count[0]+"笔提现！");
    }

    /**
     *
     * @param ids 用逗号拼接的ids
     * @return
     */
    public String reviewPass(String ids){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        String batchOut = batchOut(idArr);
        if(batchOut.startsWith("成功处理") && idArr.length != 0){
            iMoneyRecordDao.pass(Arrays.asList(idArr));
        }
        return batchOut;
    }

    public String reviewNoPass(String ids, String failureReason){
        // 出金审核不通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        if(idArr.length != 0){
            Map<String, Object> map = new HashMap<>();
            map.put("ids", idArr);
            map.put("failureReason", failureReason);
            iMoneyRecordDao.nopass(map);
        }
        MoneyRecordBean moneyRecord = null;
        for (int i=0; i < idArr.length; i++){
            moneyRecord = iMoneyRecordDao.get(idArr[i]);
            returnMoney2User(moneyRecord);
            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                    .concat("元的提现审核不通过，流水号：").concat(moneyRecord.getSerialNo()).concat("，原因：").concat(failureReason));
        }
        return "成功";
    }

}
