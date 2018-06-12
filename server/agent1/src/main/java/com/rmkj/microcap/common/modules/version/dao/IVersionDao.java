package com.rmkj.microcap.common.modules.version.dao;


import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.version.entity.VersionBean;

/**
 * Created by zhangbowen on 2015-12-25.
 */
@DataSource
public interface IVersionDao extends BaseDao<VersionBean> {
}
