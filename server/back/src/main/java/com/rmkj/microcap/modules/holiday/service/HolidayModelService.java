package com.rmkj.microcap.modules.holiday.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.modules.holiday.dao.IHolidayModelDao;
import com.rmkj.microcap.modules.holiday.entity.HolidayModelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class HolidayModelService implements IBaseService<HolidayModelBean> {
    @Autowired
    private IHolidayModelDao holidayModelDao;

    @Override
    public HolidayModelBean get(String id){
        return holidayModelDao.get(id);
    }
    @Override
    public int update(HolidayModelBean holidayModelBean){
        holidayModelBean.preUpdate();
        return holidayModelDao.update(holidayModelBean);
    }
    @Override
    public int delete(String id){
        return holidayModelDao.delete(id);
    }
    @Override
    public int insert(HolidayModelBean holidayModelBean){
        holidayModelBean.preInsert();
        return holidayModelDao.insert(holidayModelBean);
    }
    @Override
    public List<HolidayModelBean> queryList(HolidayModelBean holidayModelBean){
        return holidayModelDao.queryList(holidayModelBean);
    }
}
