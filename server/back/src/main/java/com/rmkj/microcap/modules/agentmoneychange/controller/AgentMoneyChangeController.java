package com.rmkj.microcap.modules.agentmoneychange.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.agentmoneychange.entity.AgentMoneyChangeBean;
import com.rmkj.microcap.modules.agentmoneychange.service.AgentMoneyChangeService;
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
* Created by Administrator on 2016-11-7.
*/
@Controller
@RequestMapping("/agentmoneychange")
public class AgentMoneyChangeController extends BaseController {
    @Autowired
    private AgentMoneyChangeService agentMoneyChangeService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneychange")
    public String listPage() {
        return "/agentmoneychange/agentmoneychange_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneychange:add")
    public String addPage() {
         return "/agentmoneychange/agentmoneychange_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneychange:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", agentMoneyChangeService.get(id));
        return "/agentmoneychange/agentmoneychange_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("agentmoneychange")
    public GridPager listData(AgentMoneyChangeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentMoneyChangeBean> entityList = agentMoneyChangeService.queryList(entity);
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
    @RequiresPermissions("agentmoneychange:add")
    public ExecuteResult save(@Valid AgentMoneyChangeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentMoneyChangeService.insert(entity);
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
    @RequiresPermissions("agentmoneychange:edit")
    public ExecuteResult update(@Valid AgentMoneyChangeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentMoneyChangeService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("agentmoneychange:delete")
    public ExecuteResult delete(String id) {
        agentMoneyChangeService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
