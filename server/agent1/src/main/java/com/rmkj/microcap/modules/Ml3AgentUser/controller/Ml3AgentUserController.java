package com.rmkj.microcap.modules.Ml3AgentUser.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3AgentUser.entity.Ml3AgentUserBean;
import com.rmkj.microcap.modules.Ml3AgentUser.service.Ml3AgentUserService;
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
@RequestMapping("/Ml3AgentUser")
public class Ml3AgentUserController extends BaseController {
    @Autowired
    private Ml3AgentUserService ml3AgentUserService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("Ml3AgentUser")
    public String listPage() {
        return "/Ml3AgentUser/Ml3AgentUser_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("Ml3AgentUser:add")
    public String addPage() {
         return "/Ml3AgentUser/Ml3AgentUser_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("Ml3AgentUser:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3AgentUserService.get(id));
        return "/Ml3AgentUser/Ml3AgentUser_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("Ml3AgentUser")
    public GridPager listData(Ml3AgentUserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<Ml3AgentUserBean> entityList = ml3AgentUserService.queryList(entity);
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
    @RequiresPermissions("Ml3AgentUser:add")
    public ExecuteResult save(@Valid Ml3AgentUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentUserService.insert(entity);
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
    @RequiresPermissions("Ml3AgentUser:edit")
    public ExecuteResult update(@Valid Ml3AgentUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3AgentUserService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("Ml3AgentUser:delete")
    public ExecuteResult delete(String id) {
        ml3AgentUserService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
