package com.rmkj.microcap.common.interceptor;

import com.rmkj.microcap.common.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by zhangbowen on 2015/12/4.
 * log日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor {
    /**
     * 日志对象
     */
    protected Logger logger = Logger.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String method = httpServletRequest.getMethod();
        StringBuffer params = new StringBuffer();
        Enumeration<?> temp = httpServletRequest.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = httpServletRequest.getParameter(en);
                if(StringUtils.isNotBlank(value)){
                    params.append("&").append(en).append("=").append(value);
                }
            }
        }
        if (params.length() > 0) {
            params = params.replace(0,1,"?");
        }
        logger.debug(new StringBuffer("访问者ip:")
                .append(Utils.getClientIp(httpServletRequest))
                .append(":")
                .append(httpServletRequest.getRemotePort())
                .append("\n")
                .append(method)
                .append(": ")
                .append(httpServletRequest.getRequestURI())
                .append(params)
        );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
