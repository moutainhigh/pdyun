package com.rmkj.microcap.modules.moneychange.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangePageBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class MoneyChangeService implements IBaseService<MoneyChangeBean> {
    @Autowired
    private IMoneyChangeDao moneyChangeDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public MoneyChangeBean get(String id){
        return moneyChangeDao.get(id);
    }
    @Override
    public int update(MoneyChangeBean moneyChangeBean){
        moneyChangeBean.preUpdate();
        return moneyChangeDao.update(moneyChangeBean);
    }
    @Override
    public int delete(String id){
        return moneyChangeDao.delete(id);
    }
    @Override
    public int insert(MoneyChangeBean moneyChangeBean){
        moneyChangeBean.preInsert();
        return moneyChangeDao.insert(moneyChangeBean);
    }
    @Override
    public List<MoneyChangeBean> queryList(MoneyChangeBean moneyChangeBean){
        return moneyChangeDao.queryList(moneyChangeBean);
    }
    //导出到excel
    public HSSFWorkbook exportExcel(MoneyChangeBean bean) {
        bean.setPage(1);
        bean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"会员id","会员姓名","会员手机号","变更金额","类型","变更前资金","变更后资金","创建时间"};
        String[] headerColumn = new String[]{"userId","uname","mobile","difMoney","type","beforeMoney","afterMoney","createTime"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*240};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("type", (Object obj) -> {
            MoneyChangeBean m = (MoneyChangeBean)obj;
            return m.getType() == 0 ? "充值" :( m.getType() == 1 ? "提现" : m.getType() == 2 ? "建仓":"平仓");
        });

        ExcelUtils.work(new ExcelUtils.DataFromDB<MoneyChangeBean>(){

            private int page = 1;

            @Override
            public List<MoneyChangeBean> find() {
                List<MoneyChangeBean> list = moneyChangeDao.queryList(bean);
                bean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long)Math.ceil(MybatisPagerInterceptor.getTotal()/bean.getRows().doubleValue());
            }

        }, MoneyChangeBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }

    public GridPager findMoneyChangePage(MoneyChangePageBean moneyChangePageBean){
        if(null != moneyChangePageBean.getUname() || null != moneyChangePageBean.getMobile()){
            UserBean userBean = new UserBean();
            userBean.setChnName(moneyChangePageBean.getUname());
            userBean.setMobile(moneyChangePageBean.getMobile());
            //获取当前查询用户的信息
            List<UserBean> userList = userDao.findUserSelective(userBean);
            if(0 < userList.size() && !userList.isEmpty()){
                List<String> userIds = new ArrayList<>();
                for (UserBean user : userList){
                    userIds.add(user.getId());
                }
                moneyChangePageBean.setUserIdList(userIds);
            }
        }
        List<MoneyChangePageBean> list = moneyChangeDao.findMoneyChangePage(moneyChangePageBean);
        GridPager page = new GridPager();
        page.setRows(list);
        page.setTotal((int) moneyChangeDao.getTotal(moneyChangePageBean));
        return page;
    }
}
