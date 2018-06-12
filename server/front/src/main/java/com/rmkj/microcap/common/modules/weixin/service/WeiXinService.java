package com.rmkj.microcap.common.modules.weixin.service;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.CacheKey;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.weixin.bean.*;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinApi;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.weixin.error.WeiXinError;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinPageAuthApi;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinQrcodeGetApi;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by zhangbowen on 2016/6/7.
 */
@Service
public class WeiXinService {

    @HttpService
    private WeiXinPageAuthApi weiXinPageAuthApi;

    @HttpService
    private WeiXinApi weiXinApi;

    @HttpService
    private WeiXinQrcodeGetApi weiXinQrcodeGetApi;

    protected org.apache.log4j.Logger logger = Logger.getLogger(getClass());

    public void initToken() {
        if(ProjectConstants.PRO_DEBUG){
            throw new WeiXinError("本地缓存模式不建议操作微信");
        }
        String cacheToken = CacheFacade.getObject(CacheKey.WeiXin.TOKEN);
        if (cacheToken != null) {
            return;
        }
        //设置请求参数
        try {
            Response<ResponseToken> response = weiXinApi.getToken("client_credential", ProjectConstants.WEI_XIN_APP_ID, ProjectConstants.WEI_XIN_SECRET).execute();
            if (response.isSuccessful()) {
                ResponseToken responseTokenBean = response.body();
                if (!responseTokenBean.isSuccessFul()) {
                    throw new WeiXinError(responseTokenBean.getErrmsg());
                }
                CacheFacade.set(CacheKey.WeiXin.TOKEN, responseTokenBean.getAccess_token(), responseTokenBean.getExpires_in());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTicket(){
        if(ProjectConstants.PRO_DEBUG){
            throw new WeiXinError("本地缓存模式不建议操作微信");
        }
        String cacheToken = CacheFacade.getObject(CacheKey.WeiXin.TICKET);
        if(cacheToken != null){
            return  cacheToken;
        }else {
            initToken();
            //设置请求参数
            try {
                Response<TicketBean> response = weiXinApi.getTicket("jsapi").execute();
                if (response.isSuccessful()) {
                    TicketBean ticketBean = response.body();
                    if (!ticketBean.isSuccessFul()) {
                        throw new WeiXinError(ticketBean.getErrmsg());
                    }
                    CacheFacade.set(CacheKey.WeiXin.TICKET, ticketBean.getTicket(), ticketBean.getExpires_in());
                    return ticketBean.getTicket();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取永久二维码 带参数
     * @param scene_str
     * @return
     */
    public String qrcodeCreate(String scene_str){
        WeiXinQrcodeCreateReq weiXinQrcodeCreateReq = new WeiXinQrcodeCreateReq();
        weiXinQrcodeCreateReq.setAction_name("QR_LIMIT_STR_SCENE");
        weiXinQrcodeCreateReq.setAction_info(new WeiXinQrcodeCreateReq.ActionInfoBean());
        weiXinQrcodeCreateReq.getAction_info().setScene(new WeiXinQrcodeCreateReq.ActionInfoBean.SceneBean());
        weiXinQrcodeCreateReq.getAction_info().getScene().setScene_str(scene_str);
        try {
            initToken();
            Response<String> execute = weiXinQrcodeGetApi.create(CacheFacade.getObject(CacheKey.WeiXin.TOKEN), weiXinQrcodeCreateReq).execute();
            if(execute.isSuccessful()){
                WeiXinQrcodeCreateResp w = JSON.parseObject(execute.body(), WeiXinQrcodeCreateResp.class);
                return w.getTicket();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据openid获取用户信息
     * @param openId
     * @return
     */
    public WeiXinUserInfo userInfo(String openId){
        try {
            initToken();
            Response<WeiXinUserInfo> userInfoExecute = weiXinApi.userInfo(openId, "zh_CN").execute();
            if(userInfoExecute.isSuccessful()){
                return userInfoExecute.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
