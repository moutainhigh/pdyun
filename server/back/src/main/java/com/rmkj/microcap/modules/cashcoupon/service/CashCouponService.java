package com.rmkj.microcap.modules.cashcoupon.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.common.modules.sys.dao.ISysDictDao;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.cashcoupon.dao.ICashCouponDao;
import com.rmkj.microcap.modules.cashcoupon.entity.CashCouponBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class CashCouponService implements IBaseService<CashCouponBean> {
    @Autowired
    private ICashCouponDao cashCouponDao;
    @Autowired
    private ISysDictDao sysDictDao;
    @Override
    public CashCouponBean get(String id){
        return cashCouponDao.get(id);
    }
    @Override
    public int update(CashCouponBean cashCouponBean){
        cashCouponBean.preUpdate();
        //同时修改字典表
//        SysDictBean sysDictBean = sysDictDao.get(cashCouponBean.getType().toString());
//        sysDictBean.setValue(cashCouponBean.getMoney().toString());
//        sysDictBean.setLabel(cashCouponBean.getMoney().toString()+"元代金券");
//        sysDictBean.preUpdate();
//        sysDictDao.update(sysDictBean);
        cashCouponBean.setUpdateTime(new Date());//修改时间
        cashCouponBean.setUpdateBy(UserUtils.getUser());
        return cashCouponDao.update(cashCouponBean);
    }
    @Override
    public int delete(String id){
        return cashCouponDao.delete(id);
    }
    @Override
    public int insert(CashCouponBean cashCouponBean){
        cashCouponBean.setId(Utils.uuid());
        cashCouponBean.setMoney(cashCouponBean.getMoney());
        cashCouponBean.setType(cashCouponBean.getType());
        cashCouponBean.setCreateTime(new Date());
        cashCouponBean.preInsert();
        return cashCouponDao.insert(cashCouponBean);
    }
    @Override
    public List<CashCouponBean> queryList(CashCouponBean cashCouponBean){
        return cashCouponDao.queryList(cashCouponBean);
    }
    public int saveSysdict(SysDictBean bean){
         bean.preInsert();
        Random random = new Random();
        Integer id = random.nextInt(1000000)%(1000000-1+1) + 1;
        bean.setId(id.toString());
        //同时新增代金券种类表
        CashCouponBean cashCouponBean = new CashCouponBean();
        cashCouponBean.setId(Utils.uuid());
        cashCouponBean.setMoney(Integer.parseInt(bean.getValue()));
        cashCouponBean.setType(id);
        cashCouponBean.setCreateTime(new Date());
        cashCouponBean.preInsert();
        cashCouponDao.insert(cashCouponBean);
        return sysDictDao.insert(bean);
    }
    public List<SysDictBean> dictList(){
        List<SysDictBean> dictList= cashCouponDao.dictList();
        return dictList;
    }
}
