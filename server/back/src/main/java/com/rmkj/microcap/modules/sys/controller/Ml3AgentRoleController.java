package com.rmkj.microcap.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.sys.bean.Ml3AgentRoleBean;
import com.rmkj.microcap.modules.sys.service.Ml3AgentRoleService;
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
* Created by Administrator on 2016-11-15.
*/
@Controller
@RequestMapping("/M3AgentRole")
public class Ml3AgentRoleController extends BaseController {
    @Autowired
    private Ml3AgentRoleService ml3AgentRoleService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentRole")
    public String listPage() {
        return "/M3AgentRole/M3AgentRole_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentRole:add")
    public String addPage() {
         return "/M3AgentRole/M3AgentRole_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentRole:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3AgentRoleService.get(id));
        return "/M3AgentRole/M3AgentRole_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("M3AgentRole")
    public GridPager listData(Ml3AgentRoleBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentRoleBean> entityList = ml3AgentRoleService.queryList(entity);
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
    @RequiresPermissions("M3AgentRole:add")
    public ExecuteResult save(@Valid Ml3AgentRoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentRoleService.insert(entity);
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
    @RequiresPermissions("M3AgentRole:edit")
    public ExecuteResult update(@Valid Ml3AgentRoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentRoleService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("M3AgentRole:delete")
    public ExecuteResult delete(String id) {
        ml3AgentRoleService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
