package com.rmkj.microcap.modules.tradeMarket.controller;/**
 * Created by Administrator on 2017/11/27.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.modules.trade.bean.SellBean;
import com.rmkj.microcap.modules.trade.bean.TradeMakeBean;
import com.rmkj.microcap.modules.tradeMarket.entity.UpdateHoldBean;
import com.rmkj.microcap.modules.tradeMarket.service.TradeMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author k
 * @create -11-27-10:12
 **/
@Controller
@RequestMapping(value = "${v1}/tradeMarket")
public class TradeMarketController extends BaseController{

    @Autowired
    private TradeMarketService tradeMarketService;

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity make(@RequestBody @Valid TradeMakeBean makeTradeBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return tradeMarketService.make(makeTradeBean);
    }

    @RequestMapping(value = "/sell", method = RequestMethod.PUT)
    @LoginAnnot
    public ResponseEntity sell(@RequestBody @Valid SellBean sellBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return tradeMarketService.sell(sellBean);
    }

    @RequestMapping(value = "/updateTrade", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity updateWinPercentOrLossPercent(@RequestBody @Valid UpdateHoldBean updateHoldBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return tradeMarketService.updateWinPercentOrLossPercent(updateHoldBean);
    }
}
