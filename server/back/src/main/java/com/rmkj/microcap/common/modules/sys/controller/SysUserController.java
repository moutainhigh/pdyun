package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysRoleBean;
import com.rmkj.microcap.common.modules.sys.bean.SysUserBean;
import com.rmkj.microcap.common.modules.sys.service.SysRoleService;
import com.rmkj.microcap.common.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbowen on 2015-9-8.
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/sysuser/sysuser_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(Model model) {
        model.addAttribute("roleList", sysRoleService.queryList(new SysRoleBean()));
        return "/sysuser/sysuser_add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Map<String, Object> model) {
        model.put("model", sysUserService.get(id));
        model.put("roleList", sysRoleService.queryList(new SysRoleBean()));
        model.put("selectRoleList", sysUserService.findSelectRoleByUserId(id));
        return "/sysuser/sysuser_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(SysUserBean entity) {
        List<SysUserBean> entityList = sysUserService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid SysUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysUserService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExecuteResult update(@Valid SysUserBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysUserService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ExecuteResult delete(String id) {
        sysUserService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
