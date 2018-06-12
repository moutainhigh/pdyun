package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.modules.moneyrecord.service.RongYaService;
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
@RequestMapping("/moneyout/rongya")
public class RongYaController {

	@Autowired
	private RongYaService rongYaService;

	@ResponseBody
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public String pass(String ids){
		return rongYaService.reviewPass(ids);
	}

	@ResponseBody
	@RequestMapping(value = "/nopass", method = RequestMethod.POST)
	public String nopass(String ids, String failureReason){
		return rongYaService.reviewNoPass(ids, failureReason);
	}

}
