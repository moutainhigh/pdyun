package com.rmkj.microcap.modules.contract.dao;

import com.rmkj.microcap.common.base.BaseDao;
import com.rmkj.microcap.common.bean.annotation.DataSource;
import com.rmkj.microcap.modules.contract.entity.ContractBean;

/**
* Created by Administrator on 2016-10-17.
*/
@DataSource
public interface IContractDao extends BaseDao<ContractBean>{
    void open(ContractBean entity);//开启合约

    void close(ContractBean entity);//关闭合约
}
