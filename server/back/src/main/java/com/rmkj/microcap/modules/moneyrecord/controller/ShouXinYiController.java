package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.modules.moneyrecord.service.ShouXinYiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by renwp on 2017/1/5.
 *
 * 线下出金模式
 */
@Controller
@RequestMapping("/moneyout/shouxinyi")
public class ShouXinYiController extends BaseController {

    @Autowired
    private ShouXinYiService shouXinYiService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String pass(String ids){
        return shouXinYiService.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return shouXinYiService.reviewNoPass(ids, failureReason);
    }

}
