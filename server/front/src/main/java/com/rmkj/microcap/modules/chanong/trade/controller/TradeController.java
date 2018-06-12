package com.rmkj.microcap.modules.chanong.trade.controller;/**
 * Created by Administrator on 2018/4/28.
 */

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.ResultError;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.lock.KeyLocks;
import com.rmkj.microcap.modules.chanong.sub.bean.SubMakeBean;
import com.rmkj.microcap.modules.chanong.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author k
 * @create -04-28-17:58
 **/
@Controller
@RequestMapping(value = "/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    private KeyLocks keyLocks = new KeyLocks();

    /**
     * 挂单
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/entryOrders", method = RequestMethod.POST)
    @LoginAnnot
    public ExecuteResult entryOrders(@Valid SubMakeBean entity){
        Map<String, Object> ret = new HashMap();
        String userId = AuthContext.getUserId();
        synchronized (keyLocks.lockByKey(userId)){
            ResponseEntity responseEntity = tradeService.entryOrders(entity);
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                ResponseErrorEntity er = (ResponseErrorEntity) responseEntity;
                ResultError resultError = (ResultError)responseEntity.getBody();
                ret.put("error",resultError.getError());
                return new ExecuteResult(resultError.getCode(),ret);
            }
        }
        return new ExecuteResult(StatusCode.OK, ret);
    }

}
