package com.rmkj.microcap.common.modules.sys.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.cache.CacheFacade;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysDictDao;
import com.rmkj.microcap.common.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by zhangbowen on 2015-8-11.
 * 字典
 */
@Service
@Transactional
public class SysDictService implements IBaseService<SysDictBean> {
    @Resource
    private ISysDictDao sysDictDao;
    /**
     * 单个
     *
     * @param id
     * @return
     */
    @Override
    public SysDictBean get(String id) {
        return sysDictDao.get(id);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public int delete(String id) {
        CacheFacade.delete(DictUtils.CACHE_DICT_MAP);
        return sysDictDao.delete(id);
    }

    /**
     * 新增
     *
     * @param sysDictBean
     */
    @Override
    public int insert(SysDictBean sysDictBean) {
        sysDictBean.preInsert();
        CacheFacade.delete(DictUtils.CACHE_DICT_MAP);
        return sysDictDao.insert(sysDictBean);
    }

    /**
     * 更新
     *
     * @param sysDictBean
     */
    @Override
    public int update(SysDictBean sysDictBean) {
        CacheFacade.delete(DictUtils.CACHE_DICT_MAP);
        sysDictBean.preUpdate();
        return sysDictDao.update(sysDictBean);
    }

    /**
     * 列表
     *
     * @param sysDictBean
     * @return
     */
    @Override
    public List<SysDictBean> queryList(SysDictBean sysDictBean) {
        return sysDictDao.queryList(sysDictBean);
    }
}
