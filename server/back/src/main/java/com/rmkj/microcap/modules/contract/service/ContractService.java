package com.rmkj.microcap.modules.contract.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.contract.dao.IContractDao;
import com.rmkj.microcap.modules.contract.entity.ContractBean;
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
public class ContractService implements IBaseService<ContractBean> {
    @Autowired
    private IContractDao contractDao;

    @Override
    public ContractBean get(String id){
        return contractDao.get(id);
    }
    @Override
    public int update(ContractBean contractBean){
        contractBean.setUpdateTime(new Date());
        return contractDao.update(contractBean);
    }
    @Override
    public int delete(String id){
        return contractDao.delete(id);
    }
    @Override
    public int insert(ContractBean contractBean){
        contractBean.preInsert();
        return contractDao.insert(contractBean);
    }
    @Override
    public List<ContractBean> queryList(ContractBean contractBean){
        return contractDao.queryList(contractBean);
    }
    //开启合约
    public void open(ContractBean entity) {
        contractDao.open(entity);
    }
    //关闭合约
    public void close(ContractBean entity) {
        contractDao.close(entity);
    }
}
