package com.rmkj.microcap.common.modules.trademarket.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renwp on 2016/10/13.
 */

@HttpApi(value = "http://data.ronmei.com:8888/")
public interface GaoLastestApi {

    @GET("Price")
    Call<String> getPrice(@Query("code") String code);

}
