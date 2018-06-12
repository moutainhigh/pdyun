package com.rmkj.microcap.common.modules.pay.rongya.http;/**
 * Created by Administrator on 2017/12/29.
 */

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * @author k
 * @create -12-29-12:25
 **/
@HttpApi(value = "http://api.ry-pay.net/")
public interface RongYaPayApi {

    @FormUrlEncoded
    @POST(value = "api.aspx")
    Call<String> rongyaPay(@FieldMap Map<String, Object> parameter);
}
