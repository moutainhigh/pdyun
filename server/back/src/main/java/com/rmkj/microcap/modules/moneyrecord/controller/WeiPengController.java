package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.modules.moneyrecord.service.WeiPengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by renwp on 2017/3/20.
 */
@Controller
@RequestMapping("/moneyout/weipeng")
public class WeiPengController {

    @Autowired
    private WeiPengService weiPengService;
    /**
     * 出金 审核 通过时-威鹏代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ExecuteResult outPast(String ids) {
        return weiPengService.outPastIn(ids, 0);
    }
}
