package com.rmkj.microcap.modules.syslog.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.syslog.dao.ISysLogDao;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-10-21.
*/
@Service
@Transactional
public class SysLogService implements IBaseService<SysLogBean> {
    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public SysLogBean get(String id){
        return sysLogDao.get(id);
    }
    @Override
    public int update(SysLogBean sysLogBean){
        sysLogBean.preUpdate();
        return sysLogDao.update(sysLogBean);
    }
    @Override
    public int delete(String id){
        return sysLogDao.delete(id);
    }
    @Override
    public int insert(SysLogBean sysLogBean){
        sysLogBean.preInsert();
        return sysLogDao.insert(sysLogBean);
    }
    @Override
    public List<SysLogBean> queryList(SysLogBean sysLogBean){
        return sysLogDao.queryList(sysLogBean);
    }
    //导出到Excel
    public HSSFWorkbook exportExcel(SysLogBean sysLogBean) {
        sysLogBean.setPage(1);
        sysLogBean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"用户id","用户登录名","日志内容","类型","时间",};
        String[] headerColumn = new String[]{"mId","loginName","lContent","lType","lCreateTime"};
        int[] columnWidth = new int[]{20*180,20*180,20*500,20*180,20*240};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("lType", (Object obj) -> {
            SysLogBean u = (SysLogBean)obj;
            return u.getlType() ==0?"无":"无";
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<SysLogBean>(){

            private int page = 1;

            @Override
            public List<SysLogBean> find() {
                List<SysLogBean> list = sysLogDao.queryList(sysLogBean);
                sysLogBean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long)Math.ceil(MybatisPagerInterceptor.getTotal()/sysLogBean.getRows().doubleValue());
            }

        }, SysLogBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }
}
