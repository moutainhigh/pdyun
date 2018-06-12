package com.rmkj.microcap.modules.chanong.index.controller;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.*;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.chanong.index.service.IndexService;
import com.rmkj.microcap.modules.chanong.user.bean.RegisterBean;
import com.rmkj.microcap.modules.user.bean.LoginBean;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinghao on 2018/4/23.
 */
@Controller
@RequestMapping("/api/index")
public class IndexController {
    private static Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private IndexService indexService;

    /**
     * 获取当前价格
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCurPrice", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getCurPrice(String id) {
        return new ExecuteResult(StatusCode.OK, indexService.getCurPrice(id));
    }

    /**
     * 获取公告
     * @param type
     * @return
     */
    @RequestMapping(value = "/getNewsFlash", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getNewsFlash(String type) {
        return new ExecuteResult(StatusCode.OK, indexService.getNewsFlash(type));
    }

    /**
     * 获取单条公告
     * @param id
     * @return
     */
    @RequestMapping(value = "/getNewsById", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getNewsById(String id) {
        return new ExecuteResult(StatusCode.OK, indexService.getNewsById(id));
    }


}
