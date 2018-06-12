package com.rmkj.microcap.common.modules.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.weixin.bean.*;
import com.rmkj.microcap.common.modules.weixin.error.WeiXinError;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinApi;
import com.rmkj.microcap.common.modules.weixin.http.WeiXinMessageCustomSendApi;
import com.rmkj.microcap.common.modules.weixin.http.interceptor.WeiXinInterceptor;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.weChatPublic.dao.WeChatPublicArticlesDao;
import com.rmkj.microcap.modules.weChatPublic.dao.WeChatPublicDao;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublic;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublicArticle2;
import com.rmkj.microcap.modules.weChatPublic.entity.WechatMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangbowen on 2016/6/7.
 */
@Service
public class WeiXinService {

    private final Logger Log = Logger.getLogger(WeiXinService.class);

    @HttpService
    private WeiXinApi weiXinApi;

    @Autowired
    private WeChatPublicDao weChatPublicDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private WeChatPublicArticlesDao weChatPublicArticlesDao;

    @HttpService
    private WeiXinMessageCustomSendApi weiXinMessageCustomSendApi;

    public String initToken(WeChatPublic weChatPublic){
        //设置请求参数
        if(weChatPublic == null){
            return null;
        }
        String accessToken = null;
        int expireSeconds = 0;
        Date now = new Date();
        if(StringUtils.isNotBlank(weChatPublic.getAccessToken())
                && now.before(weChatPublic.getAccessTokenExpireTime())){
            accessToken = weChatPublic.getAccessToken();
            expireSeconds = (int) (weChatPublic.getAccessTokenExpireTime().getTime()/1000 - now.getTime()/1000);
        }else{
            try {
                Response<ResponseToken> response = weiXinApi.getToken("client_credential", weChatPublic.getAppId(), weChatPublic.getSecret()).execute();
                if (response.isSuccessful()) {
                    ResponseToken responseTokenBean = response.body();
                    if (!responseTokenBean.isSuccessFul()) {
                        throw new WeiXinError(responseTokenBean.getErrmsg());
                    }
                    accessToken = responseTokenBean.getAccess_token();
                    expireSeconds = responseTokenBean.getExpires_in();

                    weChatPublic.setAccessToken(accessToken);
                    Calendar instance = Calendar.getInstance();
                    instance.add(Calendar.SECOND, expireSeconds);
                    weChatPublic.setAccessTokenExpireTime(instance.getTime());
                    weChatPublicDao.updateAccessToken(weChatPublic);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(expireSeconds != 0){
            CacheFacade.set(WeiXinInterceptor.appKey(), accessToken, expireSeconds);
        }

        return accessToken;
    }

    public String initToken() {
        if(ProjectConstants.PRO_DEBUG){
            throw new WeiXinError("本地缓存模式不建议操作微信");
        }
        String cacheToken = CacheFacade.getObject(WeiXinInterceptor.appKey());
        if (cacheToken != null) {
            return cacheToken;
        }
        //设置请求参数
        WeChatPublic weChatPublic =  weChatPublicDao.findByAppId(WeiXinInterceptor.appId());
        return initToken(weChatPublic);
    }
    //添加菜单
    public void createMenu(CreateMenuBean createMenuBean) {
        initToken();
        try {
            Response<WeiXinResult> resultResponse = weiXinApi.createMenu(createMenuBean).execute();
            if (resultResponse.isSuccessful()) {
                WeiXinResult result = resultResponse.body();
                if (!result.isSuccessFul()) {
                    throw new WeiXinError(result.getErrmsg());
                }
            } else {
                throw new WeiXinError(resultResponse.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空按钮
     */
    public void clearMenu() {
        initToken();
        try {
            Response<WeiXinResult> resultResponse = weiXinApi.clearMenu().execute();
            if (resultResponse.isSuccessful()) {
                WeiXinResult result = resultResponse.body();
                if (!result.isSuccessFul()) {
                    throw new WeiXinError(result.getErrmsg());
                }
            } else {
                throw new WeiXinError(resultResponse.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public String queryMedias(String type, int offset, int count) {
        QueryMediasBean queryMediasBean = new QueryMediasBean();
        queryMediasBean.setType(type);
        queryMediasBean.setOffset(offset+"");
        queryMediasBean.setCount(count+"");
        try {
            Response<String> execute = weiXinApi.queryMedias(queryMediasBean).execute();
            if(execute.isSuccessful()){
                return execute.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param type
     * @param offset
     * @param count
     * @return
     */
    public String queryTempMedias(String type, int offset, int count) {
        QueryMediasBean queryMediasBean = new QueryMediasBean();
        queryMediasBean.setType(type);
        queryMediasBean.setOffset(offset+"");
        queryMediasBean.setCount(count+"");
        try {
            Response<String> execute = weiXinApi.queryTempMedias(queryMediasBean).execute();
            if(execute.isSuccessful()){
                return execute.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean sendMessage(String accessToken, CustomMessage customMessage){
        String json = JSONObject.toJSONString(customMessage);
        Log.info(json);
        Call<String> call = weiXinMessageCustomSendApi.messageCustomSend(accessToken, customMessage);
        try {
            Response<String> execute = call.execute();
            if(execute.isSuccessful()){
                String result = execute.body();
                // {"errcode":40003,"errmsg":"invalid openid hint: [n3I63a0099ge21]"}
                if(!result.startsWith("{\"errcode\":0,")){
                    Log.error("sendMessage: ".concat(result));
                }
                return true;
            }else{
                Log.error(execute.errorBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param type
     * @param userId
     * @param params
     */
    public void sendMessage(String type, String userId, String... params){
        if(ProjectConstants.PRO_DEBUG){
            return ;
        }
        try{
            WechatMessage wechatMessage = weChatPublicDao.findByWechatMessage(type);
            if(wechatMessage != null) {
                User userById = userDao.findUserById(userId);
                CustomMessage customMessage = new CustomMessage();
                WeChatPublic weChatPublic = weChatPublicDao.findById(userById.getWechatPublicId());
                String cacheToken = CacheFacade.getObject(WeiXinInterceptor.appKey(weChatPublic.getAppId()));
                if (cacheToken == null) {
                    WeiXinInterceptor.setAppId(weChatPublic.getAppId());
                    cacheToken = initToken(weChatPublic);
                }
                if(StringUtils.isNotBlank(cacheToken)){
                    customMessage.setMsgtype(wechatMessage.getSendType());
                    customMessage.setTouser(userById.getOpenId());

                    if("text".equals(customMessage.getMsgtype())){
                        customMessage.setText(new CustomMessageText());
                        customMessage.getText().setContent(Utils.formatStr(wechatMessage.getContent(), params));
                    }else if("news".equals(customMessage.getMsgtype())){
                        customMessage.setNews(new CustomMessageNews());
                        customMessage.getNews().setArticles(new ArrayList<>());
                        WeChatPublicArticle2 weChatPublicArticle2 = weChatPublicArticlesDao.findById2(wechatMessage.getContent());
                        customMessage.getNews().getArticles().add(weChatPublicArticle2);
                    }
                    sendMessage(cacheToken, customMessage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
