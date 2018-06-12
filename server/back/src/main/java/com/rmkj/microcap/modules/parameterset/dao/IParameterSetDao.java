package com.rmkj.microcap.modules.parameterset.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.parameterset.entity.ParameterSetBean;

/**
* Created by Administrator on 2016-12-16.
*/
@DataSource
public interface IParameterSetDao extends BaseDao<ParameterSetBean>{
    String getQrCodeMenuUrl();
}
