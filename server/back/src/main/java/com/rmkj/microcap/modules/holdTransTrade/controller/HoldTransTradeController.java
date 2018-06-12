package com.rmkj.microcap.modules.holdTransTrade.controller;/**
 * Created by Administrator on 2018/5/18.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.modules.holdTransTrade.entity.HoldTransTradeBean;
import com.rmkj.microcap.modules.holdTransTrade.service.HoldTransTradeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author k
 * @create -05-18-17:21
 **/
@Controller
@RequestMapping(value = "/transTrade")
public class HoldTransTradeController extends BaseController {

    @Autowired
    private HoldTransTradeService holdTransTradeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "/holdTransTrade/trans_list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager list(HoldTransTradeBean entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));

        return holdTransTradeService.queryList(entity);
    }

}
