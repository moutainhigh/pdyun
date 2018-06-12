package com.rmkj.microcap.common.modules.money.out.weipeng.http;/**
 * Created by Administrator on 2017/3/7.
 */

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * TODO 威鹏代付接口
 * @author k
 * @create -03-07-11:38
 **/
@HttpApi(value = "http://139.224.61.115:3030/hmpay/online/")
public interface WeiPengApi {

    /**
     * TODO 威鹏代付返回信息接口
     * @param result
     * @return
     */
    @FormUrlEncoded
    @POST(value = "wpdfpay.do")
    Call<String> getWeiPengDaiPayInfo(@FieldMap Map<String, String> result);

    /**
     * 威鹏代付 查询代付结果接口
     */
    @FormUrlEncoded
    @POST(value = "searchedfStatus.do")
    Call<String> selWeiPengDaiPayResult(@Field("merchant_no") String merchant_no, @Field("orderid") String orderid);
}
