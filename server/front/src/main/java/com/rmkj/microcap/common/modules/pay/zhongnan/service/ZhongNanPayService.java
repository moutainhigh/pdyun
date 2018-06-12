package com.rmkj.microcap.common.modules.pay.zhongnan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.modules.pay.NotifyInterface;
import com.rmkj.microcap.common.modules.pay.zhongnan.entity.ZhongNanNotifyReq;
import com.rmkj.microcap.common.modules.pay.zhongnan.http.ZhongNanApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.utils.DateUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class ZhongNanPayService {

	private final Logger Log = Logger.getLogger(ZhongNanPayService.class);

	@HttpService
	ZhongNanApi zhongNanApi;

	@Value("${zhong_nan_pay_merchant_no}")
	String merchant_no;

	@Value("${zhong_nan_pay_notifyurl}")
	String notifyurl;

	@Value("${zhong_nan_pay_key}")
	String key;

	/**
	 *
	 * @param money
	 * @param serialNo
	 * @param payType 微信扫码：wxpay，支付宝扫码：alipay，网关：gwpay，快捷支付：qkpay，公众号支付：gzhpay，微信H5：wxh5pay,支付宝H5：alih5pay，QQ扫码：qqpay,微信WAP:wxwap
	 * @return
	 */
	public JSONObject createOrder(String money, String serialNo, String payType, Map<String, String> extParams){
		Map<String, String> map = new HashMap<>();

		map.put("merchant_no", merchant_no);
		map.put("total_fee", Math.round(Double.parseDouble(money)*100) + "");
		map.put("pay_num", serialNo);
		map.put("notifyurl", notifyurl);
		map.put("pay_type", payType);

		if(extParams != null)
			map.putAll(extParams);

		map.put("sign", DigestUtils.md5Hex(merchant_no.concat(map.get("total_fee")).concat(DateUtils.getDate("yyyyMMdd")).concat(key)));

		if("qkpay".equals(payType)){
			return JSONObject.parseObject(JSONObject.toJSONString(map));
		}

		try {
			Response<String> execute = zhongNanApi.createWxOrder(map).execute();
			if(execute.isSuccessful()){
				String body = execute.body();
				Log.info(body);
				return JSONObject.parseObject(body);
			}else{
				Log.error(execute.errorBody().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Autowired
	NotifyInterface notifyInterface;

	public ResponseEntity callback(ZhongNanNotifyReq zhongNanNotifyReq) {
		Log.info(JSON.toJSONString(zhongNanNotifyReq));
		String sign = DigestUtils.md5Hex(merchant_no
				.concat(zhongNanNotifyReq.getOut_trade_no())
				.concat(zhongNanNotifyReq.getPay_num())
				.concat(zhongNanNotifyReq.getTotal_fee())
				.concat(key));
		if(sign.equalsIgnoreCase(zhongNanNotifyReq.getSign())){
			if("10000".equals(zhongNanNotifyReq.getReturn_code())
					&& "success".equals(zhongNanNotifyReq.getTrade_result())){
				notifyInterface.success(zhongNanNotifyReq.getPay_num(), zhongNanNotifyReq.getOut_trade_no());
				return ResponseEntity.ok("success");
			}
		}else{
			Log.error("验签失败：" + sign + " " + zhongNanNotifyReq.getSign());
		}
		return ResponseEntity.ok("failure");

	}

//	public ResponseEntity check(ZhongNanWithdrawalsCheckBean zhongNanWithdrawalsCheckBean) {
//		Map<String, String> map = new HashMap<>();
//		map.put("merchant_no", merchant_no);
//
//		try {
//			Response<String> execute = zhongNanApi.createCKOrder(map).execute();
//			if(execute.isSuccessful()){
//				String body = execute.body();
//				Log.info(JSON.toJSONString(zhongNanWithdrawalsCheckBean) + " >> " + body);
//				if("10000".equals(JSONObject.parseObject(body).get("return_code"))){
//					return ResponseEntity.ok("ok");
//				}
//			}else{
//				Log.error(execute.errorBody().string());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return ResponseEntity.ok("not ok");
//	}

}
