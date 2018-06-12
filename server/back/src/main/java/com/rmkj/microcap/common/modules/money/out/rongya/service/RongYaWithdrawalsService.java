package com.rmkj.microcap.common.modules.money.out.rongya.service;/**
 * Created by Administrator on 2018/1/2.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.modules.money.out.MoneyOut;
import com.rmkj.microcap.common.modules.money.out.MoneyOutServiceInterface;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.guofu.http.HttpClientUtils;
import com.rmkj.microcap.common.modules.money.out.rongya.http.RongYaApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author k
 * @create -01-02-13:23
 **/
@Service
public class RongYaWithdrawalsService implements MoneyOut{

    private final Logger Log = Logger.getLogger(RongYaWithdrawalsService.class);

    @HttpService
    private RongYaApi rongYaApi;

    @Value("${rongya_withdrawals_url}")
    String withdrawals_url;

    @Value("${rongya_withdrawals_merchat_no}")
    String merchat_no;

    @Value("${rongya_withdrawals_key}")
    String key;

    @Override
    public String batchOut(List<WithdrawalsBean> list, MoneyOutServiceInterface moneyOutService) {
        Map<String, Object> parameter = new HashedMap();

        StringBuilder sb = new StringBuilder();
        int successCount = 0, failureCount = 0, errorCount = 0, waitQueryCount = 0;
        try{
            for (WithdrawalsBean withdrawalsBean : list){
                StringBuffer sign = new StringBuffer();
                parameter.clear();
                parameter.put("userid", merchat_no);
                parameter.put("cusNo", withdrawalsBean.getSerialNo());
                parameter.put("applyMoney", withdrawalsBean.getMoney().toString());
                parameter.put("payeeAccount", withdrawalsBean.getBankAccount());
                parameter.put("bankCode", "1003");
                parameter.put("payeeUserName", withdrawalsBean.getChnName());
                sign.append("userid=").append(parameter.get("userid"))
                        .append("&cusNo=").append(parameter.get("cusNo"))
                        .append("&applyMoney=").append(parameter.get("applyMoney"))
                        .append("&payeeAccount=").append(parameter.get("payeeAccount")).append(key);
                parameter.put("sign", DigestUtils.md5Hex(sign.toString()));
                Log.info("融雅代付请求报文:".concat(JSON.toJSONString(parameter)));
                String result = HttpClientUtils.doPostPre(withdrawals_url, HttpClientUtils.map2str(parameter), "Utf-8");
                if("success".equals(result)){
                    successCount++;
                    moneyOutService.review(withdrawalsBean);
                    Log.info("融雅出金成功:".concat(withdrawalsBean.getSerialNo()));
                }else {
                    failureCount++;
                    sb.append(result).append(" ");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            sb.append(e.getMessage()).append(" ");
        }

        return "成功处理".concat(successCount+"").concat("笔，失败")
                .concat(failureCount+"").concat("笔，待查询")
                .concat(waitQueryCount+"").concat("笔，错误")
                .concat(errorCount+"").concat("笔！").concat(sb.toString());
    }

    @Override
    public int queryResult(WithdrawalsBean moneyRecord, MoneyOutServiceInterface moneyOutService) {
        return 0;
    }

}
