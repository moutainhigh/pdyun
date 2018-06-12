package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.modules.moneyrecord.service.ZhongNanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/9/20.
 */
@Controller
@RequestMapping("/moneyout/zhongnan")
public class ZhongNanController {

	@Autowired
	private ZhongNanService zhongNanService;

	@ResponseBody
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public String pass(String ids){
		return zhongNanService.reviewPass(ids);
	}

	@ResponseBody
	@RequestMapping(value = "/nopass", method = RequestMethod.POST)
	public String nopass(String ids, String failureReason){
		return zhongNanService.reviewNoPass(ids, failureReason);
	}

}
