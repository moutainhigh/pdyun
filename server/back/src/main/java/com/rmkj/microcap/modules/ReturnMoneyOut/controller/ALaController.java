package com.rmkj.microcap.modules.ReturnMoneyOut.controller;/**
 * Created by Administrator on 2017/4/7.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.ALaReturnMoneyService;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 啊啦代付
 * @author k
 * @create -04-07-14:28
 **/

@Controller
@RequestMapping(value = "/returnmoneyout/ala")
public class ALaController {
    private final static Logger Log = Logger.getLogger(ALaController.class);

    @Autowired
    private BaseService baseService;

    @Autowired
    private ALaReturnMoneyService aLaReturnMoneyService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String pass(String ids){
        return aLaReturnMoneyService.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return baseService.reviewNoPass(ids, failureReason);
    }
}
