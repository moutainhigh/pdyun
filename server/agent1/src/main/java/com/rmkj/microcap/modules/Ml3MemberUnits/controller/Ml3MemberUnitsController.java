package com.rmkj.microcap.modules.Ml3MemberUnits.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnitsMoneyRecord.entity.Ml3MemberUnitsMoneyRecord;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.parameterSet.service.ParameterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-11-17.
*/
@Controller
@RequestMapping("/Ml3MemberUnits")
public class Ml3MemberUnitsController extends BaseController {
    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private Ml3AgentService ml3AgentService;
    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("Ml3MemberUnits")
    public String listPage() {
        return "/Ml3MemberUnits/Ml3MemberUnits_list";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits")
    public GridPager listData(Ml3MemberUnitsBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3MemberUnitsBean> entityList = ml3MemberUnitsService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits:add")
    public ExecuteResult save(@Valid Ml3MemberUnitsBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setCenterId(ml3AgentBean.getCenterId());
        //记录money
        entity.setMoney(new BigDecimal(entity.getBondMoney()));
        entity.setMoneyLimit(new BigDecimal(3000));//默认保证金余额限制为3000
        //会员单位默认停用
        entity.setStatus(Integer.valueOf(Constants.AGENT_STATUS.DISABLE));
        ml3MemberUnitsService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 更新
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits:edit")
    public ExecuteResult update(@Valid Ml3MemberUnitsBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3MemberUnitsService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits:delete")
    public ExecuteResult delete(String id) {
        ml3MemberUnitsService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //开启合约
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits")
    public ExecuteResult open(Ml3MemberUnitsBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3MemberUnitsService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //关闭合约
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits")
    public ExecuteResult close(Ml3MemberUnitsBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3MemberUnitsService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
     * 会员单位管理页面
     * @return
     */
    @RequestMapping(value = "/unitslist", method = RequestMethod.GET)
    @RequiresPermissions("Ml3MemberUnits")
    public String unitslistPage(Map<String,Object> model) {
        String qrCodeMenuUrl = parameterService.getQrCodeMenuUrl();
        model.put("qrCodeMenuUrl",qrCodeMenuUrl);
        return "/Ml3MemberUnits/Ml3InnerUnits_list";
    }
    /**
     * 会员单位管理页面列表数据
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unitslist", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits")
    public GridPager unitslistData(Ml3MemberUnitsBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setCenterId(ml3AgentBean.getCenterId());
        List<Ml3MemberUnitsBean> entityList = ml3MemberUnitsService.memberUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
     * 新增页面
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("Ml3MemberUnits:add")
    public String addPage() {
//        List<Ml3OperateCenterBean> entityList = ml3OperateCenterService.OcList();
//        request.setAttribute("list",entityList);
        return "/Ml3MemberUnits/Ml3MemberUnits_add";
    }

    /**
     * 编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("Ml3MemberUnits:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3MemberUnitsService.get(id));
//        List<Ml3OperateCenterBean> entityList = ml3OperateCenterService.OcList();
//        request.setAttribute("list",entityList);
        return "/Ml3MemberUnits/Ml3MemberUnits_edit";
    }

    /**
     * 会员单位申请出金
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUnitsWithdraw", method = RequestMethod.GET)
    @RequiresPermissions("Ml3MemberUnits:withdraw")
    public String getUnitsWithdraw(Model model){
        ml3MemberUnitsService.getMemberUnits(model);
        return "/Ml3Agent/Ml3Agent_unitsWithdraw";
    }

    @ResponseBody
    @RequestMapping(value = "/unitsWithdraw", method = RequestMethod.POST)
    @RequiresPermissions("Ml3MemberUnits:withdraw")
    public ExecuteResult unitsMoneyWithdraw(Ml3MemberUnitsMoneyRecord entity, Errors errors, Model model){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return ml3MemberUnitsService.unitsMoneyWithdraw(entity);
    }
}
