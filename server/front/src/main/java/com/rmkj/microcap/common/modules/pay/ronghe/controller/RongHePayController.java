package com.rmkj.microcap.common.modules.pay.ronghe.controller;/**
 * Created by Administrator on 2018/1/12.
 */

import com.rmkj.microcap.common.modules.pay.ronghe.bean.RongHeNotify;
import com.rmkj.microcap.common.modules.pay.ronghe.service.RongHePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author k
 * @create -01-12-15:34
 **/
@Controller
@RequestMapping(value = "/ronghe/pay")
public class RongHePayController {

    @Autowired
    private RongHePayService rongHePayService;

    @RequestMapping(value = "/callback")
    public ResponseEntity callback(RongHeNotify entity){
        return rongHePayService.callback(entity);
    }
}
