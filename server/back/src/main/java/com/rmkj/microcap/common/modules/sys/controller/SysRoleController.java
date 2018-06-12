package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleMenuBean;
import com.rmkj.microcap.common.modules.sys.service.SysMenuService;
import com.rmkj.microcap.common.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@Controller
@RequestMapping("/sysrole")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService menuService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/sysrole/sysrole_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage() {
        return "/sysrole/sysrole_add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Map<String, Object> model) {
        model.put("model", sysRoleService.get(id));
        return "/sysrole/sysrole_edit";
    }

    @RequestMapping(value = "/setMenu", method = RequestMethod.GET)
    public String roleMenuPage(String id, Map<String, Object> model) {
        model.put("roleId", id);
        return "/sysrole/sysrole_menu";
    }


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(SysRoleBean entity) {
        List<SysRoleBean> entityList = sysRoleService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid SysRoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysRoleService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExecuteResult update(@Valid SysRoleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysRoleService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ExecuteResult delete(String id) {
        sysRoleService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/menu_trees", method = RequestMethod.POST)
    public Map<String, Object> menuTreeData(String roleId) {
//        System.out.println("---------------------"+roleId);
        Map<String, Object> map = new HashMap<>();
        map.put("menuTrees", menuService.findMenuTree());
        map.put("selectMenuIds", sysRoleService.findSelectMenuByRoleId(roleId));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/saveRoleMenu", method = RequestMethod.POST)
    public ExecuteResult saveRoleMenu(String roleId, @RequestBody List<SysRoleMenuBean> sysRoleMenuBeans) {
        sysRoleService.insertRoleMenu(roleId, sysRoleMenuBeans);
        return new ExecuteResult(StatusCode.OK);
    }
}
