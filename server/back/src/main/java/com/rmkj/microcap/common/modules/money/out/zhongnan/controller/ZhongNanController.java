package com.rmkj.microcap.common.modules.money.out.zhongnan.controller;

import com.rmkj.microcap.common.modules.money.out.zhongnan.bean.WithdrawalsCallBackReq;
import com.rmkj.microcap.common.modules.money.out.zhongnan.service.ZhongNanWithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/9/20.
 */
@RestController
@RequestMapping("/zhongnan/withdrawals")
public class ZhongNanController {

	@Autowired
	ZhongNanWithdrawalsService zhongNanWithdrawalsService;

	@RequestMapping(value = "/callback")
	public ResponseEntity callback(WithdrawalsCallBackReq withdrawalsCallBackReq){
		return zhongNanWithdrawalsService.callBack(withdrawalsCallBackReq);
	}

}
