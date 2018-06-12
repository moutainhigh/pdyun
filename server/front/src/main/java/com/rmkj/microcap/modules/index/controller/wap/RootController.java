package com.rmkj.microcap.modules.index.controller.wap;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.*;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.bean.annotation.WeiXinLogin;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.index.service.RootService;
import com.rmkj.microcap.modules.money.entity.CashCoupon;
import com.rmkj.microcap.modules.money.service.CashCouponService;
import com.rmkj.microcap.modules.user.bean.LoginBean;
import com.rmkj.microcap.modules.user.bean.RegBean;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renwp on 2016/10/19.
 */
@Controller
@RequestMapping("${wap}")
public class RootController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RootService rootService;

    @Autowired
    private CashCouponService cashCouponService;

    @RequestMapping("/debug")
    public String debug(Model model){
        return "/wap/debug";
    }

    @RequestMapping("/invalid")
    public String invalid(Model model){
        return "/wap/invalid";
    }

    @RequestMapping("/error/404")
    public String error404(Model model){
        return "/error/404";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg(RegBean regBean,Model model){

        if(StringUtils.isBlank(regBean.getAgentInviteCode()) && StringUtils.isBlank(regBean.getUserId())){
            regBean.setAgentInviteCode("");
            regBean.setUserId("");
        }else if(StringUtils.isBlank(regBean.getUserId())){
            regBean.setUserId("");
        }
        return "/wap/reg";
    }

    @RequestMapping(value = "/registProcessIntroduce", method = RequestMethod.GET)
    @LoginAnnot
    public String regInstuction(){
        return "/wap/user/registProcessIntroduce";
    }
    @RequestMapping(value = "/tradeProcessIntroduce", method = RequestMethod.GET)
    @LoginAnnot
    public String tradeProcessIntroduce(){
        return "/wap/user/tradeProcessIntroduce";
    }
    @RequestMapping(value = "/withdrawalsProcessIntroduce", method = RequestMethod.GET)
    @LoginAnnot
    public String withdrawalsProcessIntroduce(){
        return "/wap/user/withdrawalsProcessIntroduce";
    }


    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "/wap/login";
    }

    @RequestMapping("/home")
    @WeiXinLogin
    @LoginAnnot
    public String index(Model model){
        //return rootService.toTrade(model);
        return rootService.toMarketTrade(model);
    }

    @RequestMapping("/market")
    @LoginAnnot
    public String toMarketTrade(Model model){
        return rootService.toMarketTrade(model);
    }

    /**
     * wap端注册注册
     * @param
     * @param
     * @return
     */
//    @RequestMapping(value = "/reg", method = RequestMethod.POST)
//    @ResponseBody
////    public ExecuteResult reg(@RequestBody Map<String,Object> m, HttpServletRequest request) {
//    public ExecuteResult reg(RegBean regBean, HttpServletRequest request) {
//        System.out.println("====================>>> in reg !");
//        Map<String,Object> map = new HashMap<>();
//

//        if(org.springframework.util.StringUtils.isEmpty(regBean)){
//            return new ExecuteResult(StatusCode.PARAM_EMPTY_ERROR,map);
//        }else{
//            ResponseEntity responseEntity = userService.reg(regBean);
//            System.out.println("=>responseEntity.getStatusCode()="+responseEntity.getStatusCode());
//            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
//                ResultError resultError = (ResultError)responseEntity.getBody();
//                map.put("error",resultError.getError());
//                return new ExecuteResult(resultError.getCode(),map);
//            }else{
//                Cookie cookie = null;
//                try {
//                    cookie = new Cookie(Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(regBean), "utf-8"));
//                    cookie.setPath(request.getContextPath().concat("/"));
//                    //response.addCookie(cookie);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                map.put("cookie",cookie);
//            }
//        }
//        return new ExecuteResult(StatusCode.OK,map);
//    }

// else {
                // 注册成功，写入客户端cookie
//               AuthEntity user = (AuthEntity) responseEntity.getBody();
//               Cookie cookie = null;
//                try {
//                    cookie = new Cookie(Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(regBean), "utf-8"));
//                    cookie.setPath(request.getContextPath().concat("/"));
//                    //response.addCookie(cookie);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                model.addAttribute("isNeedGiveCashCoupon",regBean);
//                map.put("cookie",cookie);
                //return rootService.toHome(model);
                //跳转到首页
                //return rootService.toTrade(model);
                //跳转到扫面二维码页面
//                return rootService.QRCodeImage(model, request,response);
//            }
//        }

//        return new ExecuteResult(StatusCode.SERVER_ERROR,map);
//        return "/wap/reg";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid LoginBean loginBean, Errors errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", parseErrors(errors).getBody());
        } else {
            String validateCode = (String) request.getSession().getAttribute("validateCode");
            String code = loginBean.getRandomCode();
            if(StringUtils.isBlank(code) || !validateCode.equalsIgnoreCase(code)){
                model.addAttribute("errors",  new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST).getBody());
                model.addAttribute("mobile", loginBean.getMobile());
                return "/wap/login";
            }
            ResponseEntity responseEntity = userService.login(loginBean, request.getServerName(),null);
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                ResponseErrorEntity er = (ResponseErrorEntity) responseEntity;
                // 停用账号
                if(er.getCode().equals(StatusCode.USER_CLOSE)){
                    return "/wap/invalid";
                }
                model.addAttribute("errors", responseEntity.getBody());
                model.addAttribute("loginBean", loginBean);
            } else {
                // 登录成功，写入客户端cookie
                AuthEntity user = (AuthEntity) responseEntity.getBody();
                try {
                    Cookie cookie = null;
                    cookie = new Cookie(Constants.Http.AUTHORIZATION, URLEncoder.encode(JSON.toJSONString(user), "utf-8"));
                    cookie.setPath(request.getContextPath().concat("/"));
                    // 五年
                    cookie.setMaxAge(5*365*24*60*60);
                    response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AuthContext.setCurAuth(user);
                userService.updateLogin(Utils.getClientIp(request));
                //return rootService.toHome(model);
                //return rootService.toTrade(model);
                return rootService.toMarketTrade(model);
            }
        }
        return "/wap/login";
    }

    @RequestMapping(value = "/pay/recharge")
    @LoginAnnot
    public String recharge(String from, Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        model.addAttribute("from", from);
        return "wap/pay/recharge";
    }

    @RequestMapping(value = "/firstPartResetTradePassWord")
    public String modifyLoginPassWord(@RequestParam String mobile,Model model){
        model.addAttribute("mobile",mobile);
        return "/wap/firstPartResetTradePassWord";
    }
    @RequestMapping(value = "/secondPartResetTradePassWord")
    public String secondPartResetTradePassWord(@RequestParam String mobile,String validCode,Model model){
        model.addAttribute("mobile",mobile);
        model.addAttribute("validCode",validCode);
        return "/wap/secondPartResetTradePassWord";
    }


    @RequestMapping(value = "/toTrade")
    @LoginAnnot
    public String toTradePage(){
        return "/wap/trade";
    }

    @RequestMapping(value = "/account")
    @LoginAnnot
    public String toAccount(Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        return "/wap/user/account";
    }

    @RequestMapping(value = "/yizhifu/pay/yiZhiFuReturn")
    @LoginAnnot
    public String yiZhiFuReturn(String from, Model model){
        return "wap/pay/recharge";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = null;
        cookie = new Cookie(Constants.Http.AUTHORIZATION, "");
        cookie.setPath(request.getContextPath().concat("/"));
        response.addCookie(cookie);
        return "/wap/login";
    }

    /**
     * TODO 输出会员代理二维码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/displayQRCode")
    public void DisplayQRCodeImage(@Valid String userId, HttpServletRequest request, HttpServletResponse response){
        //查询当前注册用户的会员单位
        String imageName = userService.getAgentInviteCode(userId);
        Utils.getFileImage(ProjectConstants.AGENT_QRCODE + imageName + ProjectConstants.AGENT_QRCODE_TYPE, response);
    }

    @RequestMapping(value = "dowload", method = RequestMethod.GET)
    public String load(){
        return "/wap/RQCodeImage";
    }

}
