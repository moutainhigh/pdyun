package com.rmkj.microcap.common.modules.money.out.rongya.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/2.
 */
@HttpApi(value = "http://api.ry-pay.net/")
public interface RongYaApi {

    @FormUrlEncoded
    @POST(value = "Settlement.aspx")
    Call<String> moneyOut(@FieldMap Map<String, Object> parameter);

}
