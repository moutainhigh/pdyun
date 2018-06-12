package com.rmkj.microcap.common.modules.version.service;


import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.version.dao.IVersionDao;
import com.rmkj.microcap.common.modules.version.entity.VersionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangbowen on 2015-12-25.
 */
@Service
@Transactional
public class VersionService implements IBaseService<VersionBean> {
    @Autowired
    private IVersionDao versionDao;

    @Override
    public VersionBean get(String id) {
        return versionDao.get(id);
    }

    @Override
    public int delete(String id) {
        return versionDao.delete(id);
    }

    @Override
    public int insert(VersionBean versionBean) {
        versionBean.preInsert();
        return versionDao.insert(versionBean);
    }

    @Override
    public int update(VersionBean versionBean) {
        versionBean.preUpdate();
        return versionDao.update(versionBean);
    }

    @Override
    public List<VersionBean> queryList(VersionBean versionBean) {
        return versionDao.queryList(versionBean);
    }
}
