package com.rmkj.microcap.common.modules.pay.zhongnan.controller;

import com.rmkj.microcap.common.modules.pay.zhongnan.entity.ZhongNanNotifyReq;
import com.rmkj.microcap.common.modules.pay.zhongnan.service.ZhongNanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/9/20.
 */
@RestController
@RequestMapping("${v1}/zhongnan")
public class ZhongNanPayController {

	@Autowired
	private ZhongNanPayService zhongNanPayService;


	@RequestMapping("/pay/callback")
	public ResponseEntity callback(ZhongNanNotifyReq zhongNanNotifyReq){
		return zhongNanPayService.callback(zhongNanNotifyReq);
	}

//	@RequestMapping("/pay/check")
//	@LoginAnnot
//	public ResponseEntity check(ZhongNanWithdrawalsCheckBean zhongNanWithdrawalsCheckBean){
//		return zhongNanPayService.check(zhongNanWithdrawalsCheckBean);
//	}

}
