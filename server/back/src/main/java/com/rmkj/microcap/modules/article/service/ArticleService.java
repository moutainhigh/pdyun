package com.rmkj.microcap.modules.article.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.article.dao.IArticleDao;
import com.rmkj.microcap.modules.article.entity.ArticleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class ArticleService implements IBaseService<ArticleBean> {
    @Autowired
    private IArticleDao articleDao;

    @Override
    public ArticleBean get(String id){
        return articleDao.get(id);
    }
    @Override
    public int update(ArticleBean articleBean){
        articleBean.preUpdate();
        SysUserBean user = UserUtils.getUser();
        articleBean.setUpdateTime(new Date());
        return articleDao.update(articleBean);
    }
    @Override
    public int delete(String id){
        return articleDao.delete(id);
    }
    @Override
    public int insert(ArticleBean articleBean){
        articleBean.preInsert();
        return articleDao.insert(articleBean);
    }
    @Override
    public List<ArticleBean> queryList(ArticleBean articleBean){
        return articleDao.queryList(articleBean);
    }
}
