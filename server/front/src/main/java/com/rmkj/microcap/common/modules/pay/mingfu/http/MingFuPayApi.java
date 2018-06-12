package com.rmkj.microcap.common.modules.pay.mingfu.http;/**
 * Created by Administrator on 2018/1/18.
 */

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * @author k
 * @create -01-18-9:26
 **/
@HttpApi(value = "http://39.108.235.176/trans/gateway/tran/")
public interface MingFuPayApi {

    @POST(value = "quickPay")
    Call<String> mingfuQuickPay(@Body Map<String, Object> parameter);

    @POST(value = "quickPayConfirm")
    Call<String> quickPayConfirm(@Body Map<String, Object> parameter);
}
