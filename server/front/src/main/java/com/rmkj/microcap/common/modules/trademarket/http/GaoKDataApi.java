package com.rmkj.microcap.common.modules.trademarket.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renwp on 2016/10/13.
 */

@HttpApi(value = "http://chart.ronmei.com/")
public interface GaoKDataApi {

    @GET("chart/chart.php?noLast=1")
    Call<String> getChart(@Query("Code") String Code, @Query("interval") String interval, @Query("rows") String row);

}
