package com.rmkj.microcap.modules.moneyrecord.controller;/**
 * Created by Administrator on 2017/11/21.
 */

import com.rmkj.microcap.modules.moneyrecord.service.CrownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author k
 * @create -11-21-13:07
 **/
@Controller
@RequestMapping(value = "/moneyout/crown")
public class CrownController {

    @Autowired
    private CrownService crownService;

    @ResponseBody
    @RequestMapping(value = "/pass")
    public String guoFuPass(String ids, HttpServletRequest request){
        return crownService.reviewPass(ids, request);
    }
}
