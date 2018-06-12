package com.rmkj.microcap.modules.chanong.shop.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.modules.chanong.index.service.IndexService;
import com.rmkj.microcap.modules.chanong.shop.service.ShopService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jinghao on 2018/4/23.
 */
@Controller
@RequestMapping("/api/shop")
public class ShopController {
    private static Logger logger = Logger.getLogger(ShopController.class);
    @Autowired
    private ShopService shopService;

    /**
     * 获取产品
     * @return
     */
    @RequestMapping(value = "/getContract", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getContract() {
        return new ExecuteResult(StatusCode.OK, shopService.getContract());
    }

}
