package com.rmkj.microcap.common.modules.pay.mingfu.controller;/**
 * Created by Administrator on 2018/1/18.
 */

import com.rmkj.microcap.common.modules.pay.mingfu.service.MingFuPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author k
 * @create -01-18-11:02
 **/
@Controller
@RequestMapping(value = "${v1}/mingfu/pay")
public class MingFuPayController {

    @Autowired
    private MingFuPayService mingFuPayService;

    @RequestMapping(value = "/callback")
    public ResponseEntity callback(String message){
        return mingFuPayService.callback(message);
    }
}
