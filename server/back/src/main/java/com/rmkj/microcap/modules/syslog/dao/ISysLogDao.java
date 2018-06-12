package com.rmkj.microcap.modules.syslog.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;

/**
* Created by Administrator on 2016-10-21.
*/
@DataSource
public interface ISysLogDao extends BaseDao<SysLogBean> {
}
