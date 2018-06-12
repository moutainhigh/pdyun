package com.rmkj.microcap.common.modules.shiro.filter;

import com.rmkj.microcap.common.modules.shiro.constants.Constants;
import com.rmkj.microcap.common.modules.sys.utils.ValidateCodeServlet;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangbowen on 2016/5/18.
 */
public class CaptchaValidateFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        return ValidateCodeServlet.validate(httpServletRequest, httpServletRequest.getParameter(ValidateCodeServlet.CAPTCHA));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //验证码验证失败
        if(servletRequest.getParameter(ValidateCodeServlet.CAPTCHA)!=null){
            servletRequest.setAttribute(Constants.ERROR_MESSAGE, Constants.CAPTCHA_ERROR);
        }
        return true;
    }
}
