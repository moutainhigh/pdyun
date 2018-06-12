package com.rmkj.microcap.modules.tradeReturnFeeWithdraw.controller;/**
 * Created by Administrator on 2017/9/21.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.trade.entity.TradeStatisticsParams;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeePage;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.entity.TradeReturnFeeRecord;
import com.rmkj.microcap.modules.tradeReturnFeeWithdraw.service.TradeReturnFeeService;
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
import java.util.List;

/**
 * @author k
 * @create -09-21-16:11
 **/

@Controller
@RequestMapping(value = "/returnFeeWithdraw")
public class TradeReturnFeeController extends BaseController{

    @Autowired
    private TradeReturnFeeService tradeReturnFeeService;

    @Autowired
    private Ml3AgentService ml3AgentService;


    @ResponseBody
    @RequestMapping(value = "/findReturnFeeRecord", method = RequestMethod.POST)
    public GridPager findReturnFeeRecord(TradeReturnFeePage entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        return tradeReturnFeeService.findRetrunFeeMoneyRecordByRole(entity);
    }

    /**
     * 返手续费提现
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/returnFeeWithDraw", method = RequestMethod.POST)
    @RequiresPermissions("returnFee:returnFeeWithDraw")
    public ExecuteResult returnFeeTotalWithDraw(@Valid TradeReturnFeeRecord entity, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return tradeReturnFeeService.returnFeeTotalWithDraw(entity);
    }

    @RequestMapping(value = "/withdrawReturnFee", method = RequestMethod.GET)
    @RequiresPermissions("returnFee:returnFeeWithDraw")
    public String withDrawReturnFeeMoney(Model model){
        return "/returnFeeMoney/returnFee_withDraw";
    }


    @RequestMapping(value = "/tradeReturnFee/list", method = RequestMethod.GET)
    //@RequiresPermissions("returnFee:tradefee")
    public String returnTradeFeeRecord(Model model){
        List<Ml3RoleBean> roleList = UserUtils.getUser().getRoleList();
        Ml3RoleBean ml3RoleBean = roleList.get(0);
        if(Constants.ML3_AGENT_ROLE.ROLE_UNITS.equals(ml3RoleBean.getId())){ //市场管理部
            return "/returnFeeMoney/returnFeeMoneyAgent_list";
        }else if(Constants.ML3_AGENT_ROLE.ROLE_CENTER.equals(ml3RoleBean.getId())){//会员单位
            return "/returnFeeMoney/returnFeeMoneyUnits_list";
        }else{
            return "";
        }
    }

    /**
     * 查询会员单位或代理商提现记录
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/returnFeeList", method = RequestMethod.POST)
    //@RequiresPermissions("returnFee:tradefee")
    public GridPager returnTradeFeeRecordList(TradeReturnFeeRecord entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3RoleBean> roleList = UserUtils.getUser().getRoleList();
        Ml3RoleBean ml3RoleBean = roleList.get(0);
        if(Constants.ML3_AGENT_ROLE.ROLE_UNITS.equals(ml3RoleBean.getId())){ //市场管理部
            entity.setCenterId(UserUtils.getUser().getCenterId());
            return tradeReturnFeeService.queryReturnFeeMoneyByAgent(entity);
        }else if(Constants.ML3_AGENT_ROLE.ROLE_CENTER.equals(ml3RoleBean.getId())){//会员单位
            entity.setUnitsId(UserUtils.getUser().getUnitsId());
            return tradeReturnFeeService.queryReturnFeeMoneyByUnits(entity);
        }else{
            return null;
        }
    }


    /**
     * 判断当前角色
     * @param tradeStatisticsParams
     */
    private void initTradeStatisticsParams(TradeStatisticsParams tradeStatisticsParams){
        com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());

        List<Ml3RoleBean> roleList = UserUtils.getUser().getRoleList();
        roleList.forEach(ml3RoleBean -> {
            if("2".equals(ml3RoleBean.getId()) || "5".equals(ml3RoleBean.getId())){
                tradeStatisticsParams.setUnitsId(ml3AgentBean.getUnitsId());
            }else if("3".equals(ml3RoleBean.getId()) || "4".equals(ml3RoleBean.getId())){
                //com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
                tradeStatisticsParams.setAgentId(ml3AgentBean.getId());
            }else if("1".equals(ml3RoleBean.getId())){
                tradeStatisticsParams.setCenterId(ml3AgentBean.getCenterId());
            }
        });
    }

    /**
     * 返手续费提现线下审核通过，不走代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ExecuteResult auditReturnFeeRecord(String ids){
        return tradeReturnFeeService.passReturnFeeMoney(ids);
    }

    /**
     * 返手续费提现线下审核不通过，不走代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public ExecuteResult unAuditReturnFeeRecord(String ids, String failureReason){
        return tradeReturnFeeService.noPassReturnFeeMoney(ids, failureReason);
    }

    /**
     * 导出返手续费提现线下出金表格
     * @param ids
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/exportAlaExcel", method = RequestMethod.GET)
    public void exportAlaExcel(String ids, HttpServletResponse response){
        HSSFWorkbook wb = tradeReturnFeeService.exportAlaExcel(ids);
        ExcelUtils.exportExcel("预线下代付名单".concat(DateUtils.getDate("yyyyMMddHHmmss")), response, wb);

    }
}
