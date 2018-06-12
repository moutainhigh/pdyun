package com.rmkj.microcap.modules.Ml3OperateCenter.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
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
* Created by Administrator on 2016-11-17.
*/
@Controller
@RequestMapping("/Ml3OperateCenter")
public class Ml3OperateCenterController extends BaseController {
    @Autowired
    private Ml3OperateCenterService ml3OperateCenterService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("Ml3OperateCenter")
    public String listPage() {
        return "/Ml3OperateCenter/Ml3OperateCenter_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("Ml3OperateCenter:add")
    public String addPage() {
         return "/Ml3OperateCenter/Ml3OperateCenter_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("Ml3OperateCenter:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3OperateCenterService.get(id));
        return "/Ml3OperateCenter/Ml3OperateCenter_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("Ml3OperateCenter")
    public GridPager listData(Ml3OperateCenterBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3OperateCenterBean> entityList = ml3OperateCenterService.queryList(entity);
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
    @RequiresPermissions("Ml3OperateCenter:add")
    public ExecuteResult save(@Valid Ml3OperateCenterBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3OperateCenterService.insert(entity);
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
    @RequiresPermissions("Ml3OperateCenter:edit")
    public ExecuteResult update(@Valid Ml3OperateCenterBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3OperateCenterService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("Ml3OperateCenter:delete")
    public ExecuteResult delete(String id) {
        ml3OperateCenterService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //开启合约
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @RequiresPermissions("Ml3OperateCenter")
    public ExecuteResult open(Ml3OperateCenterBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3OperateCenterService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //关闭合约
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @RequiresPermissions("Ml3OperateCenter")
    public ExecuteResult close(Ml3OperateCenterBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3OperateCenterService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }
}
