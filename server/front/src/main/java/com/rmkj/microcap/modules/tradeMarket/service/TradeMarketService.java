package com.rmkj.microcap.modules.tradeMarket.service;/**
 * Created by Administrator on 2017/11/27.
 */

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.AuthEntity;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.trademarket.MarketPointBean;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.market.service.MarketService;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.trade.bean.*;
import com.rmkj.microcap.modules.trade.dao.TradeDao;
import com.rmkj.microcap.modules.trade.entity.Agent3Trade;
import com.rmkj.microcap.modules.trade.entity.Contract;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.trade.service.TradeService;
import com.rmkj.microcap.modules.tradeMarket.dao.TradeMarketDao;
import com.rmkj.microcap.modules.tradeMarket.entity.TradeMarketBean;
import com.rmkj.microcap.modules.tradeMarket.entity.UpdateHoldBean;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.dao.UserMessageDao;
import com.rmkj.microcap.modules.user.entity.Agent3User;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author k
 * @create -11-27-10:19
 **/
@Service
@Lazy(false)
public class TradeMarketService {

    private final Logger Log = Logger.getLogger(TradeMarketService.class);

    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private TradeMarketDao tradeMarketDao;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private MoneyService moneyService;

    public ResponseEntity makes(TradeMakeBean makeTradeBean){
        Contract contract = tradeDao.findContractByCode(makeTradeBean.getCode());
        if(!tradeService.isMarketOpen() || contract == null || !tradeService.isMarketOpen(new String[]{contract.getBeginTime(), contract.getEndTime()})){
            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
        }

        AuthEntity curAuth = AuthContext.getCurAuth();
        User userById = null;
        if(ProjectConstants.THREE_SALE_SYS){
            userById = userDao.findAgent3UserById(curAuth.getUserId());
        }else{
            userById = userDao.findUserById(curAuth.getUserId());
        }
        //验证账户余额是否充足
        double createHoldMoney = makeTradeBean.getMoney().multiply(new BigDecimal(makeTradeBean.getHoldCount())).doubleValue();
        double createHoldFee = Utils.toBigDecimal(contract.fee(makeTradeBean.getMoney().toString())).multiply(new BigDecimal(makeTradeBean.getHoldCount())).doubleValue();
        if((createHoldMoney + createHoldFee) > userById.getMoney().doubleValue()){
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }
        //StringBuffer message = new StringBuffer();
        for (int i = 0; i < makeTradeBean.getHoldCount(); i++){
            //ResponseEntity entity = make(makeTradeBean, userById, contract);
            //message.append(entity.getBody());
        }
        return new ResponseErrorEntity(HttpStatus.OK);
    }

    /**
     * 建仓下单
     * @param makeTradeBean
     * @return
     */
    @Transactional
    public ResponseEntity make(TradeMakeBean makeTradeBean) {
        Contract contract = tradeDao.findContractByCode(makeTradeBean.getCode());
        boolean isExemptClosed = false;
        if(contract != null && contract.getExemptClosed() == 1){
            isExemptClosed = true;
        }
        if(!tradeService.isMarketOpen() || contract == null || !tradeService.isMarketOpen(new String[]{contract.getBeginTime(), contract.getEndTime()}, isExemptClosed)){
            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
        }

        AuthEntity curAuth = AuthContext.getCurAuth();
        User userById = null;
        if(ProjectConstants.THREE_SALE_SYS){
            userById = userDao.findAgent3UserById(curAuth.getUserId());
        }else{
            userById = userDao.findUserById(curAuth.getUserId());
        }


        // 校验交易密码
        /*if(!makeTradeBean.getTradePassword().equals(userById.getTradePassword())){
            return new ResponseErrorEntity(StatusCode.TRADE_PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
        }*/

        if(Constants.Trade.TYPE_COUPON_MONEY.equals(makeTradeBean.getType())){
            if(userById.getCouponMoney().subtract(makeTradeBean.getMoney()).doubleValue() < 0){
                return new ResponseErrorEntity(StatusCode.COUPON_MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
            }
        }else {
            if(userById.getMoney().subtract(makeTradeBean.getMoney()).doubleValue() < 0){
                return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
            }
        }

        // 获取当前最新行情
        MarketPointBean latest = marketService.latest(makeTradeBean.getCode());

        String money = makeTradeBean.getMoney().toString();
        BigDecimal holdCount = new BigDecimal(makeTradeBean.getHoldCount()); //手数

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        Trade trade = ProjectConstants.THREE_SALE_SYS ? new Agent3Trade() : new Trade();
        trade.setModel(Constants.Trade.MODEL_DEFAULT);
        trade.setSerialNo(serialNo());
        trade.setUserId(userById.getId());
        trade.setType(makeTradeBean.getType());
        trade.setBuyTime(now);

        trade.setBuyPoint(Utils.toBigDecimal(latest.getPrice()));
        trade.setBuyUpDown(makeTradeBean.getBuyUpDown());
        trade.setCode(contract.getCode());
        trade.setContractName(new StringBuffer().append(contract.getName()).append("/").append(holdCount.toString()).append("手").toString());
        trade.setMoney(makeTradeBean.getMoney().multiply(holdCount));
        trade.setPointValue(Utils.toBigDecimal(contract.pointMoney(money)).multiply(holdCount));
        trade.setFee(Utils.toBigDecimal(contract.fee(money)).multiply(holdCount));
        trade.setProfitMax(makeTradeBean.getProfitMax() == null ? 0 : makeTradeBean.getProfitMax());
        trade.setLossMax(makeTradeBean.getLossMax() == null ? 0 : makeTradeBean.getLossMax());

        // 计算止盈止损最大点
        // 购买金额*止盈止损百分比/波动点值 （向上取整）
        //
        if(null != trade.getProfitMax() && null != trade.getLossMax()){//选择止盈止损
            BigDecimal profitMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(trade.getProfitMax()/100d)).divide(trade.getPointValue(), 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            BigDecimal lossMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(trade.getLossMax()/100d)).divide(trade.getPointValue(), 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
                trade.setProfitMaxPoint(profitMaxPoint.compareTo(new BigDecimal(0)) == 0 ? null : trade.getBuyPoint().add(profitMaxPoint));
                trade.setLossMaxPoint(lossMaxPoint.compareTo(new BigDecimal(0)) == 0 ? null : trade.getBuyPoint().subtract(lossMaxPoint));
            }else{
                trade.setProfitMaxPoint(profitMaxPoint.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0) : trade.getBuyPoint().subtract(profitMaxPoint));
                trade.setLossMaxPoint(lossMaxPoint.compareTo(new BigDecimal(0)) == 0 ? null : trade.getBuyPoint().add(lossMaxPoint));
            }
        }else if(true){

        }else{
            /*BigDecimal lossMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(trade.getLossMax()/100d)).divide(trade.getPointValue(), 2, BigDecimal.ROUND_HALF_UP).doubleValue()));
            if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
                trade.setProfitMaxPoint(null);
                trade.setLossMaxPoint(trade.getBuyPoint().subtract(lossMaxPoint));
            }else{
                trade.setProfitMaxPoint(null);
                trade.setLossMaxPoint(trade.getBuyPoint().add(lossMaxPoint));
            }*/
        }


        // 全民经纪人
        /*Agent agent = agentDao.findAgentByAgentInviteCode(userById.getAgentInviteCode());
        if(agent != null && Constants.USER_STATUE.VALID.equals(agent.getStatus().toString()) && Constants.Agent.AGENT_REVIEW_STATUS_SUCCESS == agent.getReviewStatus().intValue()){
            trade.setBrokerId(agent.getId());
        }*/

        trade.preInsert();
        if(ProjectConstants.THREE_SALE_SYS && !Constants.Trade.TYPE_COUPON_MONEY.equals(makeTradeBean.getType())){
            Agent3User agent3User = (Agent3User) userById;
            Agent3Trade agent3Trade = (Agent3Trade) trade;
            agent3Trade.setAgentId(agent3User.getAgentId());
            agent3Trade.setUnitsId(agent3User.getUnitsId());
            agent3Trade.setCenterId(agent3User.getCenterId());
            if(tradeMarketDao.make3(agent3Trade) != 1){
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else {
            if(tradeMarketDao.make(trade) != 1){
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }

        BigDecimal difMoney = trade.getMoney().add(trade.getFee()).multiply(new BigDecimal(-1));

        if(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType())){
            if(!moneyService.changeCouponMoney(userById.getId(), difMoney, Constants.UserMessage.TITLE_TRADE,
                    Utils.formatStr("建仓代金券变更{0}元，剩余{1}元，下单流水号：{2}", difMoney.toString(), userById.getCouponMoney().add(difMoney).toString(), trade.getSerialNo()))){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else if(Constants.Trade.TYPE_MONEY.equals(trade.getType())){
            if(!moneyService.changeMoney(userById.getId(), difMoney, userById.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_TRADE_MAKE,
                    Utils.formatStr("建仓资金变更{0}，下单流水号：{1}", difMoney.toString(), trade.getSerialNo()))){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 手动平仓
     * @param sellBean
     * @return
     */
    @Transactional
    public ResponseEntity sell(SellBean sellBean) {
        if(!tradeService.isMarketOpen()){
            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
        }

        AuthEntity curAuth = AuthContext.getCurAuth();
        User userById = userDao.findUserById(curAuth.getUserId());
        // 校验交易密码
        /*if(!sellBean.getTradePassword().equals(userById.getTradePassword())){
            return new ResponseErrorEntity(StatusCode.TRADE_PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
        }*/
        UserTradeBean trade = tradeDao.findTradeById(sellBean.getId());
        trade.setUserMoney(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType()) ? userById.getCouponMoney() : userById.getMoney());
        // 获取当前最新行情
        MarketPointBean latest = marketService.latest(trade.getCode());

        if(!doSell(trade, latest, Constants.Trade.SellType.HAND)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        if(null != trade){
            removeHoldTrade();
        }
        return new ResponseEntity(trade, HttpStatus.OK);
    }

    /**
     * 最新行情变更时，触发调用此方法
     * 加锁
     * 止盈止损平仓
     * @param latest
     */
    public synchronized void checkToSell(MarketPointBean... latest){
        for (MarketPointBean marketPointBean : latest){
            List<UserTradeBean> trades = tradeMarketDao.findStopProfixOrLoss(marketPointBean);
            if(!trades.isEmpty()){
                Log.info("发现".concat(trades.size()+"").concat("笔持仓数据需要止盈止损平仓"));
                for (UserTradeBean trade : trades){
                    // 自动平仓 TODO 1秒内波动变化很大，如何处理
                    if(!doSell(trade, marketPointBean, Constants.Trade.SellType.STOP)){
                        Log.error(trade.getSerialNo().concat("交易止盈止损平仓失败"));
                    }
                }
            }

            if(!trades.isEmpty()){
                removeHoldTrade();
            }
        }
    }

    /**
     * 平仓 手动 止盈止损 结算平仓
     *
     * @param trade
     * @param latest
     * @return
     */
    private boolean doSell(UserTradeBean trade, MarketPointBean latest, Constants.Trade.SellType sellType){
        preSell(trade, latest, sellType);

        if(tradeDao.sell(trade) != 1){
            return false;
        }
        BigDecimal difMoney = trade.getMoney().add(trade.getDifMoney());
        if(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType())){
            if(!moneyService.changeCouponMoney(trade.getUserId(), difMoney, Constants.UserMessage.TITLE_TRADE,
                    Utils.formatStr("平仓代金券变更{0}，剩余{1}元，下单流水号：{2}", difMoney.toString(), trade.getUserMoney().add(difMoney).toString(), trade.getSerialNo()))){
                return false;
            }
        }else if(Constants.Trade.TYPE_MONEY.equals(trade.getType())){
            if(!moneyService.changeMoney(trade.getUserId(), difMoney, trade.getUserMoney(), Constants.Money.MONEY_CHANGE_TYPE_TRADE_SELL,
                    Utils.formatStr("平仓资金变更{0}，下单流水号：{1}", difMoney.toString(), trade.getSerialNo()))){
                return false;
            }
        }else {
            return false;
        }

        if(Constants.Trade.SellType.HAND != sellType){
            // 写入消息
            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(trade.getUserId());
            userMessage.setTitle(Constants.UserMessage.TITLE_TRADE);
            userMessage.setContent(Utils.formatStr("系统自动平仓，交易流水：{0}", trade.getSerialNo()));
            userMessage.preInsert();
            if(userMessageDao.record(userMessage) != 1){
                return false;
            }
        }

        return true;
    }

    /**
     * 盈利算法
     * @param trade
     * @param point
     */
    private void preSell(Trade trade, MarketPointBean point, Constants.Trade.SellType sellType){
        if(point == null || StringUtils.isBlank(point.getPrice())){
            trade.setSellPoint(new BigDecimal(0));
            trade.setSellTime(new Date());
            trade.setSellType(sellType.val());
            trade.setDifMoney(new BigDecimal(0));

            return ;
        }

        BigDecimal price = Utils.toBigDecimal(point.getPrice());
        // 止盈止损按止盈止损点位计算，不按当前时间价格
        if(Constants.Trade.SellType.STOP == sellType){
            if(null != trade.getProfitMaxPoint() && Math.abs(trade.getProfitMaxPoint().subtract(price).doubleValue())
                    < Math.abs(trade.getLossMaxPoint().subtract(price).doubleValue())){
                price = trade.getProfitMaxPoint();
            }else {
                price = trade.getLossMaxPoint();
            }
        }
        trade.setSellPoint(price);
        //trade.setSellTime(point.getTime() == null ? new Date() : point.getTime());
        trade.setSellTime(new Date());
        if(point.getTime() == null){
            Log.warn("平仓行情点 ".concat(JSON.toJSONString(point)));
        }
        //平仓类型 0 手动平仓 1 止盈止损平仓 2 休市平仓
        trade.setSellType(sellType.val());

        // 计算盈亏
        // 盈亏=点值*波动值-手续费
        // 买涨 波动值=平仓价-建仓价
        // 买跌 波动值=建仓价-平仓价
        if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
            trade.setDifMoney(trade.getPointValue().multiply((trade.getSellPoint().subtract(trade.getBuyPoint()))));
        }else if(Constants.Trade.BUY_DOWN.equals(trade.getBuyUpDown())){
            trade.setDifMoney(trade.getPointValue().multiply((trade.getBuyPoint().subtract(trade.getSellPoint()))));
        }
    }

    /**
     * 结算时间触发
     * 每天定时，未平仓交易，按建仓价平仓，扣除手续费
     */
    //@Scheduled(cron="0 50 15 * * ?")
    //@Scheduled(cron="0 00 4 * * ?")
    public void settlement(){
        List<UserTradeBean> list = tradeMarketDao.findNoSellForSettlement();
        MarketPointBean latest = new MarketPointBean();
        latest.setTime(new Date());

        for(UserTradeBean trade : list){
            MarketPointBean latestPrice = marketService.latest(trade.getCode());
            latest.setPrice(latestPrice.getPrice());
            //latest.setPrice(null);
            doSell(trade, latest, Constants.Trade.SellType.TIME_STOP);
        }

        if(!list.isEmpty()){
            removeHoldTrade();
        }
    }

    public void removeHoldTrade() {
        // 移动数据到历史表
        try{
            tradeMarketDao.moveTrade();
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY, -1);
        tradeMarketDao.deleteHasMove(instance.getTime());
    }

    private final Random random = new Random();

    /**
     * 获取交易流水号
     * @return
     */
    private String serialNo(){
        return "JY" + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    public ResponseEntity updateWinPercentOrLossPercent(UpdateHoldBean updateHoldBean){
        if(!tradeService.isMarketOpen()){
            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
        }

        TradeMarketBean tradeMakeBean = new TradeMarketBean();
        tradeMakeBean.setId(updateHoldBean.getId());
        tradeMakeBean.setUserId(AuthContext.getUserId());
        UserTradeBean trade = tradeMarketDao.findHoldByUser(tradeMakeBean);
        if(null == trade){
            return new ResponseErrorEntity("订单不存在", HttpStatus.BAD_REQUEST);
        }

        trade.setProfitMax(updateHoldBean.getProfitMax());
        trade.setLossMax(updateHoldBean.getLossMax());

        BigDecimal profitMaxPoint = new BigDecimal(0);
        BigDecimal lossMaxPoint = new BigDecimal(0);

        if((null == updateHoldBean.getProfitMax() && null == updateHoldBean.getLossMax()) || (0 == updateHoldBean.getProfitMax() && 0 == updateHoldBean.getLossMax())){
            return new ResponseErrorEntity("请输入止盈或止损!", HttpStatus.BAD_REQUEST);
        }

        if(null != updateHoldBean.getLossMax()){
            profitMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(updateHoldBean.getProfitMax()/100d)).divide(trade.getPointValue()).doubleValue()));
        }

        if(null != updateHoldBean.getProfitMax()){
            lossMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(updateHoldBean.getLossMax()/100d)).divide(trade.getPointValue()).doubleValue()));
        }

        // 计算止盈止损最大点
        // 购买金额*止盈止损百分比/波动点值 （向上取整）
        //
        if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
            trade.setProfitMaxPoint(trade.getBuyPoint().add(profitMaxPoint));
            trade.setLossMaxPoint(trade.getBuyPoint().subtract(lossMaxPoint));
        }else{
            trade.setProfitMaxPoint(trade.getBuyPoint().subtract(profitMaxPoint));
            trade.setLossMaxPoint(trade.getBuyPoint().add(lossMaxPoint));
        }
        if(1 != tradeMarketDao.updateProfitMaxOrLossMaxByUser(trade)){
            return new ResponseErrorEntity("设置失败！", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("设置成功！", HttpStatus.OK);
    }
}
