package com.rmkj.microcap.modules.weChatPublic.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublic;
import com.rmkj.microcap.modules.weChatPublic.entity.WechatMessage;

import java.util.List;

/**
 * Created by renwp on 2017/2/11.
 */
@DataSource
public interface WeChatPublicDao {
    int insert(WeChatPublic weChatPublic);

    int update(WeChatPublic weChatPublic);

    List<WeChatPublic> list(WeChatPublic weChatPublic);

    WeChatPublic findById(String id);

    int del(String id);

    WeChatPublic findByAppId(String s);

    List<UserBean> findWeChatPublicUsers(String wechatPublicId);

    WechatMessage findByWechatMessage(String type);

    int updateAccessToken(WeChatPublic weChatPublic);
}
