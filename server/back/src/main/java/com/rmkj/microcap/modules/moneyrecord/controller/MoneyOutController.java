package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.service.MoneyOutService;
import com.rmkj.microcap.modules.moneyrecord.service.MoneyRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by renwp on 2017/1/5.
 */
@Controller
@RequestMapping("/moneyout")
public class MoneyOutController extends BaseController {

    @Autowired
    private MoneyOutService moneyOutService;

    @Autowired
    private MoneyRecordService moneyRecordService;

    @ResponseBody
    @RequestMapping(value = "/pass2", method = RequestMethod.POST)
    public String pass(String ids){
        return moneyOutService.reviewPass(ids);
    }

    @ResponseBody
    @RequestMapping(value = "/nopass", method = RequestMethod.POST)
    public String nopass(String ids, String failureReason){
        return moneyOutService.reviewNoPass(ids, failureReason);
    }

    /**
     * 出金 审核 通过时-威鹏代付
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    //@RequiresPermissions("moneyrecord:out:pastpastin")
    public ExecuteResult outPast(String ids) {
        if(ids.indexOf(",") == -1){
            return moneyRecordService.outPastIn(ids, 0);
        }else{
            return moneyRecordService.outPastIn(ids, 0);
        }
    }
}
