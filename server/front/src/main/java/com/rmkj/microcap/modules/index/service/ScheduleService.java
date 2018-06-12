package com.rmkj.microcap.modules.index.service;

import com.rmkj.microcap.modules.index.dao.ScheduleDao;
import com.rmkj.microcap.modules.user.entity.Ml3MemberUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by renwp on 2017/1/10.
 * 缓存 周期更新类
 */
@Service
@Lazy(false)
public class ScheduleService {

    private volatile List<Ml3MemberUnits> list = null;

    @Autowired
    private ScheduleDao scheduleDao;

    private Map<String, Object> parameterSetMap = null;


    /**
     * 获取会员单位信息
     * @return
     */
    public List<Ml3MemberUnits> ml3MemberUnits(){
        if(list == null){
            units();
        }
        return list;
    }

    /**
     * 获取系统设置参数
     * @param key
     * @return
     */
    public Object getParameter(String key){
        if(null == parameterSetMap){
            units();
        }
        return parameterSetMap.get(key);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void units(){
        list  = scheduleDao.queryUnits();
        //获取系统参数配置表
        parameterSetMap = scheduleDao.findParameterSet();
    }

    //@Scheduled(initialDelay = 10000, fixedDelay = 10*60000)
    public void checkOvertime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        scheduleDao.overtimeMoneyIn(calendar.getTime());
    }
}
