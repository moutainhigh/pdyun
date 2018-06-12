package com.rmkj.microcap.common.modules.shiro.filter;

import com.rmkj.microcap.common.modules.shiro.constants.Constants;
import com.rmkj.microcap.common.utils.ContextUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 表单验证（包含验证码）过滤类
 */
public class LoginAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (request.getAttribute(Constants.ERROR_MESSAGE) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = ContextUtils.getRemoteAddr(WebUtils.toHttp(request));
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request,
                                        ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(),message;
        if (IncorrectCredentialsException.class.getName().equals(className) || UnknownAccountException.class.getName().equals(className)) {
            message = Constants.LOGIN_ERROR;
        } else {
            message = Constants.SYSTEM_ERROR;
            e.printStackTrace(); // 输出到控制台
            if(e instanceof AuthenticationException){
                message = e.getMessage();
            }
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(Constants.ERROR_MESSAGE, message);
        return true;
    }

}