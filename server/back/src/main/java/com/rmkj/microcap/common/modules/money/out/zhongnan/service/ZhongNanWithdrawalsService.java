package com.rmkj.microcap.common.modules.money.out.zhongnan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.modules.money.out.MoneyOut;
import com.rmkj.microcap.common.modules.money.out.MoneyOutServiceInterface;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.zhongnan.bean.WithdrawalsCallBackReq;
import com.rmkj.microcap.common.modules.money.out.zhongnan.http.ZhongNanWithdrawalsApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.modules.moneyrecord.service.CustomerWithdrawalsService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service
public class ZhongNanWithdrawalsService implements MoneyOut {

	private final Logger Log = Logger.getLogger(getClass());

	@HttpService
    ZhongNanWithdrawalsApi zhongNanWithdrawalsApi;

	@Value("${zhongnan_withdrawals_merchant_no}")
	String merchant_no;

	@Value("${zhongnan_withdrawals_notify_url}")
	String notify_url;

	@Value("${zhongnan_withdrawals_key}")
	String key;

	@Override
	public String batchOut(List<WithdrawalsBean> list, MoneyOutServiceInterface moneyOutService) {
		Map<String, String> map = new HashMap<>();

		int successCount = 0, failureCount = 0, errorCount = 0, waitQueryCount = 0;
		JSONObject jsonObject;
		StringBuilder sb = new StringBuilder();
		try{
			for(WithdrawalsBean record : list){
				map.clear();

				map.put("merchant_no", merchant_no);
				map.put("amount", record.getMoney().multiply(new BigDecimal(100)).intValue()+"");
				map.put("pay_num", record.getSerialNo());
				map.put("notify_url", notify_url);
				map.put("sign", DigestUtils.md5Hex(merchant_no.
						concat(map.get("amount"))
						.concat(record.getSerialNo())
						.concat(DateUtils.getDate("yyyyMMdd"))
						.concat(key)).toUpperCase());
				// 0=对私 1=对公
				map.put("account_prop", "0");
				map.put("account_no", record.getBankAccount());
				map.put("account_name", record.getChnName());
				map.put("bank_general_name", record.getBankName());

				map.put("bank_name", record.getBankName());
//			map.put("bank_code", bank_code);
//			map.put("drct_bank_code", drct_bank_code);
				map.put("phone", record.getPhone());
				map.put("id_no", record.getIdCard());

				Response<String> execute = zhongNanWithdrawalsApi.createDFOrder(map).execute();
				if(execute.isSuccessful()){
					String body = execute.body();
					Log.info(record.getSerialNo() + " " + body);
					jsonObject = JSONObject.parseObject(body);

					if("00000".equals(jsonObject.getString("return_code"))
							|| "00001".equals(jsonObject.getString("return_code"))){
						successCount++;
						record.setThirdSerialNo(jsonObject.getString("out_trade_no"));
						moneyOutService.review(record);
					}else{
						failureCount++;
						sb.append(jsonObject.get("message")).append(" ");
					}
				}else{
					Log.error(record.getSerialNo() + " " + execute.errorBody().string());
					sb.append(execute.errorBody().string()).append(" ");
				}
			}
		} catch (Exception e) {
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
		String sign = DigestUtils.md5Hex(merchant_no
				.concat(moneyRecord.getThirdSerialNo())
				.concat(DateUtils.getDate("yyyyMMdd"))
				.concat(key)).toUpperCase();
		JSONObject jsonObject;
		try {
			Response<String> execute = zhongNanWithdrawalsApi.queryDFOrder(merchant_no, moneyRecord.getThirdSerialNo(), sign).execute();
			if(execute.isSuccessful()){
				String body = execute.body();
				Log.info(moneyRecord.getSerialNo() + " " +body);
				jsonObject = JSONObject.parseObject(body);
				if("10000".equals(jsonObject.getString("return_code"))){
					moneyOutService.success(moneyRecord);
				}else if("10009".equals(jsonObject.getString("return_code"))){
					moneyRecord.setFailureReason(jsonObject.getString("message"));
					moneyOutService.failure(moneyRecord);
				}
			}else{
				Log.error(moneyRecord.getSerialNo() + " " + execute.errorBody().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.info(moneyRecord.getSerialNo() + " " + e.getMessage());
		}
		return 0;
	}

	@Resource(name = "CustomerWithdrawalsService")
	CustomerWithdrawalsService customerWithdrawalsService;

	/*@Resource(name = "MemberUnitWithdrawalsService")
	MemberUnitWithdrawalsService memberUnitWithdrawalsService;*/

	public ResponseEntity callBack(WithdrawalsCallBackReq withdrawalsCallBackReq) {
		Log.info("中南代付回调:".concat(JSON.toJSONString(withdrawalsCallBackReq)));
		String sign = merchant_no
				.concat(withdrawalsCallBackReq.getOut_trade_no())
				.concat(withdrawalsCallBackReq.getPay_num())
				.concat(withdrawalsCallBackReq.getAmount())
				.concat(key);
		sign = DigestUtils.md5Hex(sign);
		Log.info("中南代付签名:" + sign + " " +withdrawalsCallBackReq.getSign());
		if(sign.equalsIgnoreCase(withdrawalsCallBackReq.getSign())){
			MoneyOutServiceInterface moneyOutServiceInterface;

			/*if(withdrawalsCallBackReq.getPay_num().startsWith("MO")){
				moneyOutServiceInterface = customerWithdrawalsService;
			}else if(withdrawalsCallBackReq.getPay_num().startsWith("MUO")){
				//moneyOutServiceInterface = memberUnitWithdrawalsService;
			}else{
				return ResponseEntity.ok("failure");
			}*/

			if(withdrawalsCallBackReq.getPay_num().startsWith("MO")){
				moneyOutServiceInterface = customerWithdrawalsService;
			}else{
				return ResponseEntity.ok("failure");
			}

			WithdrawalsBean moneyRecordBean = moneyOutServiceInterface.find(withdrawalsCallBackReq.getPay_num());

			if("10000".equals(withdrawalsCallBackReq.getReturn_code())
					&& "success".equals(withdrawalsCallBackReq.getTrade_result())){
				moneyOutServiceInterface.success(moneyRecordBean);
			}else if("10009".equals(withdrawalsCallBackReq.getReturn_code())){
				moneyOutServiceInterface.failure(moneyRecordBean);
			}
			return ResponseEntity.ok("success");
		}

		return ResponseEntity.ok("failure");
	}
}
