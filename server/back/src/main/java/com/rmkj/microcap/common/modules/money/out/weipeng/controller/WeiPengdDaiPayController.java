package com.rmkj.microcap.common.modules.money.out.weipeng.controller;/**
 * Created by Administrator on 2017/3/8.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.Config;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPayAsyncResultBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.bean.WeiPengDaiPayResultBean;
import com.rmkj.microcap.common.modules.money.out.weipeng.service.WeiPengDaiPayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 威鹏代付异步通知
 * @author k
 * @create -03-08-9:01
 **/
@Controller
@RequestMapping(value = "/weiPengDaiPay")
public class WeiPengdDaiPayController extends BaseController{

    private final static Logger logger = Logger.getLogger(WeiPengdDaiPayController.class);

    @Autowired
    private WeiPengDaiPayService weiPengDaiPayService;

    @RequestMapping(value = "/getDaiPayAsyncResult", method = RequestMethod.POST)
    public String getWeiPengDaiPayAsyncResult(WeiPengDaiPayAsyncResultBean weiPengDaiPayAsyncResultBean){
        return weiPengDaiPayService.getWeiPengDaiPayAsyncResult(weiPengDaiPayAsyncResultBean);
    }

    @RequestMapping(value = "/getDaiPayAsyncResultReturnMoney", method = RequestMethod.POST)
    public synchronized String getDaiPayAsyncResultReturnMoney(WeiPengDaiPayAsyncResultBean weiPengDaiPayAsyncResultBean){
        return weiPengDaiPayService.getDaiPayAsyncResultReturnMoney(weiPengDaiPayAsyncResultBean);
    }
}
