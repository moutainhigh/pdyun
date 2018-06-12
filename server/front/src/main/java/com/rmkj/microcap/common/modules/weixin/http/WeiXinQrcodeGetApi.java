package com.rmkj.microcap.common.modules.weixin.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import com.rmkj.microcap.common.modules.weixin.bean.WeiXinQrcodeCreateReq;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by renwenpeng on 2016/7/5.
 */
@HttpApi("WEI_XIN_BASE_URL")
public interface WeiXinQrcodeGetApi {


    @POST("cgi-bin/qrcode/create")
    Call<String> create(@Query("access_token") String accessToken, @Body WeiXinQrcodeCreateReq weiXinQrcodeCreateReq);

    /**
     * 下载二维码图片
     * @param ticket
     * @return
     */
    @GET("cgi-bin/showqrcode")
    Call<byte[]> getQrcodeImg(@Query("ticket") String ticket);
}
