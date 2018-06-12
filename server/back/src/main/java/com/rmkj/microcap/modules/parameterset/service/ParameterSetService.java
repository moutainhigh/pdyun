package com.rmkj.microcap.modules.parameterset.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.parameterset.dao.IParameterSetDao;
import com.rmkj.microcap.modules.parameterset.entity.ParameterSetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-12-16.
*/
@Service
@Transactional
public class ParameterSetService implements IBaseService<ParameterSetBean> {
    @Autowired
    private IParameterSetDao parameterSetDao;

    @Override
    public ParameterSetBean get(String id){
        return parameterSetDao.get(id);
    }
    @Override
    public int update(ParameterSetBean parameterSetBean){
        parameterSetBean.preUpdate();
        return parameterSetDao.update(parameterSetBean);
    }
    @Override
    public int delete(String id){
        return parameterSetDao.delete(id);
    }
    @Override
    public int insert(ParameterSetBean parameterSetBean){
        parameterSetBean.preInsert();
        return parameterSetDao.insert(parameterSetBean);
    }
    @Override
    public List<ParameterSetBean> queryList(ParameterSetBean parameterSetBean){
        return parameterSetDao.queryList(parameterSetBean);
    }
    public String getQrCodeMenuUrl(){
        return parameterSetDao.getQrCodeMenuUrl();
    }
}
