package com.rmkj.microcap.modules.ReturnMoneyOut.controller;/**
 * Created by Administrator on 2017/11/21.
 */

import com.rmkj.microcap.modules.ReturnMoneyOut.service.CrownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author k
 * @create -11-21-16:07
 **/
@Controller
@RequestMapping(value = "/returnmoneyout/crown")
public class CrownController {

    @Autowired
    private CrownService crownService;

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public synchronized String fuoFuPass(String ids){
        return crownService.pass(ids);
    }
}
