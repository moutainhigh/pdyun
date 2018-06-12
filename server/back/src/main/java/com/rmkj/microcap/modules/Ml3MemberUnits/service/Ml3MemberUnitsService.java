package com.rmkj.microcap.modules.Ml3MemberUnits.service;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.modules.Ml3MemberUnits.dao.IMl3MemberUnitsDao;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.ml3MemberUnitisMoneyChange.dao.Ml3MemberUnitisMoneyChangeDao;
import com.rmkj.microcap.modules.ml3MemberUnitisMoneyChange.entity.Ml3MemberUnitsMoneyChange;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* Created by Administrator on 2016-11-17.
*/
@Service
@Transactional
public class Ml3MemberUnitsService implements IBaseService<Ml3MemberUnitsBean> {
    @Autowired
    private IMl3MemberUnitsDao ml3MemberUnitsDao;

    @Autowired
    private Ml3MemberUnitisMoneyChangeDao ml3MemberUnitisMoneyChangeDao;

    @Override
    public Ml3MemberUnitsBean get(String id){
        return ml3MemberUnitsDao.get(id);
    }
    @Override
    public int update(Ml3MemberUnitsBean ml3MemberUnitsBean){
        ml3MemberUnitsBean.preUpdate();
        return ml3MemberUnitsDao.update(ml3MemberUnitsBean);
    }
    @Override
    public int delete(String id){
        return ml3MemberUnitsDao.delete(id);
    }
    @Override
    public int insert(Ml3MemberUnitsBean ml3MemberUnitsBean){
        ml3MemberUnitsBean.preInsert();
        ml3MemberUnitsBean.setStatus(0);
        return ml3MemberUnitsDao.insert(ml3MemberUnitsBean);
    }
    @Override
    public List<Ml3MemberUnitsBean> queryList(Ml3MemberUnitsBean ml3MemberUnitsBean){
        return ml3MemberUnitsDao.queryList(ml3MemberUnitsBean);
    }
    //启用
    public void open(Ml3MemberUnitsBean entity) {
        ml3MemberUnitsDao.open(entity);
    }
    //停用
    public void close(Ml3MemberUnitsBean entity) {
        ml3MemberUnitsDao.close(entity);
    }
    public List<Ml3MemberUnitsBean> muList(){return ml3MemberUnitsDao.muList();}
    public List<Ml3MemberUnitsBean> memberUnitsList(Ml3MemberUnitsBean entity){
        return ml3MemberUnitsDao.memberUnitsList(entity);
    }

    public ExecuteResult addUnitsBondMoney(Ml3MemberUnitsBean ml3MemberUnitsBean){
        //查询追加金额的会员单位
        Ml3MemberUnitsBean unitsBean = ml3MemberUnitsDao.get(ml3MemberUnitsBean.getId());
        if(ml3MemberUnitsDao.addUnitsBondMoney(ml3MemberUnitsBean) == 1){

            Ml3MemberUnitsMoneyChange ml3MemberUnitsMoneyChange = new Ml3MemberUnitsMoneyChange();
            ml3MemberUnitsMoneyChange.preInsert();
            ml3MemberUnitsMoneyChange.setUnitsId(unitsBean.getId());
            ml3MemberUnitsMoneyChange.setUnitsName(unitsBean.getName());
            ml3MemberUnitsMoneyChange.setType(Constants.UNITS_MONEY_CHANGE_TYPE.ADD_BOND_MONEY); //2
            ml3MemberUnitsMoneyChange.setDifMoney(ml3MemberUnitsBean.getAddUnitsBondMoney());
            ml3MemberUnitsMoneyChange.setBeforeMoney(unitsBean.getMoney());
            ml3MemberUnitsMoneyChange.setAfterMoney(unitsBean.getMoney().add(ml3MemberUnitsBean.getAddUnitsBondMoney()));
            ml3MemberUnitsMoneyChange.setCreateTime(new Date());
            ml3MemberUnitisMoneyChangeDao.insert(ml3MemberUnitsMoneyChange);
            return new ExecuteResult(StatusCode.OK,"追加保证金成功");
        }else{
            return new ExecuteResult(StatusCode.FAILED, "追加保证金失败");
        }
    }

    public Map<String, Object> findMl3MemberUnits(String centerId){
        Ml3MemberUnitsBean units = new Ml3MemberUnitsBean();
        units.setCenterId(centerId);
        Map<String, Object> result = new HashedMap();
        result.put("unitsList", ml3MemberUnitsDao.queryList(units));
        return result;
    }
}
