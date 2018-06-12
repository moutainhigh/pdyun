/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.modules.shiro.SystemAuthorizingRealm;
import com.rmkj.microcap.common.modules.shiro.constants.Constants;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.modules.sys.utils.ValidateCodeServlet;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录Controller
 *
 * @author
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 管理登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        if (principal != null) {
            return "redirect:/";
        }
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model) {
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        if (principal != null) {
            return "redirect:/";
        }
        String message = (String) request.getAttribute(Constants.ERROR_MESSAGE);
        model.addAttribute(Constants.ERROR_MESSAGE, message);
        // 验证失败清空验证码
        request.getSession().removeAttribute(ValidateCodeServlet.CAPTCHA);
        return "/login";
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/layout/index";
    }
}
