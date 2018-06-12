package com.rmkj.microcap.modules.moneyrecord.controller;/**
 * Created by Administrator on 2017/10/18.
 */

import com.rmkj.microcap.modules.moneyrecord.service.GuoFuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author k
 * @create -10-18-16:40
 **/

@Controller
@RequestMapping(value = "/moneyout/guofu")
public class GuoFuController {

    @Autowired
    private GuoFuService guoFuService;

    @ResponseBody
    @RequestMapping(value = "/pass")
    public String guoFuPass(String ids){
        return guoFuService.reviewPass(ids);
    }
}
