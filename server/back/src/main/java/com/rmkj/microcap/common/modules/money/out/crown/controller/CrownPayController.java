package com.rmkj.microcap.common.modules.money.out.crown.controller;/**
 * Created by Administrator on 2017/11/21.
 */

import com.rmkj.microcap.common.modules.money.out.crown.entity.CrownNotifyBean;
import com.rmkj.microcap.common.modules.money.out.crown.service.CrownPayService;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.CrownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author k
 * @create -11-21-15:25
 **/
@Controller
@RequestMapping(value = "/pay/crown")
public class CrownPayController {

    @Autowired
    private CrownPayService crownPayService;


    @Autowired
    private CrownService crownService;

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public ResponseEntity moneyOutCallBack(CrownNotifyBean crownNotifyBean){
        return crownPayService.crownPayNotify(crownNotifyBean);
    }

    @RequestMapping(value = "/returnCallback", method = RequestMethod.POST)
    public ResponseEntity returnMoneyOutCallBack(CrownNotifyBean crownNotifyBean){
        return crownPayService.crownReturnMoneyNotify(crownNotifyBean);
    }
}
