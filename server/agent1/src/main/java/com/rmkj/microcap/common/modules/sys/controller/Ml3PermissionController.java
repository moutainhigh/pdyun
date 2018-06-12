package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3PermissionService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-11-15.
*/
@Controller
    @RequestMapping("/M3AgentPermission")
public class Ml3PermissionController extends BaseController {
    @Autowired
    private Ml3PermissionService ml3PermissionService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentPermission")
    public String listPage() {
        return "/M3AgentPermission/M3AgentPermission_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentPermission:add")
    public String addPage(String id, Model model) {
        model.addAttribute("parentId", id);
        return "/M3AgentPermission/M3AgentPermission_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("M3AgentPermission:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", ml3PermissionService.get(id));
        return "/M3AgentPermission/M3AgentPermission_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
//    */
//    @ResponseBody
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @RequiresPermissions("M3AgentPermission")
//    public GridPager listData(Ml3PermissionBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<Ml3PermissionBean> entityList = ml3PermissionService.queryList(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Ml3PermissionBean> listData(Ml3PermissionBean entity) {
        List<Ml3PermissionBean> entityList = ml3PermissionService.queryList(entity);
        GridPager g = new GridPager();
        g.setRows(entityList);
        if (StringUtils.isEmpty(entity.getId())) {
            Ml3PermissionBean menuBean = new Ml3PermissionBean();
            menuBean.setName("菜单列表");
            menuBean.setState("open");
            menuBean.setId("0");
            List<Ml3PermissionBean> list = new ArrayList<>();
            menuBean.setParentId("0");
            List<Ml3PermissionBean> menuBeanList = ml3PermissionService.queryList(menuBean);
            menuBean.setChildren(menuBeanList);
            list.add(menuBean);
            return list;
        } else {
            entity.setParentId(entity.getId());
            return ml3PermissionService.queryList(entity);
        }
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("M3AgentPermission:add")
    public ExecuteResult save(@Valid Ml3PermissionBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3PermissionService.insert(entity);
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
    @RequiresPermissions("M3AgentPermission:edit")
    public ExecuteResult update(@Valid Ml3PermissionBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        ml3PermissionService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("M3AgentPermission:delete")
    public ExecuteResult delete(String id) {
        ml3PermissionService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
