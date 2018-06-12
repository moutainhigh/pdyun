package com.rmkj.microcap.modules.moneyrecord.service;/**
 * Created by Administrator on 2017/11/21.
 */

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownNotifyBean;
import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownResponseBean;
import com.rmkj.microcap.common.modules.money.out.crown.service.CrownPayService;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author k
 * @create -11-21-13:58
 **/
@Service
public class CrownService {

    private static Logger logger = Logger.getLogger(CrownService.class);

    @Autowired
    private IMoneyRecordDao iMoneyRecordDao;
    
    @Autowired
    private CrownPayService crownPayService;

    @Autowired
    private BaseService baseService;

    public synchronized String reviewPass(String ids, HttpServletRequest request){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        // 剔除掉已经审核的
        List<WithdrawalsBean> list = iMoneyRecordDao.queryInfoForOut(Arrays.asList(idArr));
        return batchOut(null);
    }

    /**
     * 批量处理,皇冠代付
     * @param list
     * @return
     */
    public String batchOut(List<MoneyRecordForOutBean> list){
        final int[] total = {0, 0, 0, 0};
        list.forEach(moneyRecordBean -> {
            Map<String, Object> param = new HashMap();
            try {
                String result = crownPayService.crownMoneyOut(moneyRecordBean, "1");
                CrownResponseBean crownResponse = JSONObject.parseObject(result, CrownResponseBean.class);

                if("true".equals(crownResponse.getSuccess()) && "0000".equals(crownResponse.getCode())){//处理成功
                    if(disposeMoneyRecord(moneyRecordBean, crownResponse, "2") == 1){
                        total[0]++;
                    }
                }else if("false".equals(crownResponse.getSuccess()) || !"0000".equals(crownResponse.getCode())){ //代付失败
                    if(disposeMoneyRecord(moneyRecordBean, crownResponse, "3") == 1){
                        baseService.returnMoney2User(moneyRecordBean);
                        baseService.sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请未通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，提现金额").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                        total[1]++;
                    }
                }else{//respCode 处理失败

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "成功处理".concat(total[0]+"").concat("笔，失败").concat(total[1]+"").concat("笔，待查询").concat(total[2]+"").concat("笔，错误").concat(total[3]+"").concat("笔！");
    }

    /**
     *
     * @param moneyRecordBean
     * @param flag 1,4:处理中 2:成功 3:失败
     */
    public int disposeMoneyRecord(MoneyRecordBean moneyRecordBean, CrownResponseBean entity, String flag){
        if("1".equals(flag)){
            moneyRecordBean.setThirdSerialNo(entity.getOrderId());
            moneyRecordBean.setRemark(entity.getMessage());
            moneyRecordBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("2".equals(flag)){
            moneyRecordBean.setThirdSerialNo(entity.getOrderId());
            moneyRecordBean.setRemark(entity.getMessage());
            moneyRecordBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            //moneyRecordBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("3".equals(flag)){
            moneyRecordBean.setThirdSerialNo(entity.getOrderId());
            moneyRecordBean.setRemark(entity.getMessage());
            moneyRecordBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            moneyRecordBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.FAILD));
        }
        int result = iMoneyRecordDao.outPastInStatusAndReviewStatus(moneyRecordBean);
        return result;
    }

    /**
     * 皇冠出金代付，异步通知处理
     * @param entity
     * @return
     */
    public boolean crownPayNotify(CrownNotifyBean entity){
        MoneyRecordBean recordBean = iMoneyRecordDao.getDaiPayByserialNo(entity.getMerOrderId());
        if(null == recordBean){
            return true;
        }
        if("true".equals(entity.getSuccess())){
            recordBean.setThirdSerialNo(entity.getOrderId());
            recordBean.setStatus(1);
            recordBean.setRemark(entity.getMessage());
            if(iMoneyRecordDao.updateDaiPayOutPastInStatus(recordBean) == 1){
                baseService.sendMessageToUser(recordBean.getUserId(), "您有一笔".concat(recordBean.getMoney().toString()).concat("元的提现成功处理，手续费").concat(recordBean.getFee().toString()).concat("元，流水号：").concat(recordBean.getSerialNo()));
                logger.info("皇冠代付，代付成功");
                return true;
            }
        }else{
            recordBean.setThirdSerialNo(entity.getOrderId());
            recordBean.setStatus(2);
            recordBean.setFailureReason(entity.getMessage());
            if(iMoneyRecordDao.updateDaiPayOutPastInStatus(recordBean) == 1){
                baseService.returnMoney2User(recordBean);
                baseService.sendMessageToUser(recordBean.getUserId(), "您有一笔".concat(recordBean.getMoney().toString()).concat("元的提现失败，手续费").concat(recordBean.getFee().toString()).concat("元，流水号：").concat(recordBean.getSerialNo()));
                logger.info("皇冠代付，退款成功");
                return true;
            }
        }
        return false;
    }

}
