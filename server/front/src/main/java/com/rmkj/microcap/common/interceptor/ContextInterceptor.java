package com.rmkj.microcap.common.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.AuthEntity;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.bean.annotation.WeiXinLogin;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.sys.service.TokenService;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.ContextUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 处理登录信息
 * 处理controller的方法上的注解标识
 */
public class ContextInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = Logger.getLogger(ContextInterceptor.class);

    @Autowired
    private UserService userService;

    static boolean isWap(HttpServletRequest request){
        // 微信wap端 从cookie中取
        // 规则：wap端的页面请求和来自wap端页面的ajax请求（通过referer判断）
         String referer = request.getHeader("Referer") == null ? "" : request.getHeader("Referer");
        if(!referer.equals("")){
            referer = referer.substring(referer.indexOf("/", referer.indexOf("//")+2));
        }
        return request.getRequestURI().startsWith(request.getContextPath().concat(ProjectConstants.API))
                || referer.startsWith(request.getContextPath().concat(ProjectConstants.API));
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        ContextUtils.setReqRes(request, response);

        if (handler instanceof HandlerMethod) {
            logger.info("handler " + request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginAnnot annotation = method.getAnnotation(LoginAnnot.class);
            //
            String loginType = annotation == null ? "" : annotation.value();

            /********************** 抓取session  ***************************/
            // 从header中取
            String headerName = Constants.LoginType.AGENT.equals(loginType) ? Constants.Http.AUTHORIZATION_AGENT : Constants.Http.AUTHORIZATION;
            String authStr = request.getHeader(headerName);

            if(isWap(request)){
               /* authStr = null;*/
                Cookie[] cookies = request.getCookies();
                if(cookies != null){
                    for(Cookie cookie : cookies){
                        if(headerName.equals(cookie.getName())){
                            authStr = URLDecoder.decode(cookie.getValue(), "utf-8");
                            break;
                        }
                    }
                }
            }

            AuthEntity authEntity = null;
            if (authStr != null) {
                // 设置当前请求者的权限标识
                try {
                    authStr = URLDecoder.decode(authStr, "UTF-8");
                    authEntity = JSON.parseObject(authStr, AuthEntity.class);
                    authEntity.setIp(Utils.getClientIp(request));
                    AuthContext.setCurAuth(authEntity);
                } catch (Exception e) {

                }
            } else {
                authEntity = new AuthEntity();
                authEntity.setIp(Utils.getClientIp(request));
                AuthContext.setCurAuth(authEntity);
            }
            /********************** 抓取session  ***************************/


            // 处理微信第三方登录
            // 获取openid并登录（注册）
           /* WeiXinLogin weiXinLogin = method.getAnnotation(WeiXinLogin.class);
            if (ProjectConstants.WEI_XIN_LOGIN && weiXinLogin != null) {
                String code = request.getParameter("code");
                logger.info("weiXinLogin code=" + code);
                if (code != null && !"".equals(code)) {
                    // 重新认证
                    authEntity = new AuthEntity();
                    authEntity.setIp(Utils.getClientIp(request));
                    AuthContext.setCurAuth(authEntity);

                    authEntity.setTerminal(Constants.Terminal.TERMINAL_WAP);

                    Object obj = null;
                    if(Constants.LoginType.AGENT.equals(loginType)){

                    }else{
                        obj = userService.loginWeiXin(code);
                    }
                    String suffix = "&ah=false";
                    if(obj != null){
                        boolean isReg = false;
                        String token = null;

                        AuthEntity entity = new AuthEntity();

                        if(Constants.LoginType.AGENT.equals(loginType)){

                        }else{
                            User user = (User) obj;
                            if(!Constants.USER_STATUE.VALID.equals(user.getStatus().toString())){
                                response.sendRedirect(request.getContextPath().concat(ProjectConstants.WAP.concat("/invalid")));
                                return false;
                            }else{
                                // 自动登录
                                authEntity.setUserId(user.getId());
                                authEntity.setOpenId(user.getOpenId());

                                isReg = user.getMobile() != null && !"".equals(user.getMobile().trim());
                                token = user.getToken();
                            }

                            suffix = "&ah=".concat(Boolean.toString(!StringUtils.isEmpty(user.getAgentInviteCode())));
                        }

                        // 平台已注册
                        if(isReg){
                            authEntity.setToken(token);
                            entity.setToken(token);
                        }
                        // 加入cookie
                        entity.setUserId(authEntity.getUserId());
                        entity.setTerminal(authEntity.getTerminal());
                        Cookie cookie = new Cookie(Constants.LoginType.AGENT.equals(loginType)
                                ? Constants.Http.AUTHORIZATION_AGENT : Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(entity), "utf-8"));
                        cookie.setPath(request.getContextPath().concat("/"));
                        response.addCookie(cookie);

                        // 平台未注册
                        if(!isReg){
                            response.sendRedirect(request.getContextPath().concat(
                                    Constants.LoginType.AGENT.equals(loginType) ? ProjectConstants.WAP.concat(ProjectConstants.WAP_AGENT).concat("/reg?thirdReg=true").concat(suffix) : ProjectConstants.WAP.concat("/reg?thirdReg=true").concat(suffix)));
                            return false;
                        }else {
                            userService.updateLogin(Utils.getClientIp(request));
                        }
                    }
                }else{
                    // 没有code时，去微信取code
                    boolean success = TokenService.checkToken();
                    if(ProjectConstants.PRO_DEBUG){
                        if(!success){
                            // TODO debug模式 登录过期怎么办
                            response.sendRedirect(request.getContextPath().concat(ProjectConstants.WAP.concat("/debug")));
                            return false;
                        }
                    }else {
                        if(!success){

                            // 去微信登录获取用户信息 第一步
                            response.sendRedirect(Utils.formatStr(ProjectConstants.WEI_XIN_PAGE_ACCESS_URL,
                                    URLEncoder.encode(Constants.LoginType.AGENT.equals(loginType) ? ProjectConstants.WEI_XIN_REDIRECT_URI_AGENT : ProjectConstants.WEI_XIN_REDIRECT_URI, "utf-8")));
                            return false;
                        }
                    }
                }
            }*/
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ContextUtils.removeReqRes();
        //删除当前请求者的权限标识
        AuthContext.removeCurAuth();
    }
}
