package com.rmkj.microcap.common.modules.money.out.zhongnan.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */
@HttpApi(value = "http://api.zhongnanpay.com/hmpay/online/")
public interface ZhongNanWithdrawalsApi {

	@FormUrlEncoded
	@POST(value = "createDFOrder.do")
	Call<String> createDFOrder(@FieldMap Map<String, String> params);

	@FormUrlEncoded
	@POST(value = "queryDFOrder.do")
	Call<String> queryDFOrder(@Field("merchant_no") String merchant_no, @Field("out_trade_no") String out_trade_no, @Field("sign") String sign);

}
