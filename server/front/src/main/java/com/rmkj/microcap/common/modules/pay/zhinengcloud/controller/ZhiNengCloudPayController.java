package com.rmkj.microcap.common.modules.pay.zhinengcloud.controller;/**
 * Created by Administrator on 2017/12/18.
 */

import com.rmkj.microcap.common.modules.pay.zhinengcloud.entity.ZhiNengNotifyBean;
import com.rmkj.microcap.common.modules.pay.zhinengcloud.service.ZhiNengCloudPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author k
 * @create -12-18-11:50
 **/
@Controller
@RequestMapping(value = "/${v1}/zhinengcloud")
public class ZhiNengCloudPayController {

    @Autowired
    private ZhiNengCloudPayService zhiNengCloudPayService;

    @RequestMapping(value = "/pay/callback", method = RequestMethod.POST)
    public ResponseEntity callBack(ZhiNengNotifyBean zhiNengNotifyBean){
        return zhiNengCloudPayService.callBack(zhiNengNotifyBean);
    }
}
