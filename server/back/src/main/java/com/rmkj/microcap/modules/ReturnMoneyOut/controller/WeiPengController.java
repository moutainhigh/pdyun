package com.rmkj.microcap.modules.ReturnMoneyOut.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.WeiPengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by renwp on 2017/3/20.
 */
@Controller
@RequestMapping("/returnmoneyout/weipeng")
public class WeiPengController {

    @Autowired
    private WeiPengService weiPengService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public synchronized ExecuteResult pass(String ids){
        if(ids.indexOf(",") == -1){
            return weiPengService.returnOutPastIn(ids, 0);
        }else{
            return weiPengService.returnOutPastIn(ids, 0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return weiPengService.reviewNoPass(ids, failureReason);
    }

}
