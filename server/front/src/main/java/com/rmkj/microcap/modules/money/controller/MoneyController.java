package com.rmkj.microcap.modules.money.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.pay.mingfu.bean.MingFuQuickBean;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.PayRequestParamBean;
import com.rmkj.microcap.common.modules.pay.yizhifu.bean.UnionPayRequestParam;
import com.rmkj.microcap.common.modules.pay.zhinengcloud.entity.ZhiNengCloudPayBean;
import com.rmkj.microcap.common.modules.pay.zhongnan.entity.ZhongNanQuickPayBean;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.money.bean.MoneyOutBean;
import com.rmkj.microcap.modules.money.bean.PrePayBean;
import com.rmkj.microcap.modules.money.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by renwp on 2016/10/19.
 */
@RestController
@RequestMapping("${v1}/money")
public class MoneyController extends BaseController {

    @Autowired
    private MoneyService moneyService;

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity out(@RequestBody @Valid MoneyOutBean moneyOutBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return "0".equals(moneyOutBean.getType()) ? moneyService.out(moneyOutBean) : moneyService.returnMoneyOut(moneyOutBean);
    }

    @RequestMapping(value = "/prepay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity prePay(@RequestBody @Valid PrePayBean prePayBean, Errors errors, HttpServletRequest request){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        prePayBean.setSpbillCreateIp(Utils.getClientIp(request));
        return moneyService.prePayOfWeiFuTong(prePayBean);
    }

    @RequestMapping(value = "/prepay/del/{thirdSerialNo}", method = RequestMethod.DELETE)
    @LoginAnnot
    public ResponseEntity deletePrePay(@PathVariable String thirdSerialNo){
        return moneyService.deletePrePay(thirdSerialNo);
    }

    //首信易支付下单
    @RequestMapping(value = "/createPayOrder", method = RequestMethod.POST)
    public ResponseEntity createPayOrder(@RequestBody PayRequestParamBean requestParamBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.shouXinYinCreateOrder(requestParamBean);
    }

    /**
     * 首信易支付 银联支付下单
     */
    @RequestMapping(value = "/yizhifu/unionPay/createPayOrder",method = RequestMethod.POST)
    public ResponseEntity createUnionPayOrder(@RequestBody UnionPayRequestParam unionPayRequestParam){
        return moneyService.createUnionPayOrder(unionPayRequestParam);
    }

    /**
     * 中南快捷支付
     * @param errors
     * @param request
     * @return
     */
    @RequestMapping(value = "/zhongNanQuickPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity zhongNanQuickPay(@RequestBody @Valid ZhongNanQuickPayBean zhongNanQuickPayBean, Errors errors, HttpServletRequest request){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.zhongNanQuickPayBean(zhongNanQuickPayBean);
    }

    /**
     * 智能云微信支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/zhinengCloudWechat", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity zhiNengCloudPayWechat(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.zhiNengCloudWechatPay(zhiNengCloudPayBean);
    }

    /**
     * 智能云支付宝支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/zhinengCloudAli", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity zhiNengCloudPayAli(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.zhiNengCloudAli(zhiNengCloudPayBean);
    }

    /**
     * 融雅网关支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/rongyaUnionPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity rongyaUnionPay(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.rongyaUnionPay(zhiNengCloudPayBean);
    }

    /**
     * 融雅微信wap支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/rongyaWechatPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity rongyaWechatWapPay(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.rongyaWechatWapPay(zhiNengCloudPayBean);
    }

    /**
     * 融雅支付宝wap支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/rongyaAliPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity rongyaAliWapPay(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.rongyaAliWapPay(zhiNengCloudPayBean);
    }

    /**
     * 融雅支付宝wap支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/rongheFastPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity rongheFastPay(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.rongheFastPay(zhiNengCloudPayBean);
    }

    /**
     * 明付网关支付
     * @param zhiNengCloudPayBean
     * @param errors
     * @return
     */
    @RequestMapping(value = "/mingfuUnionPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity mingfuUnionPay(@RequestBody @Valid ZhiNengCloudPayBean zhiNengCloudPayBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.mingfuUnionPay(zhiNengCloudPayBean);
    }


    @RequestMapping(value = "/mingfuQuickPay", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity mingfuQuickPay(@RequestBody @Valid MingFuQuickBean mingFuQuickBean, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.mingfuQuickPay(mingFuQuickBean);
    }

    @RequestMapping(value = "/mingfuMsgSub", method = RequestMethod.POST)
    @LoginAnnot
    public ResponseEntity mingfuMsgSub(@RequestBody @Valid @NotNull(message = "验证码不能为空") String msg, Errors errors){
        if(errors.hasErrors()){
            return parseErrors(errors);
        }
        return moneyService.validMingFuMsg(msg);
    }

}
