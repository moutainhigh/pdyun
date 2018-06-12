package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.modules.sys.bean.Ml3PermissionBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3PermissionService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    @Autowired
    private Ml3AgentService ml3AgentService;
    @Autowired
    private Ml3PermissionService ml3PermissionService;
    @RequestMapping(value = "/editors", method = RequestMethod.GET)
    public String indexMain(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/editors";
    }

    @RequestMapping(value = "/menulist", method = RequestMethod.GET)
    public String menuList(Model model) {
        //获取该用户所拥有权限的菜单
        List<Ml3PermissionBean> sysMenuBeanList = UserUtils.getMenuList();
        model.addAttribute("menuList", sysMenuBeanList);
        return "/layout/menu";
    }

    @RequestMapping(value = "/dashboard/index", method = RequestMethod.GET)
    public String dashboard()
    {
        return "/dashboard/index";
    }
}

