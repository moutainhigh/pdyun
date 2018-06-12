package com.rmkj.microcap.modules.chanong.money.controller;/**
 * Created by Administrator on 2018/5/2.
 */

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.ResultError;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.pay.huanxun.entity.HuanXunMoneyVO;
import com.rmkj.microcap.modules.money.bean.MoneyOutBean;
import com.rmkj.microcap.modules.money.service.MoneyService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author k
 * @create -05-02-17:46
 **/
@Controller
@RequestMapping(value = "/api/moneyRecord")
public class MoneyController extends BaseController {

    @Autowired
    private MoneyService moneyService;

    @ResponseBody
    @RequestMapping(value = "/huanxun/gateway", method = RequestMethod.POST)
    @LoginAnnot
    public ExecuteResult huanXunGateWayPay(@Valid HuanXunMoneyVO entity, Errors errors){
        Map<String, Object> ret = new HashedMap();
        if(errors.hasErrors()){
            ResponseEntity errorEntity = parseErrors(errors);
            if (!errorEntity.getStatusCode().equals(HttpStatus.OK)) {
                ResponseErrorEntity er = (ResponseErrorEntity) errorEntity;
                ResultError resultError = (ResultError)errorEntity.getBody();
                ret.put("error",resultError.getError());
                return new ExecuteResult(resultError.getCode(),ret);
            }
        }
        ResponseEntity responseEntity = moneyService.huanXunGetaWayPay(entity);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResultError resultError = (ResultError)responseEntity.getBody();
            ret.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),ret);
        }
        ret.put("pGateWayReq", responseEntity.getBody());
        ret.put("payUrl", ProjectConstants.HUANXUN_PAY_URL);
        return new ExecuteResult(StatusCode.OK, ret);

    }

    @ResponseBody
    @RequestMapping(value = "/out", method = RequestMethod.POST)
    @LoginAnnot
    public ExecuteResult out(@Valid MoneyOutBean moneyOutBean, Errors errors){
        Map<String, Object> ret = new HashedMap();
        ResponseEntity responseEntity = null;
        if(errors.hasErrors()){
             responseEntity = parseErrors(errors);
        }

        if("0".equals(moneyOutBean.getType())){
            responseEntity = moneyService.out(moneyOutBean);
        }else{
            responseEntity = moneyService.returnMoneyOut(moneyOutBean);
        }

        if(!responseEntity.getStatusCode().equals(HttpStatus.OK)){
            ResultError resultError = (ResultError) responseEntity.getBody();
            ret.put("error", resultError.getError());
            return new ExecuteResult(resultError.getCode(), ret);
        }
        return new ExecuteResult(StatusCode.OK, ret);
    }

}
