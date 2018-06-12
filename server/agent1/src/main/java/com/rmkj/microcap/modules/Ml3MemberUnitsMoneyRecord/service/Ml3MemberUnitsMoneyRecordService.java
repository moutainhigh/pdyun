package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.service;/**
 * Created by Administrator on 2017/5/9.
 */

import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.dao.Ml3MemberUnitsMoneyRecordDao;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import sun.security.krb5.internal.AuthContext;

import java.util.List;

/**
 * @author k
 * @create -05-09-15:24
 **/
@Service
@Transactional
public class Ml3MemberUnitsMoneyRecordService {

    @Autowired
    private Ml3MemberUnitsMoneyRecordDao ml3MemberUnitsMoneyRecordDao;

    /**
     * 查询会员单位申请出金记录
     * @param entity
     * @return
     */
    public GridPager findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord entity){
        entity.setUnitsId(UserUtils.getUser().getUnitsId());
        List<Ml3MemberUnitsMoneyRecord> moneyRecordList = ml3MemberUnitsMoneyRecordDao.findMemberUnitsMoneyRecordList(entity);
        GridPager pager = new GridPager();
        pager.setTotal(MybatisPagerInterceptor.getTotal());
        pager.setRows(moneyRecordList);
        return pager;
    }
}
