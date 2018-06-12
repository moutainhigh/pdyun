package com.rmkj.microcap.modules.moneyrecord.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.MoneyOutServiceInterface;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.weifutong.service.MoneyService;
import com.rmkj.microcap.common.modules.money.out.weipeng.utils.WeiPengUtils;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by renwp on 2017/3/20.
 * 威富通代付
 */
@Service
public class WeiFuTongService extends CustomerWithdrawalsService {

    private final Logger Log = Logger.getLogger(getClass());

    @Autowired
    private BaseService baseService;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private IMoneyRecordDao iMoneyRecordDao;

    public synchronized String reviewPass(String ids) {
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        // 剔除掉已经审核的
        List<WithdrawalsBean> list = iMoneyRecordDao.queryInfoForOut(Arrays.asList(idArr));
        return moneyService.batchOut(list, this);
    }

    public synchronized String reviewNoPass(String ids, String failureReason) {
        return baseService.reviewNoPass(ids, failureReason);
    }

    /**
     * 主动定频查询提现结果
     * 不开事务
     * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
     */
    //@Scheduled(initialDelay = 10000, fixedDelay = 60000*5)
    public void checkOutResult(){
        List<MoneyRecordBean> list = iMoneyRecordDao.findNoResult2Check();
        if(ProjectConstants.PRO_DEBUG){
            return;
        }
        list.forEach(moneyRecord -> {
            int result = moneyService.queryResult(moneyRecord, this);
            if(result == 0){
                // 成功
                success(moneyRecord);
            }else if(result == 1){
                failure(moneyRecord);
            }else{
                // TODO 出金查询结果未知

            }
        });
    }

    public void review(MoneyRecordBean moneyRecord){
        iMoneyRecordDao.review(moneyRecord);
    }

    /**
     * 失败
     * @param moneyRecord
     */
    public void failure(MoneyRecordBean moneyRecord){
        baseService.checkFailure(moneyRecord, false);
        baseService.sendWeiXinMsg(moneyRecord,
                moneyRecord.getMoney().add(moneyRecord.getFee()).toString(),
                "余额", "失败", WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), "及时联系客服处理");
    }
    /**
     * 失败
     * @param moneyRecord
     */
    public void success(MoneyRecordBean moneyRecord){
        baseService.checkSuccess(moneyRecord, false);
        baseService.sendWeiXinMsg(moneyRecord,
                moneyRecord.getMoney().add(moneyRecord.getFee()).toString(), "余额", "成功，手续费".concat(moneyRecord.getFee().toString()).concat("元"),
                WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), "查收");
    }

}
