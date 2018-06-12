package com.rmkj.microcap.modules.weChatPublic.service;

import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.trade.entity.Units;
import com.rmkj.microcap.modules.weChatPublic.dao.WeChatPublicArticlesDao;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublicArticle;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by renwp on 2017/3/14.
 */
@Service
public class WeChatPublicArticlesService {

    @Autowired
    private WeChatPublicArticlesDao weChatPublicArticlesDao;

    public List<WeChatPublicArticle> list(WeChatPublicArticle weChatPublicArticle) {
        return weChatPublicArticlesDao.list(weChatPublicArticle);
    }

    public WeChatPublicArticle findById(String id) {
        return weChatPublicArticlesDao.findById(id);
    }

    public void save(WeChatPublicArticle weChatPublicArticle) {
        if(StringUtils.isBlank(weChatPublicArticle.getId())){
            weChatPublicArticle.setId(Utils.uuid());
            if(StringUtils.isBlank(weChatPublicArticle.getUrl())){
                weChatPublicArticle.setUrl(Utils.rootUrl().concat("/weChatPublic/articles/look/").concat(weChatPublicArticle.getId()));
            }
            weChatPublicArticlesDao.add(weChatPublicArticle);
        }else{
            if(StringUtils.isBlank(weChatPublicArticle.getUrl())){
                weChatPublicArticle.setUrl(Utils.rootUrl().concat("/weChatPublic/articles/look/").concat(weChatPublicArticle.getId()));
            }
            weChatPublicArticlesDao.update(weChatPublicArticle);
        }
    }

    public void del(String id) {
        weChatPublicArticlesDao.del(id);
    }
}
