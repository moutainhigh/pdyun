package com.rmkj.microcap.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.PageEntity;
import com.rmkj.microcap.common.bean.PagerBean;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.modules.chanong.user.bean.RegisterBean;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.money.service.CashCouponService;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.user.bean.*;
import com.rmkj.microcap.modules.user.entity.UserMessage;
import com.rmkj.microcap.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by renwp on 2016/10/18.
 */
@RestController
@RequestMapping("${v1}/users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private CashCouponService cashCouponService;

    @Autowired
    private MoneyService moneyService;

    /**
     * 注册
     * @param registerBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity reg(@Valid RegisterBean registerBean, Errors errors, Model model){
        if(errors.hasErrors()){
           return parseErrors(errors);
        }
        return userService.reg(registerBean);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity get(){
        return userService.get();
    }

    @RequestMapping(value = "/tradeList", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity tradeList(PageEntity pageEntity){
        PagerBean<Trade> pb = userService.tradeList(pageEntity);
        return new ResponseEntity(pb, HttpStatus.OK);
    }

    @RequestMapping(value = "/moneyRecordList", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity moneyRecordList(PageEntity pageEntity){
        PagerBean<MoneyRecord> pb = userService.moneyRecordList(pageEntity);
        return new ResponseEntity(pb, HttpStatus.OK);
    }

    @RequestMapping(value = "/moneyOutInfo", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity moneyOutInfo(MoneyRecord moneyRecord){
        return new ResponseEntity(userService.moneyOutInfo(moneyRecord), HttpStatus.OK);
    }

    @RequestMapping(value = "/modifyChnName", method = RequestMethod.PUT)
    @LoginAnnot
    public ResponseEntity modifyChnName(@RequestBody(required = false) @Valid ModifyChnNameBean modifyChnNameBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.modifyChnName(modifyChnNameBean);
    }

    @RequestMapping(value = "/modifyMobile", method = RequestMethod.PUT)
    @LoginAnnot
    public ResponseEntity modifyMobile(@RequestBody(required = false) @Valid ModifyMobile modifyMobile, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.modifyMobile(modifyMobile);
    }

    @RequestMapping(value = "/modifyTradePwd", method = RequestMethod.PUT)
    @LoginAnnot
    public ResponseEntity modifyTradePwd(@RequestBody(required = false) @Valid ModifyTradePwd modifyTradePwd, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.modifyTradePwd(modifyTradePwd);
    }

    @RequestMapping(value = "/firstPartResetTradePassWord",method = RequestMethod.PUT)
    public ResponseEntity resetTradePassWord(@RequestBody @Valid FirstPartResetTradePwd firstPartResetTradePwd, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.validateMobileCode(firstPartResetTradePwd);
    }
    @RequestMapping(value = "/secondPartResetTradePassWord",method = RequestMethod.PUT)
    public ResponseEntity secondPartResetTradePassWord(@RequestBody @Valid SecondPartResetTradePwd secondPartResetTradePwd, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.restTradePass(secondPartResetTradePwd);
    }

    @RequestMapping(value = "/messageList", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity messageList(UserMessage userMessage){
        PagerBean<UserMessage> pb = userService.messageList(userMessage);
        return ResponseEntity.ok(pb);
    }

    @RequestMapping(value = "/newMessage", method = RequestMethod.GET)
    @LoginAnnot
    public ResponseEntity newMessage(){
        long count = userService.countNewMessage();
        return ResponseEntity.ok(new JSONObject().put("total", count).toString());
    }

    @RequestMapping(value = "/readMessage/{id}", method = RequestMethod.PUT)
    @LoginAnnot
    public ResponseEntity readMessage(@PathVariable String id){
        return userService.readMessage(id);
    }

    @RequestMapping(value = "/addBankCard", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity addBankCard(@RequestBody @Valid AddBankCard addBankCard, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return userService.addBankCard(addBankCard);
    }

    @RequestMapping(value = "/getCashCoupon",method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity getCashCoupon(){
        cashCouponService.giveCashCoupon(AuthContext.getUserId(), Constants.CashCoupon.GIVE_MONEY,Constants.CashCoupon.MIN_MONEY, Constants.CashCoupon.interval,Constants.CashCoupon.counts);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getOpenBankName",method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity getOpenBankName(String mainWord){
        return new ResponseEntity(userService.findOpenBankName(mainWord),HttpStatus.OK);
    }

    /*
    * 佣金转换成余额
    *
    * */
    @RequestMapping(value = "/commissionToMoney", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity commissionToMoney(   ){

        return moneyService.commissionToMoney();
    }
}
