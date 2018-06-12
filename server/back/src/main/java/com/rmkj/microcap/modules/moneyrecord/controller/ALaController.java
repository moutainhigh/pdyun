package com.rmkj.microcap.modules.moneyrecord.controller;/**
 * Created by Administrator on 2017/4/7.
 */

import com.rmkj.microcap.modules.moneyrecord.service.ALaService;
import com.rmkj.microcap.modules.moneyrecord.service.MoneyOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author k
 * @create -04-07-11:32
 **/

@Controller
@RequestMapping(value = "/moneyout/ala")
public class ALaController {
    @Autowired
    private MoneyOutService moneyOutService;

    @Autowired
    private ALaService aLaService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String pass(String ids){
        return aLaService.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return moneyOutService.reviewNoPass(ids, failureReason);
    }
}
