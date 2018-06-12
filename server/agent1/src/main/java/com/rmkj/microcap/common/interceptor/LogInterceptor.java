package com.rmkj.microcap.common.interceptor;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//        if(ProjectConstants.PRO_DEBUG){
            String method = httpServletRequest.getMethod();
            logger.info(new StringBuffer("访问者ip:")
                    .append(httpServletRequest.getRemoteHost())
                    .append(":")
                    .append(httpServletRequest.getRemotePort())
                    .append("\n")
                    .append(method)
                    .append(": ")
                    .append(httpServletRequest.getRequestURI())
            );
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
