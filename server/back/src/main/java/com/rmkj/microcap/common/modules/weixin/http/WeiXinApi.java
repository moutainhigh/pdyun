package com.rmkj.microcap.common.modules.weixin.http;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import com.rmkj.microcap.common.modules.weixin.bean.*;
import com.rmkj.microcap.common.modules.weixin.http.interceptor.WeiXinInterceptor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhangbowen on 2016/6/7.
 */
@HttpApi(value = "WEI_XIN_BASE_URL", interceptor = {WeiXinInterceptor.class})
public interface WeiXinApi {
    /**
     * 获得token
     *
     * @return
     */
    @GET("token")
    Call<ResponseToken> getToken(@Query("grant_type") String grant_type, @Query("appid") String appid, @Query("secret") String secret);

    /**
     * 创建按钮
     * @return
     */
    @POST("menu/create")
    Call<WeiXinResult> createMenu(@Body CreateMenuBean menuBean);
    /**
     * 清空按钮
     * @return
     */
    @GET("menu/delete")
    Call<WeiXinResult> clearMenu();

    @GET("menu/get")
    Call<String> get();

    @POST("menu/create")
    Call<String> create(@Body JSONObject body);

    @GET("menu/delete")
    Call<String> delete();

    @POST("material/batchget_material")
    Call<String> queryMedias(@Body QueryMediasBean queryMediasBean);

    @POST("material/batchget_material")
    Call<String> queryTempMedias(@Body QueryMediasBean queryMediasBean);

}
