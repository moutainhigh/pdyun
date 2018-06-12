package com.rmkj.microcap.modules.ReturnMoneyOut.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.ShouXinYiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/1/10.
 */
@Controller
@RequestMapping("/returnmoneyout/shouxinyi")
public class ShouXinYiController extends BaseController {
    @Autowired
    private ShouXinYiService returnMoneyOutService1;
    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String pass(String ids){
        return returnMoneyOutService1.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return returnMoneyOutService1.reviewNoPass(ids, failureReason);
    }

}
