package com.rmkj.microcap.modules.ReturnMoneyOut.service;/**
 * Created by Administrator on 2017/11/16.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.guofu.entity.GuoFuResultBean;
import com.rmkj.microcap.common.modules.money.out.guofu.http.HttpClientUtils;
import com.rmkj.microcap.common.modules.money.out.guofu.service.GuoFuPayService;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author k
 * @create -11-16-12:12
 **/
@Service
public class GuoFuService {

    @Autowired
    private GuoFuPayService guoFuPayService;

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;

    public String pass(String ids){
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
     * 批量处理国富代付
     * @param list
     * @return
     */
    public String batchOut(List<MoneyRecordForOutBean> list){
        final int[] total = {0, 0, 0, 0};
        list.forEach(moneyRecordBean -> {
            Map<String, Object> param = new HashMap();
            try {
                guoFuPayService.buildParam(param, moneyRecordBean);
                String buildParam = map2str(param);
                //Response<String> execute = guoFuApi.virtPay(buildParam).execute();
                //String str = JSON.toJSONString(execute.body());
                //logger.info(str);

                String result = HttpClientUtils.doPost(ProjectConstants.GUOFU_URL, buildParam, "GBK");
                GuoFuResultBean guoFuResultBean = JSON.parseObject(result, GuoFuResultBean.class);
                if("00".equals(guoFuResultBean.getRespCode()) && "2".equals(guoFuResultBean.getTransStatus()) && "2".equals(guoFuResultBean.getPayStatus())){//处理成功
                    if(disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "2") == 1){
                        //代付成功,发消息
                        guoFuPayService.sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请已通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，实际到账").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                        total[0]++;
                    }
                }else if("3".equals(guoFuResultBean.getTransStatus()) || "3".equals(guoFuResultBean.getPayStatus())){
                    if(disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "3") == 1 && guoFuPayService.updateNotOutMoney(moneyRecordBean, guoFuResultBean.getPayMsg()) == 1){
                        guoFuPayService.sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请未通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，提现金额").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                        total[1]++;
                    }
                }else if("4".equals(guoFuResultBean.getPayStatus()) || "1".equals(guoFuResultBean.getPayStatus()) || "1".equals(guoFuResultBean.getTransStatus())){//4-付款异常(需要发起查询进行确认) 1-代付处理中(需要发起查询进行确认)
                    disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "1");

                    total[2]++;
                }else{//respCode 处理失败

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "成功处理".concat(total[0]+"").concat("笔，失败").concat(total[1]+"").concat("笔，待查询").concat(total[2]+"").concat("笔，错误").concat(total[3]+"").concat("笔！");
    }

    //@Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 1)
    public void timingQueryGuofuDaiPayResult(){
        List<MoneyRecordForOutBean> list = iReturnMoneyOutDao.findNoResult2Check();
        if(ProjectConstants.PRO_DEBUG){
            return;
        }

        list.forEach(moneyRecordBean -> {
            Map<String, Object> param = new HashedMap();
            try {
                guoFuPayService.buildQueryParam(param, moneyRecordBean);
                String buildQueryParam = map2str(param);
                String result = HttpClientUtils.doPost(ProjectConstants.GUOFU_QUERY_URL, buildQueryParam, "GBK");
                GuoFuResultBean guoFuResultBean = JSON.parseObject(result, GuoFuResultBean.class);
                if("00".equals(guoFuResultBean.getRespCode()) && "2".equals(guoFuResultBean.getTransStatus()) && "2".equals(guoFuResultBean.getPayStatus())){//处理成功
                    if(disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "2") == 1){
                        //代付成功,发消息
                        guoFuPayService.sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请已通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，实际到账").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                    }
                }else if("3".equals(guoFuResultBean.getTransStatus()) || "3".equals(guoFuResultBean.getPayStatus())){
                    if(disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "3") == 1 && guoFuPayService.updateNotOutMoney(moneyRecordBean, guoFuResultBean.getPayMsg()) == 1){
                        guoFuPayService.sendMessageToUser(moneyRecordBean.getUserId(), "您的提现申请未通过,手续费".concat(moneyRecordBean.getFee().toString()).concat("元，提现金额").concat(moneyRecordBean.getMoney().toString()).concat("元"));
                    }
                }else if("4".equals(guoFuResultBean.getPayStatus()) || "1".equals(guoFuResultBean.getPayStatus()) || "1".equals(guoFuResultBean.getTransStatus())){//4-付款异常(需要发起查询进行确认) 1-代付处理中(需要发起查询进行确认)
                    disposeReturnMoneyRecord(moneyRecordBean, guoFuResultBean, "1");

                }else{//respCode 处理失败

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     * @param moneyRecordForOutBean
     * @param guoFuResultBean
     * @param flag 1,4:处理中 2:成功 3:失败
     * @return
     */
    public int disposeReturnMoneyRecord(MoneyRecordForOutBean moneyRecordForOutBean, GuoFuResultBean guoFuResultBean, String flag){
        ReturnMoneyOutBean returnMoneyOutBean = new ReturnMoneyOutBean();
        returnMoneyOutBean.setId(moneyRecordForOutBean.getId());
        if("1".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(guoFuResultBean.getOrderno());
            returnMoneyOutBean.setRemark(guoFuResultBean.getPayMsg());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("2".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(guoFuResultBean.getOrderno());
            returnMoneyOutBean.setRemark(guoFuResultBean.getPayMsg());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            returnMoneyOutBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
        }else if("3".equals(flag)){
            returnMoneyOutBean.setThirdSerialNo(guoFuResultBean.getOrderno());
            returnMoneyOutBean.setRemark(guoFuResultBean.getPayMsg());
            returnMoneyOutBean.setReviewStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.SUCCESS));
            returnMoneyOutBean.setStatus(Integer.valueOf(Constants.WITHDRAW_STATUS.FAILD));
        }
        int result = iReturnMoneyOutDao.outPastInStatusAndReviewStatus(returnMoneyOutBean);
        return result;
    }

    /**
     * 将MAP转成字符串
     *
     * @param param
     * @return
     */
    private String map2str(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer();
        boolean flag = true;
        Set<String> set = param.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = (String) param.get(key);
            if (flag) {
                flag = false;
            } else {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }
}
