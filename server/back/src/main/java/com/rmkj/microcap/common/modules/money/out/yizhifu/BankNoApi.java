package com.rmkj.microcap.common.modules.money.out.yizhifu;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.GET;

import java.io.InputStream;

/**
 * Created by renwp on 2017/1/4.
 */
@HttpApi("http://www.cmbc.com.cn/")
public interface BankNoApi {

    @GET("download/lianhang.txt")
    Call<InputStream> lianhang();
}
