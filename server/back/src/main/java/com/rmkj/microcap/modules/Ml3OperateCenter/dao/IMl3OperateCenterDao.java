package com.rmkj.microcap.modules.Ml3OperateCenter.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;

import java.util.List;

/**
* Created by Administrator on 2016-11-17.
*/
@DataSource
public interface IMl3OperateCenterDao extends BaseDao<Ml3OperateCenterBean>{
    void open(Ml3OperateCenterBean entity);//启用

    void close(Ml3OperateCenterBean entity);//停用
    List<Ml3OperateCenterBean>  OcList();

    /**
     * 选择性查询
     * @param ml3OperateCenterBean
     * @return
     */
    Ml3AgentBean getOperateCenterSelective(Ml3OperateCenterBean ml3OperateCenterBean);
}
