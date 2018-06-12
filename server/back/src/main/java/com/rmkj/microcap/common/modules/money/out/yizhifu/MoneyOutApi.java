package com.rmkj.microcap.common.modules.money.out.yizhifu;

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by renwp on 2017/1/4.
 */
@HttpApi(value = "money_out_yizhifu_url")
public interface MoneyOutApi {

    /**
     * 查询余额接口
     * @param v_min
     * @param v_mac
     * @return
     */
    @GET("merchant/virement/mer_payment_balance_check.jsp")
    Call<byte[]> queryOverMoney(@Query("v_mid") String v_min, @Query("v_mac") String v_mac);

    /**
     * 批量代付
     * @param v_mid
     * @param v_data
     * @param v_mac
     * @param v_version
     * @return
     */
    @FormUrlEncoded
    @POST("merchant/virement/mer_payment_submit_utf8.jsp")
    Call<byte[]> batchOut(@Field("v_mid") String v_mid, @Field("v_data") String v_data,
                          @Field("v_mac") String v_mac, @Field("v_version") String v_version);

    /**
     * 单笔代付查询结果
     * @param v_min
     * @param v_data
     * @param v_mac
     * @param v_version
     * @return
     */
    @GET("merchant/virement/mer_payment_status_utf8.jsp")
    Call<byte[]> queryResult(@Query("v_mid") String v_min, @Query("v_data") String v_data, @Query("v_mac") String v_mac, @Query("v_version") String v_version);
}
