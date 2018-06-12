package com.rmkj.microcap.common.modules.money.out.guofu.http;/**
 * Created by Administrator on 2017/10/18.
 */

import com.rmkj.microcap.common.modules.retrofit.annotation.HttpApi;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author k
 * @create -10-18-17:34
 **/
@HttpApi(value = "http://settle.posp168.com")
public interface GuoFuApi {

    @POST(value = "/virtPay.do")
    Call<String> virtPay(@Url String str);
}
