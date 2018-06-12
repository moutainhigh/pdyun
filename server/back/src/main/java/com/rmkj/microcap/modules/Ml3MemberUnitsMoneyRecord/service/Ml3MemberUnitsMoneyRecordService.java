package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.service;/**
 * Created by Administrator on 2017/5/9.
 */

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3MemberUnits.dao.IMl3MemberUnitsDao;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.dao.Ml3MemberUnitsMoneyRecordDao;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author k
 * @create -05-09-15:24
 **/
@Service
@Transactional
public class Ml3MemberUnitsMoneyRecordService {

    @Autowired
    private Ml3MemberUnitsMoneyRecordDao ml3MemberUnitsMoneyRecordDao;

    @Autowired
    private IMl3MemberUnitsDao ml3MemberUnitsDao;

    /**
     * 查询会员单位申请出金记录
     * @param entity
     * @return
     */
    public Map<String, Object> findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord entity){
        Map<String, Object> result = new HashMap();

        List<Ml3MemberUnitsMoneyRecord> moneyRecordList = ml3MemberUnitsMoneyRecordDao.findMemberUnitsMoneyRecordList(entity);
        GridPager pager = new GridPager();
        pager.setTotal(MybatisPagerInterceptor.getTotal());
        pager.setRows(moneyRecordList);
        result.put("g", pager);

        result.put("moneyOutTotal", ml3MemberUnitsMoneyRecordDao.unitsMoneySum(entity));
        result.put("noMoneyOutTotal", ml3MemberUnitsMoneyRecordDao.unitsNoMoneyOutTotal(entity));
        result.put("alreadyMoneyOutTotal", ml3MemberUnitsMoneyRecordDao.unitsMoneyOutTotal(entity));

        return result;
    }

    /**
     * 审核会员单位出金-通过
     * @param ids
     * @return
     */
    public ExecuteResult memberUnitsMoneyPass(String ids){
        if(StringUtils.isBlank(ids)){
            return new ExecuteResult(StatusCode.FAILED, "请选择！");
        }
        if(ids.indexOf(",") == -1){
            if(updateMl3MemberUnitsMoneyRecordStatus(ids) != 1){
                return new ExecuteResult(StatusCode.FAILED, "审核失败！");
            }else{
                return new ExecuteResult(StatusCode.FAILED, "审核通过！");
            }

        }else{
            //记录审核状态
            int success = 0, falid = 0;
            String[] split = ids.split(",");
            int length = split.length;
            for (int i = 0; i < length; i++){
                if(updateMl3MemberUnitsMoneyRecordStatus(split[i]) != 1){
                    falid++;
                }else{
                    success++;
                }
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("成功审核：").append(success).append("笔，").append("失败：").append(falid).append("笔");
            return new ExecuteResult(StatusCode.OK, buffer.toString());
        }

    }

    /**
     * 修改出金状态
     * @param id
     * @return
     */
    public int updateMl3MemberUnitsMoneyRecordStatus(String id){
        Ml3MemberUnitsMoneyRecord unitsMoneyRecord = ml3MemberUnitsMoneyRecordDao.get(id);
        if(null == unitsMoneyRecord){
            return -1;
        }
        unitsMoneyRecord.setStatus(Constants.WITHDRAW_STATUS.SUCCESS);
        unitsMoneyRecord.setReviewStatus(Constants.WITHDRAW_STATUS.SUCCESS);
        unitsMoneyRecord.setCompleteTime(new Date());
        if(ml3MemberUnitsMoneyRecordDao.updateByPrimaryKeySelective(unitsMoneyRecord) == 1){
            return 1;
        }
        return -1;
    }

    /**
     * 会员单位出金-审核不通过
     * @param ids
     * @param failureReason
     * @return
     */
    public ExecuteResult memberUnitsMoneyNoPass(String ids, String failureReason){
        if(StringUtils.isBlank(ids)){
            return new ExecuteResult(StatusCode.FAILED);
        }
        if(ids.indexOf(",") == -1){
            if(updateMl3MemberUnitsMoneyRecordNoStatus(ids, failureReason) != 1){
                return new ExecuteResult(StatusCode.FAILED);
            }else{
                return new ExecuteResult(StatusCode.OK);
            }
        }else{
            //记录审核状态
            int success = 0, falid = 0;
            String[] split = ids.split(",");
            int length = split.length;
            for (int i = 0; i < length; i++){
                if(updateMl3MemberUnitsMoneyRecordNoStatus(split[i], failureReason) != 1){
                    falid++;
                }else{
                    success++;
                }
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("成功操作：").append(success).append("笔，").append("失败：").append(falid).append("笔");
            return new ExecuteResult(StatusCode.OK, buffer.toString());
        }
    }

    /**
     * 修改出金状态-不通过
     * @param id
     * @return
     */
    public int updateMl3MemberUnitsMoneyRecordNoStatus(String id, String failureReason){
        Ml3MemberUnitsMoneyRecord unitsMoneyRecord = ml3MemberUnitsMoneyRecordDao.get(id);
        if(null == unitsMoneyRecord){
            return -1;
        }
        unitsMoneyRecord.setStatus(Constants.WITHDRAW_STATUS.FAILD);
        unitsMoneyRecord.setReviewStatus(Constants.WITHDRAW_STATUS.FAILD);
        unitsMoneyRecord.setRemark(failureReason);
        unitsMoneyRecord.setCompleteTime(new Date());
        if(ml3MemberUnitsMoneyRecordDao.updateByPrimaryKeySelective(unitsMoneyRecord) == 1){
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            returnUnitsMoney(unitsMoneyRecord);
            return 1;
        }
        return -1;
    }

    /**
     * 审核不通过时返还会员单位保证金
     * @param ml3MemberUnitsMoneyRecord
     */
    public int returnUnitsMoney(Ml3MemberUnitsMoneyRecord ml3MemberUnitsMoneyRecord){
        return ml3MemberUnitsDao.returnUnitsMOney(ml3MemberUnitsMoneyRecord);
    }
}
