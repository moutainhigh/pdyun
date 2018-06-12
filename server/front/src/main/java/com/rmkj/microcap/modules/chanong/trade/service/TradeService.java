package com.rmkj.microcap.modules.chanong.trade.service;/**
 * Created by Administrator on 2018/4/27.
 */

import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.chanong.sub.bean.GoodsBean;
import com.rmkj.microcap.modules.chanong.sub.bean.OpenTime;
import com.rmkj.microcap.modules.chanong.sub.bean.ScalesBean;
import com.rmkj.microcap.modules.chanong.sub.bean.SubMakeBean;
import com.rmkj.microcap.modules.chanong.sub.dao.SubDao;
import com.rmkj.microcap.modules.chanong.sub.service.SubService;
import com.rmkj.microcap.modules.chanong.trade.dao.TradeDao;
import com.rmkj.microcap.modules.chanong.trade.entity.TradeBean;
import com.rmkj.microcap.modules.index.service.ScheduleService;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author k
 * @create -04-27-17:37
 **/
@Service
@Transactional
public class TradeService {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private TradeDao tradeDao;
    @Autowired
    private SubDao subDao;
    @Autowired
    private MoneyService moneyService;
    @Autowired
    private SubService subService;
    @Autowired
    private ScheduleService scheduleService;

    public ResponseEntity entryOrders(SubMakeBean subMakeBean){
        String moneyValid = subMakeBean.getBuyPoint().toString();
        //验证挂单金额是否小数点后两位
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher matcher = pattern.matcher(moneyValid);
        if(!matcher.matches()){
            return new ResponseErrorEntity(StatusCode.MONEY_TWO_FLOAT, HttpStatus.BAD_REQUEST);
        }

        User user = userDao.findUserById(AuthContext.getUserId());
        //判断用户是否被停用
        if(user.getStatus()!=0) {
            return new ResponseErrorEntity(StatusCode.USER_CLOSE, HttpStatus.BAD_REQUEST);
        }
        //验证商品可挂单数量
        SubMakeBean tradeHold = tradeDao.getTradeHoldNum(user.getId(), subMakeBean.getId());
        if(null == tradeHold){
            return new ResponseErrorEntity(StatusCode.SUBGOODS_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
        if(subMakeBean.getHoldNum() > tradeHold.getHoldNum()){
            return new ResponseErrorEntity(StatusCode.TRADE_HOLDNUM_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }

        GoodsBean goodsBean = subDao.getSubGoodsById(tradeHold.getGoodsId());
        //判断商品是否能挂单
        if(!Constants.SubGoodsStatus.TRADE.equals(goodsBean.getStatus())){
            return new ResponseErrorEntity(StatusCode.NOT_HANG_TIME_ERROR, HttpStatus.BAD_REQUEST);
        }

        BigDecimal buyPoint = new BigDecimal(0);
        BigDecimal percentMoney = new BigDecimal(0);
        //验证是否在开盘时间内
//        String openTimeStr = (String) scheduleService.getParameter(Constants.PARAMETER_SET_KEY.OPEN_TIME);
//        String weekDaySet = (String) scheduleService.getParameter(Constants.PARAMETER_SET_KEY.WEEK_DAY_SET);
        OpenTime openTimes = subDao.openWeekAndTime();
        String openTimeStr = openTimes.getOpenTime();
        String weekDaySet = openTimes.getWeekDaySet();
        //验证是否在规定开盘日期
        String[] weekDay = weekDaySet.split(",");
        String[] openTime = openTimeStr.split(",");
        //休市则挂单零售价
        if(!subService.isMarketOpen(openTime, weekDay)){
            buyPoint  = goodsBean.getGoodsSubPrice().multiply(new BigDecimal(10));
            return new ResponseErrorEntity(StatusCode.NOT_HANG_TIME_ERROR, HttpStatus.BAD_REQUEST);
        }else {
            //验证客户挂单价格是否是最新价格的上下百分之十区间,若没有最新成交价取基准价
            BigDecimal money = subMakeBean.getBuyPoint();
            BigDecimal sellPoint = new BigDecimal(0); //最新价格
            TradeBean newSellPoint = tradeDao.getTodaySellPoint(tradeHold.getGoodsId());
            BigDecimal upAndDownPercent = tradeDao.getUpAndDownPercent();
            /*if(null == newSellPoint){*/
                sellPoint = goodsBean.getGoodsCost();
                percentMoney = goodsBean.getGoodsCost().multiply((upAndDownPercent.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            /*}else {
                sellPoint = newSellPoint.getSellPoint();
                percentMoney = newSellPoint.getSellPoint().multiply((upAndDownPercent.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }*/
            if(money.compareTo(sellPoint.add(percentMoney)) == 1 || money.compareTo(sellPoint.subtract(percentMoney)) == -1){
                return new ResponseErrorEntity(StatusCode.ENTRY_ORDER_NONE, HttpStatus.BAD_REQUEST);
            }else {
                //取消原订单买入价
                //buyPoint = money;
                buyPoint = subMakeBean.getBuyPoint();
            }
        }

        SubMakeBean entity = new SubMakeBean();
        entity.setSerialNo(subService.serialNo("GD"));
        entity.setUserId(user.getId());
        entity.setHoldNum(subMakeBean.getHoldNum());
        entity.setGoodsName(tradeHold.getGoodsName());
        entity.setGoodsId(tradeHold.getGoodsId());
        entity.setBuyTime(new Date());
        entity.setBuyPoint(tradeHold.getBuyPoint());
        entity.setSellPoint(buyPoint);
        entity.setStatus(Constants.SubMake.HANG);
        entity.setCenterId(tradeHold.getCenterId());
        entity.setUnitsId(tradeHold.getUnitsId());
        entity.setAgentId(tradeHold.getAgentId());
        entity.setParent1Id(tradeHold.getParent1Id());
        entity.setParent2Id(tradeHold.getParent2Id());
        entity.setParent3Id(tradeHold.getParent3Id());
        entity.setId(Utils.uuid());
        entity.setHangTime(new Date());
        entity.setOldTradeSerialNo(tradeHold.getSerialNo());
        entity.setHoldFlag("2");
        /**
         * 挂单时  - 原订单认购服务费，如果为0则不减， money也是一样
         */
        //获取服务费比例、手续费比例、骑兵步兵返佣比例
        ScalesBean scalesBean = subDao.getScales();

        //计算单笔服务费   求单笔服务费
        BigDecimal serviceFee = new BigDecimal(0);
       /* if("1".equals(tradeHold.getHoldFlag())){
            serviceFee = new BigDecimal(0);
            entity.setMoney(new BigDecimal(0));
        }else{
           *//* BigDecimal countMoney = tradeHold.getBuyPoint().multiply(new BigDecimal(tradeHold.getHoldNum()));//当前持仓金额
            serviceFee = countMoney.multiply(scalesBean.getSubServiceScale()).setScale(2, BigDecimal.ROUND_HALF_DOWN);//减去提货单的持仓手续费*//*
            entity.setMoney(buyPoint.multiply(new BigDecimal(entity.getHoldNum())).setScale(2, BigDecimal.ROUND_DOWN));
            tradeHold.setMoney(tradeHold.getMoney().subtract(entity.getMoney()));

        }*/
        if(new BigDecimal(0).compareTo(tradeHold.getMoney()) == 0){
            serviceFee = new BigDecimal(0);
            entity.setMoney(new BigDecimal(0));
        }else if(new BigDecimal(0).compareTo(tradeHold.getServiceFee()) == -1){
            serviceFee = tradeHold.getServiceFee();
            entity.setMoney(buyPoint.multiply(new BigDecimal(entity.getHoldNum())).setScale(2, BigDecimal.ROUND_DOWN));
            /*tradeHold.setMoney(tradeHold.getMoney().subtract(entity.getMoney()));*/
            //计算商品单价
            BigDecimal goodsPrice = tradeHold.getBuyPoint().multiply(new BigDecimal(tradeHold.getHoldNum()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            goodsPrice = goodsPrice.divide(new BigDecimal(tradeHold.getHoldNum()));
            tradeHold.setMoney(tradeHold.getMoney().subtract(goodsPrice.multiply(new BigDecimal(subMakeBean.getHoldNum())).setScale(2, BigDecimal.ROUND_HALF_DOWN)));
            //计算反比服务费
            serviceFee = goodsPrice.multiply(scalesBean.getSubServiceScale());

        }else{
            serviceFee = new BigDecimal(0);
            entity.setMoney(buyPoint.multiply(new BigDecimal(entity.getHoldNum())).setScale(2, BigDecimal.ROUND_DOWN));
        }
        entity.setServiceFee(serviceFee.multiply(new BigDecimal(subMakeBean.getHoldNum())).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        //entity.setServiceFee(serviceFee.divide(new BigDecimal(tradeHold.getHoldNum()), 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(subMakeBean.getHoldNum())).setScale(2, BigDecimal.ROUND_DOWN));

        //计算商品单价
        BigDecimal scale = new BigDecimal(subMakeBean.getHoldNum()).divide(new BigDecimal(tradeHold.getHoldNum()),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);

        entity.setFeeBuy(tradeHold.getFeeBuy().multiply(scale).setScale(2,BigDecimal.ROUND_HALF_DOWN));
       // entity.setMoney(buyPoint.multiply(new BigDecimal(entity.getHoldNum())).setScale(2, BigDecimal.ROUND_DOWN));

        //减少当前认购商品订单数量
        tradeHold.setHoldNum(tradeHold.getHoldNum() - subMakeBean.getHoldNum());
        tradeHold.setServiceFee(tradeHold.getServiceFee().subtract(entity.getServiceFee()));
        tradeHold.setFeeBuy(tradeHold.getFeeBuy().subtract(entity.getFeeBuy()));
        if(tradeHold.getHoldNum() == 0){
            tradeHold.setStatus(Constants.SubMake.DELETE);
        }
        if(tradeDao.saveEntryOrder(entity) != 1 || tradeDao.updateEntryOrderHoldNum(tradeHold) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.ENTRY_ORDERS_FAIL, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(StatusCode.OK);
    }

}
