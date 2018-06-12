package com.rmkj.microcap.modules.parameterSet.dao;

import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.parameterSet.entity.ParameterSet;

@DataSource
public interface ParameterSetDao {
    int deleteByPrimaryKey(String id);

    int insert(ParameterSet record);

    int insertSelective(ParameterSet record);

    ParameterSet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ParameterSet record);

    int updateByPrimaryKey(ParameterSet record);

    /**
     * TODO 查询参数设置表全部数据
     * @return
     */
    ParameterSet findParameterSet();

    String getQrCodeMenuUrl();
}