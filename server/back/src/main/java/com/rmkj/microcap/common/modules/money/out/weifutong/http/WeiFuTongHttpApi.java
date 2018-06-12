package com.rmkj.microcap.common.modules.money.out.weifutong.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by renwp on 2017/3/20.
 */
@HttpApi("wei_fu_tong_url")
public interface WeiFuTongHttpApi {

    @POST("ProcessServlet")
    Call<String> processServlet(@Body String xml);

}
