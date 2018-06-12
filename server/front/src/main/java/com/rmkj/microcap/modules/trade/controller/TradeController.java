package com.rmkj.microcap.modules.trade.controller;

import com.mysql.fabric.Response;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.modules.trade.bean.MakeTradeBean;
import com.rmkj.microcap.modules.trade.bean.SellBean;
import com.rmkj.microcap.modules.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by renwp on 2016/10/18.
 */
@RestController
@RequestMapping("${v1}/trade")
public class TradeController extends BaseController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping(value = "/contractList", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity contractList(){
        return new ResponseEntity(tradeService.contractList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity make(@RequestBody @Valid MakeTradeBean makeTradeBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return tradeService.make(makeTradeBean);
    }

//    @RequestMapping(value = "/sell", method = RequestMethod.PUT)
//    @LoginAnnot
//    public ResponseEntity sell(@RequestBody @Valid SellBean sellBean, Errors errors){
//        if(errors.hasErrors()){
//            return parseErrors(errors);
//        }
//        return tradeService.sell(sellBean);
//    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity list(){
        return new ResponseEntity(tradeService.tradeList(), HttpStatus.OK);
    }
}
