package com.rmkj.microcap.modules.ReturnMoneyOut.service;/**
 * Created by Administrator on 2017/11/21.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownNotifyBean;
import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownResponseBean;
import com.rmkj.microcap.common.modules.money.out.crown.service.CrownPayService;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author k
 * @create -11-21-16:09
 **/
@Service
public class CrownService {

    private static Logger logger = Logger.getLogger(CrownService.class);

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;

    @Autowired
    private CrownPayService crownPayService;

    @Autowired
    private BaseService baseService;

    public synchronized String pass(String ids){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        // 剔除掉已经审核的
        List<WithdrawalsBean> returnMoneyOutBeen = iReturnMoneyOutDao.queryInfoForOut(Arrays.asList(idArr));
        return batchOut(null);
    }

    /**
     * 批量处理军团长提现,皇冠代付
     * @param list
     * @return
     */
    public String batchOut(List<MoneyRecordForOutBean> list){
        final int[] total = {0, 0, 0, 0};
        list.forEach(moneyRecordBean -> {
            Map<String, Object> param = new HashMap();
            try {
                String result = crownPayService.crownMoneyOut(moneyRecordBean, "2");

                CrownResponseBean crownResponse = JSON.parseObject(result, CrownResponseBean.class);

                if("true".equals(crownResponse.getSuccess()) && "0000".equals(crownResponse.getCode())){//处理成功
                    if(disposeReturnMoneyRecord(moneyRecordBean, crownResponse, "2") == 1){
                        //代付成功,发消息
                        //sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请已通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，实际到账").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                        total[0]++;
                    }
                }else if("false".equals(crownResponse.getSuccess()) || !"0000".equals(crownResponse.getCode())){
                    if(disposeReturnMoneyRecord(moneyRecordBean, crownResponse, "3") == 1 ){
                        moneyRecordBean.setFailureReason(crownResponse.getMessage());
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
     * @param moneyRecordForOutBean
     * @param entity
     * @param flag 1,4:处理中 2:成功 3:失败
     * @return
     */
    public int disposeReturnMoneyRecord(MoneyRecordForOutBean moneyRecordForOutBean, CrownResponseBean entity, String flag){
        ReturnMoneyOutBean returnMoneyOutBean = new ReturnMoneyOutBean();
        returnMoneyOutBean.setId(moneyRecordForOutBean.getId());
        if("1".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(entity.getMerOrderId());
            returnMoneyOutBean.setRemark(entity.getMessage());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("2".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(entity.getMerOrderId());
            returnMoneyOutBean.setRemark(entity.getMessage());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            //returnMoneyOutBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("3".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(entity.getMerOrderId());
            returnMoneyOutBean.setFailureReason(entity.getMessage());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            returnMoneyOutBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.FAILD));
        }
        int result = iReturnMoneyOutDao.outPastInStatusAndReviewStatus(returnMoneyOutBean);
        return result;
    }

    /**
     * 返佣出金，异步通知处理
     * @param entity
     * @return
     */
    public boolean crownReturnMoneyNotify(CrownNotifyBean entity){
        ReturnMoneyOutBean returnMoneyOutBean = iReturnMoneyOutDao.getReturnMoneyByserialNo(entity.getMerOrderId());
        if(null == returnMoneyOutBean){
            return true;
        }

        if("true".equals(entity.getSuccess())){
            returnMoneyOutBean.setThirdSerialNo(entity.getOrderId());
            returnMoneyOutBean.setStatus(1);
            returnMoneyOutBean.setRemark(entity.getMessage());
            if(iReturnMoneyOutDao.updateDaiPayOutPastInStatus(returnMoneyOutBean) == 1){
//                weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, recordBean.getUserId(), recordBean.getMoney().toString(), status, WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"));
                baseService.sendMessageToUser(returnMoneyOutBean.getUserId(), "您的提现申请已通过,手续费".concat(returnMoneyOutBean.getFee().toString()).concat("元，实际到账").concat(returnMoneyOutBean.getMoney().toString()).concat("元"));
                logger.info("皇冠代付，返佣代付成功");
                return true;
            }
        }else{
            returnMoneyOutBean.setThirdSerialNo(entity.getOrderId());
            returnMoneyOutBean.setStatus(2);
            returnMoneyOutBean.setFailureReason(entity.getMessage());
            if(iReturnMoneyOutDao.updateDaiPayOutPastInStatus(returnMoneyOutBean) == 1){
//                weiXinService.sendMessage(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_TYPE.TI_XIAN, recordBean.getUserId(), recordBean.getMoney().toString(), status, WeiPengUtils.getNowTime("yyyy-MM-dd HH:mm:ss"));
                baseService.returnMoney2User(returnMoneyOutBean);
                baseService.sendMessageToUser(returnMoneyOutBean.getUserId(), "您的提现申请未通过,手续费".concat(returnMoneyOutBean.getFee().toString()).concat("元，提现金额").concat(returnMoneyOutBean.getMoney().toString()).concat("元"));
                logger.info("皇冠代付，返佣退款成功");
                return true;
            }
        }
        return false;
    }
}
