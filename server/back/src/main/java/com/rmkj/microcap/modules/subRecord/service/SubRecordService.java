package com.rmkj.microcap.modules.subRecord.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.service.SystemService;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.moneychange.dao.IMoneyChangeDao;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneyrecord.dao.IMoneyRecordDao;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.subGoods.entity.SubGoods;
import com.rmkj.microcap.modules.subRecord.dao.SubRecordDao;
import com.rmkj.microcap.modules.subRecord.entity.SubParamBean;
import com.rmkj.microcap.modules.subRecord.entity.SubRecordBean;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.service.Ml3AgentRoleService;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.SubMoney;
import com.rmkj.microcap.modules.user.entity.UserBean;
import com.rmkj.microcap.modules.user.entity.UserReport;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class SubRecordService {
    private final Logger logger = Logger.getLogger(SubRecordService.class);
    @Autowired
    private SubRecordDao subRecordDao;

    /**
     * 添加认购明细
     * @param subRecordBean
     */
    public ExecuteResult addSubRecord(SubRecordBean subRecordBean){
        subRecordBean.setId(Utils.uuid());
        SubParamBean subParamBean;
        if(!StringUtils.isEmpty(subRecordBean.getAgentId())){
            subRecordBean.setSubRoleId(subRecordBean.getAgentId());
            subRecordBean.setSubRole("3");//1-vip客户， 2-高级客户， 3-合伙人 ,4-玩家
            subParamBean = subRecordDao.getAgentLeftTimes(subRecordBean.getAgentId());
            subRecordBean.setSubRoleName(subParamBean.getAgentName());
            subRecordBean.setUnitsName(subParamBean.getUnitsName());
            subRecordBean.setCenterName(subParamBean.getCenterName());
        }else if(!StringUtils.isEmpty(subRecordBean.getUnitsId())){
            subRecordBean.setSubRoleId(subRecordBean.getUnitsId());
            subRecordBean.setSubRole("2");
            subParamBean = subRecordDao.getUnitsLeftTimes(subRecordBean.getUnitsId());
            subRecordBean.setSubRoleName(subParamBean.getUnitsName());
            subRecordBean.setCenterName(subParamBean.getCenterName());
        }else{
            subRecordBean.setSubRoleId(subRecordBean.getCenterId());
            subRecordBean.setSubRole("1");
            subParamBean = subRecordDao.getCenterLeftTimes(subRecordBean.getCenterId());
            subRecordBean.setSubRoleName(subParamBean.getCenterName());
        }
        //判断次数
        if(subParamBean.getSubTimes() == 0){
            return new ExecuteResult(StatusCode.SUB_TIMES_OVER);
        }
        SubGoods subGoods = subRecordDao.getSubGoodsById(subRecordBean.getGoodsId());
        int subNum = subRecordBean.getSubMoney().divide(subGoods.getGoodsSubPrice(),0,BigDecimal.ROUND_HALF_DOWN).intValue();
        subRecordBean.setSubTotalNum(subNum);
        //添加认购明细
        subRecordDao.saveSubRecord(subRecordBean);
        //商品数量更新
//        subRecordDao.updSubGoodsLeftNum(subRecordBean);
        //认购次数更新
        if(subRecordBean.getSubRole().equals("3")){
            subRecordDao.updAgentSubTimes(subRecordBean.getSubRoleId());
        }else if(subRecordBean.getSubRole().equals("2")){
            subRecordDao.updUnitsSubTimes(subRecordBean.getSubRoleId());
        }else{
            subRecordDao.updCenterSubTimes(subRecordBean.getSubRoleId());
        }
        return new ExecuteResult(StatusCode.OK);
    }

    public  List<SubRecordBean> queryList(SubRecordBean subRecordBean ){
        return subRecordDao.queryList(subRecordBean);
    }

}
