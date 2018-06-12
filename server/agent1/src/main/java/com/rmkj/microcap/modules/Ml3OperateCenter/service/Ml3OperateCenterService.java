package com.rmkj.microcap.modules.Ml3OperateCenter.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3OperateCenter.dao.IMl3OperateCenterDao;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3OperateCenterService implements IBaseService<Ml3OperateCenterBean> {
    @Autowired
    private IMl3OperateCenterDao ml3OperateCenterDao;

    @Override
    public Ml3OperateCenterBean get(String id){
        return ml3OperateCenterDao.get(id);
    }
    @Override
    public int update(Ml3OperateCenterBean ml3OperateCenterBean){
        ml3OperateCenterBean.preUpdate();
        return ml3OperateCenterDao.update(ml3OperateCenterBean);
    }
    @Override
    public int delete(String id){
        return ml3OperateCenterDao.delete(id);
    }
    @Override
    public int insert(Ml3OperateCenterBean ml3OperateCenterBean){
        ml3OperateCenterBean.preInsert();
        String uuid = Utils.uuid();
        ml3OperateCenterBean.setId(uuid);
        return ml3OperateCenterDao.insert(ml3OperateCenterBean);
    }
    @Override
    public List<Ml3OperateCenterBean> queryList(Ml3OperateCenterBean ml3OperateCenterBean){
        return ml3OperateCenterDao.queryList(ml3OperateCenterBean);
    }
    //启用
    public void open(Ml3OperateCenterBean entity) {
        ml3OperateCenterDao.open(entity);
    }
    //停用
    public void close(Ml3OperateCenterBean entity) {
        ml3OperateCenterDao.close(entity);
    }
    public List<Ml3OperateCenterBean>  OcList(){ return ml3OperateCenterDao.OcList(); }
}
