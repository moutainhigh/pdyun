package com.rmkj.microcap.common.modules.weixin.http;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by songwei on 2016/7/12.
 */
@HttpApi("wei_xin_get_qrcode")
public interface WeiXinQrcodeGetApi {
    /**
     * 下载二维码图片
     * @param ticket
     * @return
     */
    @GET("cgi-bin/showqrcode")
    Call<byte[]> getQrcodeImg(@Query("ticket") String ticket);
}

