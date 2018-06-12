package com.rmkj.microcap.common.modules.pay.zhongnan.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@HttpApi(value = "http://api.zhongnanpay.com:3022/hmpay/online/")
public interface ZhongNanApi {

	@FormUrlEncoded
	@POST(value = "createWxOrder.do")
	Call<String> createWxOrder(@FieldMap Map<String, String> params);

	/**
	 * 实名认证接口
	 * @param params
	 * @return
	 */
	@FormUrlEncoded
	@POST(value = "createCKOrder.do")
	Call<String> createCKOrder(@FieldMap Map<String, String> params);

}
