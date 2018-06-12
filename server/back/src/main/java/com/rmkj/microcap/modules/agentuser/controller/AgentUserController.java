package com.rmkj.microcap.modules.agentuser.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.agentuser.entity.AgentUserBean;
import com.rmkj.microcap.modules.agentuser.service.AgentUserService;
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
* Created by Administrator on 2016-11-4.
*/
@Controller
@RequestMapping("/agentuser")
public class AgentUserController extends BaseController {
    @Autowired
    private AgentUserService agentUserService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("agentuser")
    public String listPage() {
        return "/agentuser/agentuser_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("agentuser:add")
    public String addPage() {
         return "/agentuser/agentuser_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("agentuser:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", agentUserService.get(id));
        return "/agentuser/agentuser_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("agentuser")
    public GridPager listData(AgentUserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentUserBean> entityList = agentUserService.queryList(entity);
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
    @RequiresPermissions("agentuser:add")
    public ExecuteResult save(@Valid AgentUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentUserService.insert(entity);
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
    @RequiresPermissions("agentuser:edit")
    public ExecuteResult update(@Valid AgentUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentUserService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("agentuser:delete")
    public ExecuteResult delete(String id) {
        agentUserService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
