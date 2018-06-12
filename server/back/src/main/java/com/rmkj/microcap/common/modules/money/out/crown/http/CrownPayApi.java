package com.rmkj.microcap.common.modules.money.out.crown.http;/**
 * Created by Administrator on 2017/11/21.
 */

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * @author k
 * @create -11-21-14:02
 **/
@HttpApi(value = "http://api.8992vip.com/guanjun/df/")
public interface CrownPayApi {

    /**
     * 皇冠 出金接口
     * @param parameter
     * @return
     */
    @FormUrlEncoded
    @POST(value = "trans")
    Call<String> crownMoneyout(@FieldMap Map<String, Object> parameter);
}
