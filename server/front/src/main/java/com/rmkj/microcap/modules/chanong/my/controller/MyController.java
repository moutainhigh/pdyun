package com.rmkj.microcap.modules.chanong.my.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.ResultError;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.modules.chanong.my.bean.BankCardBean;
import com.rmkj.microcap.modules.chanong.my.bean.MoneyBean;
import com.rmkj.microcap.modules.chanong.my.bean.MyAddressBean;
import com.rmkj.microcap.modules.chanong.my.bean.MyTradeBean;
import com.rmkj.microcap.modules.chanong.my.service.MyService;
import com.rmkj.microcap.modules.chanong.shop.service.ShopService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by jinghao on 2018/4/23.
 */
@Controller
@RequestMapping("/api/my")
public class MyController {
    private static Logger logger = Logger.getLogger(MyController.class);
    @Autowired
    private MyService myService;

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/getMyInfo", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getMyInfo() {
        return new ExecuteResult(StatusCode.OK, myService.getMyInfo());
    }

    /**
     * 添加地址
     * @param myAddressBean
     * @return
     */
    @RequestMapping(value = "/addMyAddress", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult addMyAddress(MyAddressBean myAddressBean) {
        myService.addMyAddress(myAddressBean);
        return new ExecuteResult(StatusCode.OK, new HashedMap());
    }

    /**
     * 管理地址
     * @param
     * @return
     */
    @RequestMapping(value = "/getMyAddress", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getMyAddress() {
        return new ExecuteResult(StatusCode.OK, myService.getMyAddress());
    }

    /**
    * 设为默认地址
    * @param id
    * @return
    */
    @RequestMapping(value = "/setDefaultAddress", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult setDefaultAddress(String id) {
        myService.setDefaultAddress(id);
        return new ExecuteResult(StatusCode.OK, new HashedMap());
    }

    /**
     * 修改地址
     * @param myAddressBean
     * @return
     */
    @RequestMapping(value = "/updAddress", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult updAddress(MyAddressBean myAddressBean) {
        myService.updAddress(myAddressBean);
        return new ExecuteResult(StatusCode.OK, new HashedMap());
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteMyAddress", method = RequestMethod.DELETE)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult deleteMyAddress(String id) {
        myService.deleteMyAddress(id);
        return new ExecuteResult(StatusCode.OK, new HashedMap());
    }

    /**
     * 订单管理
     * @param myTradeBean
     * @return
     */
    @RequestMapping(value = "/getMyTrade", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getMyTrade(MyTradeBean myTradeBean) {
        return new ExecuteResult(StatusCode.OK, myService.getMyTrade(myTradeBean));
    }

    /**
     * 资金管理
     * @param moneyBean
     * @return
     */
    @RequestMapping(value = "/moneyManager", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult moneyManager(MoneyBean moneyBean) {
        return new ExecuteResult(StatusCode.OK, myService.moneyManager(moneyBean));
    }

    /**
     * 绑卡
     * @param bankCardBean
     * @return
     */
    @RequestMapping(value = "/addBankCard", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult addBankCard(BankCardBean bankCardBean){
        Map<String,Object> ret = new HashedMap();
        ResponseEntity responseEntity = myService.addBankCard(bankCardBean);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResultError resultError = (ResultError)responseEntity.getBody();
            ret.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),ret);
        }

        return new ExecuteResult(StatusCode.OK, ret);
    }

    /**
     * 修改绑定银行卡
     * @param bankCardBean
     * @return
     */
    @RequestMapping(value = "/updateBankCard", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult updateBankCard(BankCardBean bankCardBean){
        Map<String,Object> ret = new HashedMap();
        ResponseEntity responseEntity = myService.updateBankCard(bankCardBean);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResultError resultError = (ResultError)responseEntity.getBody();
            ret.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),ret);
        }
        return new ExecuteResult(StatusCode.OK, ret);
    }

    /**
     * 获取绑定银行卡
     * @param
     * @return
     */
    @RequestMapping(value = "/getBankCard", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getBankCard(){
        return new ExecuteResult(StatusCode.OK, myService.getBankCard());
    }


    /**
     * 查询军团信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/corps", method = RequestMethod.POST)
    @LoginAnnot
    public ExecuteResult queryCorpsList(){
        return new ExecuteResult(StatusCode.OK, myService.queryCorpsList());
    }

}
