package com.rmkj.microcap.common.modules.money.out.weifutong.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.MoneyOut;
import com.rmkj.microcap.common.modules.money.out.MoneyOutServiceInterface;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.weifutong.XmlTools;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.AipgRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.common.InfoReq;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.payreq.Body;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.payreq.Trans_Detail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.payreq.Trans_Sum;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.rtreq.Trans;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.transquery.QTDetail;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.transquery.QTransRsp;
import com.rmkj.microcap.common.modules.money.out.weifutong.bean.transquery.TransQueryReq;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 123 on 2017/3/20.
 * 威富通代付实现类
 */
@Service
public class MoneyService implements MoneyOut {

    private final Logger Log = Logger.getLogger(this.getClass());

    private final AtomicInteger atomicInteger = new AtomicInteger();

    private final String yyyymmddhhmmss = "%1$tY%1$tm%1$td%1$tH%1$tM%1$tS";

    private String reqSn(){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        String nowString = String.format(yyyymmddhhmmss, now);
        return reqSn(nowString);
    }

    private String reqSn(String time){
        return ProjectConstants.WEI_FU_TONG_NO
                .concat(time)
                .concat(atomicInteger.getAndIncrement()+"");
    }

    public String batchOut(List<WithdrawalsBean> list, MoneyOutServiceInterface moneyOutService) {
        final int[] total = {0, 0, 0, 0};
        InfoReq infoReq = makeInfoReq("100014");
        list.forEach(moneyRecordForOutBean -> {
            // 威富通代付接口
            AipgReq aipg = new AipgReq();
            // 单笔实时代付
            aipg.setINFO(infoReq);

            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            String nowString = String.format(yyyymmddhhmmss, now);
            aipg.getINFO().setREQ_SN(reqSn(nowString));

            Trans trans = new Trans();
            aipg.addTrx(trans);

            trans.setBUSINESS_CODE(ProjectConstants.WEI_FU_TONG_BUSINESS_CODE);
            trans.setMERCHANT_ID(ProjectConstants.WEI_FU_TONG_NO);
            trans.setSUBMIT_TIME(nowString);

            trans.setACCOUNT_NO(moneyRecordForOutBean.getBankAccount());
            trans.setACCOUNT_NAME(moneyRecordForOutBean.getChnName());
            trans.setAMOUNT(moneyRecordForOutBean.getMoney().multiply(new BigDecimal(100)).intValue()+"");
            // 数字签名
            try {
                String xml = XmlTools.buildXml(aipg, true);
                xml = signMsg(xml);
                Log.info("单笔实时代付发送：".concat(xml));
                String result = XmlTools.send(ProjectConstants.WEI_FU_TONG_URL.concat("ProcessServlet"), xml);
                // 校验签名
                if(verifySign(result)){
                    Log.info("单笔实时代付结果：".concat(result));

                    AipgRsp aipgRsp = (AipgRsp) XmlTools.parseXml(result, false);
                    String retCode = aipgRsp.getINFO().getRET_CODE();
                    int resultInt = convertRetCode(retCode);
                    // 更新 REQ_SN 到 third_serial_no!!!
                    moneyRecordForOutBean.setThirdSerialNo(aipg.getINFO().getREQ_SN());
                    // 处理中
                    if(0 == resultInt || 1 == resultInt || 2 == resultInt){
                        moneyOutService.review(moneyRecordForOutBean);
                    }
                    if(0 == resultInt){
                        moneyOutService.success(moneyRecordForOutBean);
                        total[0]++;
                    }else if(1 == resultInt){
                        moneyOutService.failure(moneyRecordForOutBean);
                        total[1]++;
                    }else if(2 == resultInt){
                        total[2]++;
                    }else{
                        total[3]++;
                    }
                }else{
                    Log.error("交易结果查询签名错误：".concat(result));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return "成功处理".concat(total[0]+"").concat("笔，失败").concat(total[1]+"").concat("笔，待查询").concat(total[2]+"").concat("笔，错误").concat(total[3]+"").concat("笔！");
    }

    @Override
    public int queryResult(WithdrawalsBean moneyRecord, MoneyOutServiceInterface moneyOutService) {
        // 威富通交易结果查询
        AipgReq aipg = new AipgReq();
        aipg.setINFO(makeInfoReq("200004"));
        aipg.getINFO().setREQ_SN(reqSn());

        TransQueryReq dr=new TransQueryReq();
        aipg.addTrx(dr);
        dr.setMERCHANT_ID(ProjectConstants.WEI_FU_TONG_NO) ;
        dr.setQUERY_SN(moneyRecord.getThirdSerialNo());
        dr.setTYPE(1) ;


        try {
            String xml = XmlTools.buildXml(aipg, true);
            xml = signMsg(xml);
            Log.info("交易结果查询发送：".concat(xml));
            String result = XmlTools.send(ProjectConstants.WEI_FU_TONG_URL.concat("ProcessServlet"), xml);
            // 校验签名
            if(verifySign(result)){
                Log.info("交易结果查询结果：".concat(result));
                String trxcode = trxCode(result);
                if(StringUtils.isNotEmpty(trxcode)){
                    AipgRsp aipgRsp = (AipgRsp) XmlTools.parseXml(result, false);
                    String trxCode = aipgRsp.getINFO().getRET_CODE();
                    if("0000".equals(trxCode)){
                        String retCode = ((QTDetail)((QTransRsp) aipgRsp.getTrxData().get(0)).getDetails().get(0)).getRET_CODE();
                        int resultInt = convertRetCode(retCode);
                        // 单笔实时 》 对于30分钟内通联一直返回1002的，应确认该笔交易失败，通联没有成功接收，应立刻停止继续查询
                        // 批量代付 》 对于50分钟内通联一直返回1002的，应确认该笔交易失败，通联没有成功接收，应立刻停止继续查询
                        Calendar calendar = Calendar.getInstance();
                        if("1002".equals(trxCode)){
                            if(moneyRecord.getReviewTime() != null && calendar.getTime().getTime() - moneyRecord.getReviewTime().getTime() > 30*60000){
                                moneyOutService.failure(moneyRecord);
                            }
                        }else if(0 != resultInt && 1 != resultInt){
                            // 超过12小时的直接失败
                            if(moneyRecord.getReviewTime() != null && calendar.getTime().getTime() - moneyRecord.getReviewTime().getTime() > 12*60*60000){
                                moneyOutService.failure(moneyRecord);
                            }
                        }
                        return resultInt;
                    }
                }
            }else{
                Log.error("交易结果查询签名错误：".concat(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int convertRetCode(String retCode){
        // 成功
        if("0000".equals(retCode)){
            return 0;
            // 失败
        }else if("1999".equals(retCode) || "2002".equals(retCode)
                || "2004".equals(retCode) || "2006".equals(retCode)){
            return 1;
        }else if("2000".equals(retCode)
                || "2001".equals(retCode) || "2003".equals(retCode) || "2005".equals(retCode)
                || "2007".equals(retCode) || "2008".equals(retCode)){
            return 2;
        }else{
            // 其他错误，继续查询或者外部继续处理
            return -1;
        }
    }

    private String signMsg(String xml) throws Exception {
        return XmlTools.signMsg(xml, ProjectConstants.WEI_FU_TONG_PRIVATE_KEY_CERT, ProjectConstants.WEI_FU_TONG_KEY, false);
    }

    private boolean verifySign(String result) throws Exception {
        return XmlTools.verifySign(result, ProjectConstants.WEI_FU_TONG_PUBLIC_KEY_CERT, false, false);
    }

    /**
     *
     * @param trxcod
     * @return
     */
    private InfoReq makeInfoReq(String trxcod) {
        InfoReq infoReq = new InfoReq();
        infoReq.setTRX_CODE(trxcod);
        infoReq.setVERSION("03");
        infoReq.setDATA_TYPE("2");
        infoReq.setLEVEL("5");
        infoReq.setUSER_NAME(ProjectConstants.WEI_FU_TONG_NAME);
        infoReq.setUSER_PASS(ProjectConstants.WEI_FU_TONG_KEY);
        return infoReq;
    }

    /**
     *
     * @param result
     * @return
     */
    private String trxCode(String result){
        if (result.indexOf("<TRX_CODE>") != -1) {
            int end = result.indexOf("</TRX_CODE>");
            int begin = end - 6;
            if (begin >= 0) return result.substring(begin, end);
        }
        return null;
    }

    /**
     * 批量代付
     * @param list
     * @param aipg
     * @param nowString
     */
    private void batchOut(List<MoneyRecordForOutBean> list, AipgReq aipg, String nowString){
        Body body = new Body();

        // 参数 AipgReq aipg

        List<Trans_Detail> details = new ArrayList<>();
        Trans_Detail trans_detail = null;
        Trans_Sum trans_sum = new Trans_Sum();
        BigDecimal totalSum = new BigDecimal(0);
        MoneyRecordForOutBean moneyRecordForOutBean = null;
        for (int i = 0; i < list.size(); i++){
            moneyRecordForOutBean = list.get(i);
            trans_detail = new Trans_Detail();
            trans_detail.setSN(String.format("%04d", i+1));
            // 存折必须填写，不填写
            trans_detail.setE_USER_CODE(null);
            trans_detail.setACCOUNT_NO(moneyRecordForOutBean.getBankAccount());
            trans_detail.setACCOUNT_NAME(moneyRecordForOutBean.getChnName());
            details.add(trans_detail);
            totalSum = totalSum.add(moneyRecordForOutBean.getMoney());
        }

        // 接入生产前，业务人员会提供，测试时，可以从该文档3.2对应的业务吗中随便选一个
        trans_sum.setBUSINESS_CODE(ProjectConstants.WEI_FU_TONG_BUSINESS_CODE);
        trans_sum.setMERCHANT_ID(ProjectConstants.WEI_FU_TONG_NO);
        trans_sum.setSUBMIT_TIME(nowString);
        trans_sum.setTOTAL_ITEM(details.size()+"");
        trans_sum.setTOTAL_SUM(totalSum.multiply(new BigDecimal(100)).intValue()+"");
        body.setDetails(details);
        body.setTRANS_SUM(trans_sum);
    }

}
