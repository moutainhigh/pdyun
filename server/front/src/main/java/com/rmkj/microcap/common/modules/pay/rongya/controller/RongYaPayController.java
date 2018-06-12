package com.rmkj.microcap.common.modules.pay.rongya.controller;/**
 * Created by Administrator on 2017/12/29.
 */

import com.rmkj.microcap.common.modules.pay.rongya.bean.RongYaNotifyBean;
import com.rmkj.microcap.common.modules.pay.rongya.service.RongYaPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author k
 * @create -12-29-14:38
 **/
@Controller
@RequestMapping(value = "${v1}/rongya")
public class RongYaPayController {

    @Autowired
    private RongYaPayService rongYaPayService;

    @RequestMapping(value = "/pay/callback", method = RequestMethod.POST)
    public ResponseEntity callback(RongYaNotifyBean entity){
        return rongYaPayService.callback(entity);
    }
}
