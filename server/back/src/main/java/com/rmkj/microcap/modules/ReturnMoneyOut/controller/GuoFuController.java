package com.rmkj.microcap.modules.ReturnMoneyOut.controller;/**
 * Created by Administrator on 2017/11/16.
 */

import com.rmkj.microcap.modules.ReturnMoneyOut.service.GuoFuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author k
 * @create -11-16-12:06
 **/
@Controller
@RequestMapping(value = "/returnmoneyout/guofu")
public class GuoFuController {
    private final Logger Log = Logger.getLogger(getClass());

    @Autowired
    private GuoFuService guoFuService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String fuoFuPass(String ids){
        return guoFuService.pass(ids);
    }


}
