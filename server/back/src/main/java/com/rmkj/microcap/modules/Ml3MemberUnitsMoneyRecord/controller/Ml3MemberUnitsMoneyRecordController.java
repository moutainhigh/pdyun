package com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.controller;/**
 * Created by Administrator on 2017/5/9.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.service.Ml3MemberUnitsMoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author k
 * @create -05-09-15:26
 **/
@Controller
@RequestMapping(value = "/Ml3MemberUnitsMoneyRecord")
public class Ml3MemberUnitsMoneyRecordController extends BaseController{

    @Autowired
    private Ml3MemberUnitsMoneyRecordService ml3MemberUnitsMoneyRecordService;

    @RequestMapping(value = "/findMemberUnitsMoneyRecordList", method = RequestMethod.GET)
    public String findUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord entity){
        return "/ml3MemberUnitsMoneyRecord/memberUnitsMoneyRecord_list";
    }

    @ResponseBody
    @RequestMapping(value = "/findMemberUnitsMoneyRecordList", method = RequestMethod.POST)
    public Map<String, Object> findMemberUnitsMoneyRecordList(Ml3MemberUnitsMoneyRecord entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        return ml3MemberUnitsMoneyRecordService.findMemberUnitsMoneyRecordList(entity);
    }

    /**
     * 审核会员单位出金-通过
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberUnitsMoneyPass/pass", method = RequestMethod.POST)
    public ExecuteResult memberUnitsMoneyPass(String ids){
        return ml3MemberUnitsMoneyRecordService.memberUnitsMoneyPass(ids);
    }

    /**
     * 审核会员单位出金-不通过
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberUnitsMoneyPass/noPass", method = RequestMethod.POST)
    public ExecuteResult memberUnitsMoneyNoPass(String ids, String failureReason){
        return ml3MemberUnitsMoneyRecordService.memberUnitsMoneyNoPass(ids, failureReason);
    }
}
