package com.rmkj.microcap.common.modules.sys.dao;


import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;

import java.util.List;

/**
 * Created by zhangbowen on 2015-8-11.
 */
@DataSource
public interface ISysDictDao extends BaseDao<SysDictBean> {
    List<SysDictBean> findList(SysDictBean dict);

}
