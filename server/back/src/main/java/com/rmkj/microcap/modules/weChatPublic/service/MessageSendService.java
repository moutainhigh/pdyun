package com.rmkj.microcap.modules.weChatPublic.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.weixin.bean.CustomMessage;
import com.rmkj.microcap.common.modules.weixin.bean.CustomMessageNews;
import com.rmkj.microcap.common.modules.weixin.http.interceptor.WeiXinInterceptor;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.weChatPublic.dao.WeChatPublicArticlesDao;
import com.rmkj.microcap.modules.weChatPublic.dao.WeChatPublicDao;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublic;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublicArticle;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublicArticle2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renwp on 2017/3/14.
 */
@Service
public class MessageSendService {

    @Autowired
    private WeChatPublicDao weChatPublicDao;

    @Autowired
    private WeChatPublicArticlesDao weChatPublicArticlesDao;

    @Autowired
    private WeiXinService weiXinService;

    public String toAll(String wechatPublicId, String type, String content) {
        if(StringUtils.isBlank(wechatPublicId)){
            return "请选择公众号！";
        }

        final long[] count = {0};
        if(ProjectConstants.WEI_XIN_MESSAGE_CUSTOM_SEND_TYPE.TU_WEN.equals(type)){

            List<WeChatPublic> list = weChatPublicDao.list(null);
            WeChatPublicArticle2 it = weChatPublicArticlesDao.findById2(content);
            if(it != null){
                list.forEach(weChatPublic -> {
                    if(wechatPublicId.equals(weChatPublic.getId()) ){
                        CustomMessage customMessage = new CustomMessage();
                        customMessage.setMsgtype("news");
                        customMessage.setNews(new CustomMessageNews());
                        customMessage.getNews().setArticles(new ArrayList<>());

                        List<WeChatPublicArticle2> articles = customMessage.getNews().getArticles();
                        articles.add(it);

                        List<UserBean> users = weChatPublicDao.findWeChatPublicUsers(weChatPublic.getId());
                        users.forEach(user -> {
                            String cacheToken = CacheFacade.getObject(WeiXinInterceptor.appKey(weChatPublic.getAppId()));
                            if (cacheToken == null) {
                                WeiXinInterceptor.setAppId(weChatPublic.getAppId());
                                cacheToken = weiXinService.initToken(weChatPublic);
                            }
                            customMessage.setTouser(user.getOpenId());
                            weiXinService.sendMessage(cacheToken, customMessage);
                            count[0]++;
                        });
                    }
                });
            }
        }

        return "成功推送"+count[0]+"条消息！";
    }
}
