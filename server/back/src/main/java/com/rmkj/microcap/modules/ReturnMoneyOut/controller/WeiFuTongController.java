package com.rmkj.microcap.modules.ReturnMoneyOut.controller;

import com.rmkj.microcap.modules.ReturnMoneyOut.service.WeiFuTongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by renwp on 2017/3/20.
 */
@Controller
@RequestMapping("/returnmoneyout/weifutong")
public class WeiFuTongController {

    @Autowired
    private WeiFuTongService weiFuTongService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String pass(String ids){
        return weiFuTongService.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return weiFuTongService.reviewNoPass(ids, failureReason);
    }

}
