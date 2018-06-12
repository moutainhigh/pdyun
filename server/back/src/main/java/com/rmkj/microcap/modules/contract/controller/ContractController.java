package com.rmkj.microcap.modules.contract.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.contract.entity.ContractBean;
import com.rmkj.microcap.modules.contract.service.ContractService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {
    @Autowired
    private ContractService contractService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("contract")
    public String listPage() {
        return "/contract/contract_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("contract:add")
    public String addPage() {
         return "/contract/contract_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("contract:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", contractService.get(id));
        return "/contract/contract_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("contract")
    public GridPager listData(ContractBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<ContractBean> entityList = contractService.queryList(entity);
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
    @RequiresPermissions("contract:add")
    public ExecuteResult save(@Valid ContractBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        contractService.insert(entity);
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
    @RequiresPermissions("contract:edit")
    public ExecuteResult update(@Valid ContractBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        contractService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("contract:delete")
    public ExecuteResult delete(String id) {
        contractService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //开启合约
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @RequiresPermissions("contract")
    public ExecuteResult open(ContractBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        contractService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //关闭合约
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @RequiresPermissions("contract")
    public ExecuteResult close(ContractBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        contractService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }
}
