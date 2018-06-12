package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.sys.bean.SysMenuBean;
import com.rmkj.microcap.common.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
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
 * Created by zhangbowen on 2015-9-8.
 */
@Controller
@RequestMapping("/sysmenu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService menuService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/sysmenu/sysmenu_list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(String id, Model model) {
        model.addAttribute("parentId", id);
        return "/sysmenu/sysmenu_add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Map<String, Object> model) {
        model.put("model", menuService.get(id));
        return "/sysmenu/sysmenu_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<SysMenuBean> listData(SysMenuBean entity) {
        List<SysMenuBean> entityList = menuService.queryList(entity);
        GridPager g = new GridPager();
        g.setRows(entityList);
        if (StringUtils.isEmpty(entity.getId())) {
            SysMenuBean menuBean = new SysMenuBean();
            menuBean.setName("菜单列表");
            menuBean.setState("open");
            menuBean.setId("0");
            List<SysMenuBean> list = new ArrayList<>();
            menuBean.setParentId("0");
            List<SysMenuBean> menuBeanList = menuService.queryList(menuBean);
            menuBean.setChildren(menuBeanList);
            list.add(menuBean);
            return list;
        } else {
            entity.setParentId(entity.getId());
            return menuService.queryList(entity);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid SysMenuBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        menuService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExecuteResult update(@Valid SysMenuBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        menuService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ExecuteResult delete(String id) {
        menuService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
