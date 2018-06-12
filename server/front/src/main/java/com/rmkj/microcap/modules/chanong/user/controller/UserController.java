package com.rmkj.microcap.modules.chanong.user.controller;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.*;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.chanong.sub.dao.SubDao;
import com.rmkj.microcap.modules.chanong.user.bean.ChangePwdBean;
import com.rmkj.microcap.modules.chanong.user.bean.ForgetPwdBean;
import com.rmkj.microcap.modules.chanong.user.bean.RegisterBean;
import com.rmkj.microcap.modules.user.bean.LoginBean;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinghao on 2018/4/23.
 */
@Controller
@RequestMapping("/api/user")
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param registerBean
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult reg(RegisterBean registerBean, HttpServletRequest request) {
        logger.info("=> in reg !");
        Map<String,Object> map = new HashMap<>();

        if(org.springframework.util.StringUtils.isEmpty(registerBean)){
            return new ExecuteResult(StatusCode.PARAM_EMPTY_ERROR,map);
        }else{
            ResponseEntity responseEntity = userService.reg(registerBean);
            logger.info("=>responseEntity.getStatusCode()="+responseEntity.getStatusCode());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                ResultError resultError = (ResultError)responseEntity.getBody();
                map.put("error",resultError.getError());
                return new ExecuteResult(resultError.getCode(),map);
            }else{
                AuthEntity user = (AuthEntity) responseEntity.getBody();
                Cookie cookie = null;
                try {
                    System.out.println("JSON.toJSONString(user)="+JSON.toJSONString(user));
                    cookie = new Cookie(Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(user), "utf-8"));
                    cookie.setPath(request.getContextPath().concat("/"));
                    //response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put("cookie",cookie);
            }
        }
        return new ExecuteResult(StatusCode.OK,map);
    }

    /**
     * 登陆
     * @param loginBean
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult login(LoginBean loginBean, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        ResponseEntity responseEntity = userService.login(loginBean, request.getServerName(),map);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResponseErrorEntity er = (ResponseErrorEntity) responseEntity;
            // 停用账号
            if(er.getCode().equals(StatusCode.USER_CLOSE)){
                return new ExecuteResult(StatusCode.USER_CLOSE,map);
            }
            ResultError resultError = (ResultError)responseEntity.getBody();
            map.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),map);
        } else {
            // 登录成功，写入客户端cookie
            AuthEntity user = (AuthEntity) responseEntity.getBody();
            try {
                Cookie cookie = null;
                System.out.println("JSON.toJSONString(user)="+JSON.toJSONString(user));
                cookie = new Cookie(Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(user), "utf-8"));
                cookie.setPath(request.getContextPath().concat("/"));
                // 五年
                cookie.setMaxAge(5*365*24*60*60);
                map.put("cookie",cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            AuthContext.setCurAuth(user);
            userService.updateLogin(Utils.getClientIp(request));
        }
        return new ExecuteResult(StatusCode.OK,map);
    }

    /**
     * 忘记密码
     * @param forgetPwdBean
     * @return
     */
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult forgetPassword(ForgetPwdBean forgetPwdBean) {
        Map<String,Object> map = new HashMap<>();
        ResponseEntity responseEntity = userService.forgetPassword(forgetPwdBean);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResultError resultError = (ResultError)responseEntity.getBody();
            map.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),map);
        }
        return new ExecuteResult(StatusCode.OK,map);
    }

    /**
     * 修改密码
     * @param changePwdBean
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult updatePassword(ChangePwdBean changePwdBean) {
        Map<String,Object> map = new HashMap<>();
        ResponseEntity responseEntity = userService.updatePassword(changePwdBean);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResultError resultError = (ResultError)responseEntity.getBody();
            map.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),map);
        }
        return new ExecuteResult(StatusCode.OK,map);
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult test( HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        System.out.println("1111");
        return new ExecuteResult(StatusCode.OK,map);
    }

    /**
     * 二维码url
     * @paramuser
     */
    @RequestMapping(value = "/getShareImg", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getSubRQCode(){
        return new ExecuteResult(StatusCode.OK,userService.getSubRQCode());
    }

}
