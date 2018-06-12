package com.rmkj.microcap.modules.returnFeeMoney.controller;/**
 * Created by Administrator on 2017/9/27.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.returnFeeMoney.entity.*;
import com.rmkj.microcap.modules.returnFeeMoney.service.ReturnFeeMoneyService;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author k
 * @create -09-27-11:26
 **/
@Controller
@RequestMapping(value = "/returnFeeMoney")
public class ReturnFeeMoneyController extends BaseController {

    @Autowired
    private ReturnFeeMoneyService returnFeeMoneyService;

    /**
     * 查询会员单位手续费提现
     * @param model
     * @return
     */
    @RequestMapping(value = "/recordOpearteCenterList", method = RequestMethod.GET)
    @RequiresPermissions("returnFeeMoney:list")
    public String queryReturnFeeMoneyCenter(Model model){
        return "/returnFeeMoney/returnFeeMoneyCenter_list";
    }

    @RequestMapping(value = "/moneyPlat", method = RequestMethod.GET)
    public String moneyPlatPage(Model model){
        return "/returnFeeMoney/returnFeePlat";
    }

    @ResponseBody
    @RequestMapping(value = "/saveMoneyPlatRecord", method = RequestMethod.POST)
    public ExecuteResult saveMoneyPlatRecord(TradeReturnFeeRecord entity, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return returnFeeMoneyService.saveMoneyPlatRecord(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/moneyPlatRecord", method = RequestMethod.POST)
    public Map<String, Object> moneyPlatRecord(TradeReturnFeeRecord entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Map<String, Object> result = new HashedMap();
        GridPager pager = returnFeeMoneyService.moneyPlatRecord(entity);
        result.put("page", pager);
        return result;
    }


     //当前持仓数据导出
    @RequestMapping("exportExcelHold")
    public void exportExcel( TradeReturnFeeRecord bean, HttpServletResponse response){
        HSSFWorkbook wb = returnFeeMoneyService.exportExcel(bean);
        ExcelUtils.exportExcel("平台手续费服务费提现", response, wb);
    }


    /**
     * 查询会员单位手续费提现
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordOpearteCenterList", method = RequestMethod.POST)
    @RequiresPermissions("returnFeeMoney:list")
    public Map<String, Object> queryReturnFeeMoneyByCenter(@Valid ReturnFeeOperateCenter entity, Errors errors){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Map<String, Object> result = new HashedMap();
        GridPager pager = returnFeeMoneyService.queryReturnFeeMoneyByCenter(entity);
        result.put("page", pager);

        return result;
    }


    /**
     * 查询会员单位手续费提现
     * @param model
     * @return
     */
    @RequestMapping(value = "/recordMemberUnitsList", method = RequestMethod.GET)
    @RequiresPermissions("returnFeeMoney:list")
    public String queryReturnFeeMoneyUnits(Model model){

        return "/returnFeeMoney/returnFeeMoneyUnits_list";
    }

    /**
     * 查询会员单位手续费提现
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordMemberUnitsList", method = RequestMethod.POST)
    @RequiresPermissions("returnFeeMoney:list")
    public Map<String, Object> queryReturnFeeMoneyByUnits(@Valid ReturnFeeMemberUnits entity, Errors errors){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Map<String, Object> result = new HashedMap();
        GridPager pager = returnFeeMoneyService.queryReturnFeeMoneyByUnits(entity);
        result.put("page", pager);

        return result;
    }

    /**
     * 查询代理商手续费提现
     * @param model
     * @return
     */
    @RequestMapping(value = "/recordAgentList", method = RequestMethod.GET)
    @RequiresPermissions("returnFeeMoney:list")
    public String queryReturnFeeMoneyAgent(Model model){

        return "/returnFeeMoney/returnFeeMoneyAgent_list";
    }

    /**
     * 查询代理商手续费提现
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordAgentList", method = RequestMethod.POST)
    @RequiresPermissions("returnFeeMoney:list")
    public Map<String, Object> queryReturnFeeMoneyByAgent(@Valid ReturnFeeAgent entity, Errors errors){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Map<String, Object> result = new HashedMap();
        GridPager pager = returnFeeMoneyService.queryReturnFeeMoneyByAgent(entity);
        result.put("page", pager);

        return result;
    }

    /**
     * 返手续费提现线下审核通过，不走代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ExecuteResult auditReturnFeeRecord(String ids){
        return returnFeeMoneyService.passReturnFeeMoney(ids);
    }

    /**
     * 返手续费提现线下审核不通过，不走代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public ExecuteResult unAuditReturnFeeRecord(String ids, String failureReason){
        return returnFeeMoneyService.noPassReturnFeeMoney(ids, failureReason);
    }

    /**
     * 导出返手续费提现线下出金表格
     * @param ids
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/exportAlaExcel", method = RequestMethod.GET)
    public void exportAlaExcel(String ids, HttpServletResponse response){
        HSSFWorkbook wb = returnFeeMoneyService.exportAlaExcel(ids);
        ExcelUtils.exportExcel("预线下代付名单".concat(DateUtils.getDate("yyyyMMddHHmmss")), response, wb);

    }
}
