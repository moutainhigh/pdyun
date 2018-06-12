package com.rmkj.microcap.modules.chanong.sub.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.ResultError;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.lock.KeyLocks;
import com.rmkj.microcap.modules.chanong.index.service.IndexService;
import com.rmkj.microcap.modules.chanong.sub.bean.BuyBean;
import com.rmkj.microcap.modules.chanong.sub.bean.BuyHangBean;
import com.rmkj.microcap.modules.chanong.sub.bean.SubMakeBean;
import com.rmkj.microcap.modules.chanong.sub.bean.TakeGoodsBean;
import com.rmkj.microcap.modules.chanong.sub.service.SubService;
import com.rmkj.microcap.modules.sms.service.SmsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by jinghao on 2018/4/23.
 */
@Controller
@RequestMapping("/api/sub")
public class SubController {
    private static Logger logger = Logger.getLogger(SubController.class);
    @Autowired
    private SubService subService;
    @Autowired
    private SmsService smsService;
    private KeyLocks keyLocks = new KeyLocks();

    /**
     * 获取认购/购销/下架商品
     * @return
     */
    @RequestMapping(value = "/getSubGoods", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getSubGoods(String status, String goodsTypeId, String isStore) {
        return new ExecuteResult(StatusCode.OK, subService.getSubGoods(status, goodsTypeId, isStore));
    }

    /**
     * 通过id获取商品
     * @return
     */
    @RequestMapping(value = "/getSubGoodsById", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getSubGoodsById(String id) {
        return new ExecuteResult(StatusCode.OK, subService.getSubGoodsById(id));
    }

    /**
     * 认购
     * @param subMakeBean
     * @return
     */
    @RequestMapping(value = "/subMake", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult subMake(SubMakeBean subMakeBean) {
        Map<String,Object> ret = new HashedMap();
//        Object lock;
//        synchronized (this){
//            SoftReference<Object> objectSoftReference = lockMap.get(subMakeBean.getUserId());
//            if(objectSoftReference == null || (lock = objectSoftReference.get()) == null){
//                lock = new Object();
//                lockMap.put(subMakeBean.getUserId(), new SoftReference<>(lock));
//            }
//        }
        synchronized (keyLocks.lockByKey(subMakeBean.getUserId())){
            ResponseEntity responseEntity = subService.subMake(subMakeBean);
            return getReturnResult(responseEntity);
        }
    }

    /**
     * 获取服务费比例及手续费比例
     * @return
     */
    @RequestMapping(value = "/getScales", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getScales(){
        return new ExecuteResult(StatusCode.OK, subService.getScales());
    }

    /**
     * 获取挂单商品
     * @return
     */
    @RequestMapping(value = "/getHangGoods", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getHangGoods(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.getHangGoods(goodsId));
    }

    /**
     * 买入
     * @param buyHangBean
     * @return
     */
/*    @RequestMapping(value = "/buyHangGoods", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult buyHangGoods(BuyHangBean buyHangBean){
        synchronized (keyLocks.lockByKey(buyHangBean.getUserId())){
            ResponseEntity responseEntity = subService.buyHangGoods(buyHangBean);
            return getReturnResult(responseEntity);
        }
    }*/

    /**
     * 获取用户积分
     * @return
     */
    @RequestMapping(value = "/getUserIntegral", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getUserIntegral(){
        return new ExecuteResult(StatusCode.OK, subService.getUserIntegral());
    }

    /**
     * 获取用户积分明细
     * @return
     */
    @RequestMapping(value = "/getUserIntegralDetail", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getUserIntegralDetail(){
        return new ExecuteResult(StatusCode.OK, subService.getUserIntegralDetail());
    }

    /**
     * 获取当前时间
     * @return
     */
    @RequestMapping(value = "/getSysTime", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getSysTime(){
        Map<String,Object> ret = new HashedMap();
        ret.put("sysTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return new ExecuteResult(StatusCode.OK, ret);
    }


    /**
     * 获取可认购商品剩余比例
     * @return
     */
    @RequestMapping(value = "/getLeftScale", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getLeftScale(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.getLeftScale(goodsId));
    }

    /**
     * 短信
     * @param type
     * @param mobile
     * @return
     */
    @RequestMapping(value="/validatecode/{type}_{mobile}", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult validateCode(@PathVariable String type, @PathVariable String mobile){
        ResponseEntity responseEntity = smsService.sendSmsValidateCode(mobile, type);
        return getReturnResult(responseEntity);
    }


    /**
     * 提货
     * @param takeGoodsBean
     * @return
     */
    @RequestMapping(value = "/takeGoodsAway", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult takeGoodsAway(TakeGoodsBean takeGoodsBean){
        ResponseEntity responseEntity = subService.takeGoodsAway(takeGoodsBean);
        return getReturnResult(responseEntity);
    }

    /**
     * 撤销挂单
     * @param serialNo
     * @return
     */
    @RequestMapping(value = "/cancelHangTrade", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult cancelHangTrade(String serialNo){
        synchronized (keyLocks.lockByKey(AuthContext.getUserId().concat(serialNo).concat("GD"))){
            ResponseEntity responseEntity = subService.cancelHangTrade(serialNo);
            return getReturnResult(responseEntity);
        }
    }

    /**
     * 返回数据
     * @param responseEntity
     * @return
     */
    private ExecuteResult getReturnResult(ResponseEntity responseEntity){
        Map<String,Object> ret = new HashedMap();
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ResponseErrorEntity er = (ResponseErrorEntity) responseEntity;
            ResultError resultError = (ResultError)responseEntity.getBody();
            ret.put("error",resultError.getError());
            return new ExecuteResult(resultError.getCode(),ret);
        }
        return new ExecuteResult(StatusCode.OK, ret);
    }

    /**
     * 获取挂单商品信息
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/getHangDetail", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getHangDetail(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.getHangDetail(goodsId));
    }

    /**
     * 获取全部商品类型
     * @return
     */
    @RequestMapping(value = "/findGoodsType", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult findGoodsType(){
        return new ExecuteResult(StatusCode.OK, subService.findGoodsType());
    }

    /**
     * 买入-新
     * @param buyBean
     * @return
     */
    @RequestMapping(value = "/buyHangGoodsNew", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult buyHangGoodsNew(BuyBean buyBean){
        /*synchronized (keyLocks.lockByKey(AuthContext.getUserId().concat("_GM"))){*/
        synchronized (keyLocks.lockByKey(AuthContext.getUserId())){
            ResponseEntity responseEntity = subService.buyHangGoodsNew(buyBean);
            return getReturnResult(responseEntity);
        }
    }

    /**
     * 收藏
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goodsStore", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult goodsStore(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.goodsStore(goodsId));
    }

    /**
     * 取消收藏
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goodsStoreCancel", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult goodsStoreCancel(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.goodsStoreCancel(goodsId));
    }

    /**
     * 我的收藏
     * @return
     */
    @RequestMapping(value = "/getMyGoodsStore", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getMyGoodsStore(){
        return new ExecuteResult(StatusCode.OK, subService.getMyGoodsStore());
    }

    /**
     * 判断商品是否已收藏
     * @return
     */
    @RequestMapping(value = "/judgeGoodsStore", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult judgeGoodsStore(String goodsId){
        return new ExecuteResult(StatusCode.OK, subService.judgeGoodsStore(goodsId));
    }

    /**
     * 获取商品详情图片
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/getGoodsDetailImg", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult getGoodsDetailImg(String goodsId){
        return new ExecuteResult(StatusCode.OK,subService.getGoodsDetailImg(goodsId));
    }

    /**
     * 获取挂单价格浮动比例
     * @param
     * @return
     */
    @RequestMapping(value = "/getUpAndDownPercent", method = RequestMethod.GET)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult getUpAndDownPercent(){
        return new ExecuteResult(StatusCode.OK,subService.getUpAndDownPercent());
    }

    /**
     * 获取开市时间
     * @return
     */
    @RequestMapping(value = "/openWeekAndTime", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult openWeekAndTime(){
        return new ExecuteResult(StatusCode.OK,subService.openWeekAndTime());
    }

    /**
     * 判断是否在开市时间
     * @return
     */
    @RequestMapping(value = "/judgeOpenTime", method = RequestMethod.GET)
    @ResponseBody
    public ExecuteResult judgeOpenTime(){
        return new ExecuteResult(StatusCode.OK,subService.judgeOpenTime());
    }

    /**
     * 奖励金额转余额
     * @param
     * @return
     */
    @RequestMapping(value = "/converUserMoney", method = RequestMethod.POST)
    @ResponseBody
    @LoginAnnot
    public ExecuteResult converUserMoney(){
        return new ExecuteResult(StatusCode.OK, subService.converUserMoney());
    }
}
