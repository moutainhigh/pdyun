package com.rmkj.microcap.common.modules.pay.ronghe.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/12.
 */
@HttpApi("http://bank.fjelt.com/pay/")
public interface RongHePayApi {

    @FormUrlEncoded
    @POST(value = "Rest")
    Call<String> fastPay(@FieldMap Map<String, Object> parameter);

}
