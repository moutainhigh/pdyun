package com.rmkj.microcap.common.modules.pay.huanxun.controller;/**
 * Created by Administrator on 2018/5/3.
 */

import com.rmkj.microcap.common.modules.pay.huanxun.service.HuanXunPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author k
 * @create -05-03-9:33
 **/
@Controller
@RequestMapping(value = "${v1}/huanxun")
public class HuanXunPayController {

    @Autowired
    private HuanXunPayService huanXunPayService;

    @RequestMapping(value = "/pay/callback", method = RequestMethod.POST)
    public ResponseEntity callback(String paymentResult){
        return huanXunPayService.callback(paymentResult);
    }
}
