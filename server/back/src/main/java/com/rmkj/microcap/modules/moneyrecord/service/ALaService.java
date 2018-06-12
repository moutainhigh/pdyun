package com.rmkj.microcap.modules.moneyrecord.service;/**
 * Created by Administrator on 2017/4/7.
 */

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.BalanceBean;
import com.rmkj.microcap.common.modules.money.out.ytb.bean.WithdrawalsResBean;
import com.rmkj.microcap.common.modules.money.out.ytb.service.WithdrawalsService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.ReturnMoney2User;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import com.sun.media.sound.AlawCodec;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author k
 * @create -04-07-11:34
 **/
@Service
public class ALaService {
    private final static Logger Log = Logger.getLogger(ALaService.class);

    @Autowired
    private IMoneyRecordDao iMoneyRecordDao;

    @Autowired
    private WithdrawalsService withdrawalsService;

    @Autowired
    private MoneyOutService moneyOutService;

    @Autowired
    private IUserDao userDao;

    /**
     * 主动定频查询提现结果
     * 不开事务
     * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
     */
    //@Scheduled(initialDelay = 30000, fixedRate = 30000)
    public void checkOutResult(){
        List<MoneyRecordBean> list = iMoneyRecordDao.findNoResult2Check();
        if(ProjectConstants.PRO_DEBUG || CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(moneyRecord -> {
            WithdrawalsResBean withdrawalsResBean = withdrawalsService.selWithdrawalsResult(moneyRecord.getSerialNo());
            Log.info("实时代付接口结果返回：" +"流水号："+moneyRecord.getSerialNo()+"返回码："+ withdrawalsResBean.getRespCode() +" 描述："+ withdrawalsResBean.getMessage()+" 提现状态"+withdrawalsResBean.getTransStatus()+" 代付状态："+withdrawalsResBean.getPayStatus());
            if("00".equals(withdrawalsResBean.getRespCode()) && "2".equals(withdrawalsResBean.getTransStatus()) ){
                // 成功
                iMoneyRecordDao.updateSuccess(moneyRecord.getId());
                sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                        .concat("元的提现成功处理，手续费").concat(moneyRecord.getFee().toString()).concat("元，流水号：")
                        .concat(moneyRecord.getSerialNo()));
            }else{
                Map<String, Object> map = new HashMap<>();
                map.put("id", moneyRecord.getId());
                map.put("failureReason", withdrawalsResBean.getMessage());
                iMoneyRecordDao.updateFailureMap(map);
                returnMoney2User(moneyRecord);
                sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                        .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
            }
        });
    }

    /**
     * 走三方提现接口
     * id用英文逗号连接的字符串
     * @return
     */
    public String batchOut(String[] idArr){
        final int[] record = {0, 0, 0, 0}; //总笔数-成功-失败-超时
        if(idArr.length != 0){
            List<WithdrawalsBean> list = iMoneyRecordDao.queryInfoForOut(Arrays.asList(idArr));
            // 手续费怎么扣除
            //sb.insert(0, "".concat(count[0] +"").concat("|").concat(String.format("%1.02f", totalMoney[0])).concat("|").concat(moneyService.batchNo()));
            if(!ProjectConstants.PRO_DEBUG){
               /* for(MoneyRecordForOutBean moneyRecord:list){
                    BalanceBean balanceBean = withdrawalsService.selBalance();
                    if(balanceBean.getAmount() == null){
                        return "查询余额接口异常!";
                    }
                    if(Double.parseDouble(balanceBean.getAmount()) < moneyRecord.getMoney().doubleValue()){
                        return "余额不足，剩余资金为".concat(balanceBean.getAmount()).concat("元！");
                    }
                    //提交代付
                    WithdrawalsResBean withdrawalsResBean = withdrawalsService.withdrawals(moneyRecord);
                    Log.info("实时代付接口结果返回：" + "流水号："+moneyRecord.getSerialNo()+"返回码："+withdrawalsResBean.getRespCode() +" 描述："+ withdrawalsResBean.getMessage()+" 提现状态"+withdrawalsResBean.getTransStatus()+" 代付状态："+withdrawalsResBean.getPayStatus());
                    //代付返回 提现状态为2默认成功，不管代付状态
                    if ("00".equals(withdrawalsResBean.getRespCode()) && "2".equals(withdrawalsResBean.getTransStatus())) {
                        //审核成功修改审核状态
                        iMoneyRecordDao.updateSuccess(moneyRecord.getId());
                        sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                                .concat("元的提现成功处理，手续费").concat(moneyRecord.getFee().toString()).concat("元，流水号：")
                                .concat(moneyRecord.getSerialNo()));
                        record[1]++;
                    }else {
                        if("72".equals(withdrawalsResBean.getRespCode())){
                            //超时
                            iMoneyRecordDao.updateTimeOut(moneyRecord.getId());
                            record[3]++;
                        }else{
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", moneyRecord.getId());
                            map.put("failureReason", withdrawalsResBean.getMessage());
                            iMoneyRecordDao.updateFailureMap(map);
                            returnMoney2User(moneyRecord);
                            sendMessageToUser(moneyRecord.getUserId(), "您有一笔".concat(moneyRecord.getMoney().toString())
                                    .concat("元的提现处理失败，流水号：").concat(moneyRecord.getSerialNo()));
                            record[2]++;
                        }
                    }
                }*/
            }
        }
        return "成功处理".concat(record[1]+"笔提现！"+"提现失败"+record[2]+"笔！"+"提现超时"+record[3]+"笔！");
    }


    public String reviewPass(String ids){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        String batchOut = batchOut(idArr);

        return batchOut;
    }

    /**
     * 提现失败，回滚金额
     * @param moneyRecord
     */
    private void returnMoney2User(MoneyRecordBean moneyRecord){
        try{
            // 失败
            //iMoneyRecordDao.updateFailure(moneyRecord.getId());
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
}
