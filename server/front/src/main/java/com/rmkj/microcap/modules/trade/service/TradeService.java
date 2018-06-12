package com.rmkj.microcap.modules.trade.service;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.AuthEntity;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.trademarket.AbstractMarketServer;
import com.rmkj.microcap.common.modules.trademarket.MarketPointBean;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.index.dao.ParameterSetDao;
import com.rmkj.microcap.modules.index.entity.ParameterSet;
import com.rmkj.microcap.modules.market.service.MarketService;
import com.rmkj.microcap.modules.money.dao.CashCouponDao;
import com.rmkj.microcap.modules.money.entity.CashCoupon;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.sms.service.SmsService;
import com.rmkj.microcap.modules.trade.bean.ControlGroup;
import com.rmkj.microcap.modules.trade.bean.MakeTradeBean;
import com.rmkj.microcap.modules.trade.bean.TradeHistoryBean;
import com.rmkj.microcap.modules.trade.bean.UserTradeBean;
import com.rmkj.microcap.modules.trade.dao.Ml3MemberUnitsMoneyChangeDao;
import com.rmkj.microcap.modules.trade.dao.Ml3MemberUnitsMoneyRecordDao;
import com.rmkj.microcap.modules.trade.dao.TradeDao;
import com.rmkj.microcap.modules.trade.entity.*;
import com.rmkj.microcap.modules.user.dao.Ml3MemberUnitsDao;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.dao.UserMessageDao;
import com.rmkj.microcap.modules.user.entity.Agent3User;
import com.rmkj.microcap.modules.user.entity.Ml3MemberUnits;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by renwp on 2016/10/19.
 * 交易核心业务类
 * 建仓
 * 平仓：手动、止盈止损、结算
 */
@Service
public class TradeService {

    private final Logger Log = Logger.getLogger(TradeService.class);

    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private CashCouponDao cashCouponDao;

    @Autowired
    private ParameterSetDao parameterSetDao;

    @Autowired
    private Ml3MemberUnitsMoneyRecordDao ml3MemberUnitsMoneyRecordDao;

    @Autowired
    private Ml3MemberUnitsMoneyChangeDao ml3MemberUnitsMoneyChangeDao;

    @Autowired
    private Ml3MemberUnitsDao ml3MemberUnitsDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private AbstractMarketServer marketServer;

    private List<Contract> contracts;

    private final static Map<String, Integer> map_precision = new HashMap();
    private final static Map<String, Integer> map_precision2 = new HashMap();

    private String[] _codes = null;

    /**
     * 更新合约
     */
//    @Scheduled(initialDelay = 3000, fixedRate = Constants.REFRESH_CONTRACTS)
    public void contracts(){
        contracts = tradeDao.contractList();
        //int len = Math.min(contracts.size(), 3);
        int len = Math.min(contracts.size(), contracts.size());
        String[] codes = _codes;
        _codes = new String[len];
        Contract contract = null;
        for (int i = 0; i < len; i++){
            contract = contracts.get(i);
//            contract.setMarketOpen(isMarketOpen(new String[]{contract.getBeginTime(), contract.getEndTime()}, contract.getExemptClosed() == 1));

            map_precision.put(contract.getCode(), contract.getPrecision());
            contract.setPrecision(0);
            map_precision2.put(contract.getCode(), contract.getPrecision());
            _codes[i] = contract.getCode();
        }

        if(codes != null){
            // 新旧数据总数可能不相同
            int newCount = _codes.length;
            int oldCount = codes.length;
            boolean[] bb = new boolean[oldCount];
            // 检查code是否变动
            if(codes != null){
                for (int i = 0; i < _codes.length; i++){
                    for(int j = 0; j < codes.length; j++){
                        if(_codes[i].equals(codes[j])){
                            newCount--;
                            oldCount--;
                            bb[j] = true;
                        }
                    }
                }
            }

            if(newCount != 0){
                marketServer.reset();
            }

            for (int i = 0; i < bb.length; i++){
                if(!bb[i]){
                    marketServer.clearCodeCache(codes[i]);
                }
            }
        }
    }

    /**
     * 产品价格精度
     * @return
     */
    public static Map<String, Integer> getPrecisions(){
        return map_precision;
    }

    public static Map<String, Integer> getPrecisions2(){
        return map_precision2;
    }

    public List<Contract> contractList() {
        if(contracts == null){
            contracts();
        }
        return contracts;
    }

    public String[] contractCodes() {
        if(_codes == null){
            tradeDao.contractList();
        }
        return _codes;
    }

    /**
     * 获取登录用户的持仓数据
     * @return
     */
    public List<Trade> tradeList() {
        return tradeDao.tradeListByUserId(AuthContext.getUserId());
    }

    /**
     * 获取登录用户的所有交易数据
     * @return
     */
    public List<Trade> tradeHistory(TradeHistoryBean tradeHistoryBean) {
        tradeHistoryBean.setUserId(AuthContext.getUserId());
        return tradeDao.tradeHistory(tradeHistoryBean);
    }

    /**
     * 获取登录用户的今天结算数据
     * @return
     */
    public List<Trade> toDayTradeList() {
        return tradeDao.toDayTradeListByUserId(AuthContext.getUserId());
    }

    /**
     * 建仓下单
     * @param makeTradeBean
     * @return
     */
    @Transactional
    public ResponseEntity make(MakeTradeBean makeTradeBean) {
        Contract contract = tradeDao.findContractByCode(makeTradeBean.getCode());
        if(!isMarketOpen() || contract == null || !isMarketOpen(new String[]{contract.getBeginTime(), contract.getEndTime()}, contract.getExemptClosed().intValue() == 1)){
            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
        }

        AuthEntity curAuth = AuthContext.getCurAuth();
        User userById = null;
        // 查询用户
        if(ProjectConstants.THREE_SALE_SYS){
            userById = userDao.findAgent3UserByIdForTrade(curAuth.getUserId());
        }else{
            userById = userDao.findUserByIdForTrade(curAuth.getUserId());
        }

        //查询用户持仓单数,持仓金额，持仓单数和持仓金额不得大于ParameterSet表中的持仓单数，金额

        /**
         * 查询全部持有仓数金额相加,再加新持仓金额进行比较
         */
        ParameterSet parameterSet = parameterSetDao.findParameterSet();
        List<Trade> holdList = tradeDao.findUserTradeCount(curAuth.getUserId());
        Double holdMoney = 0D;
        for(Trade trade : holdList){
            holdMoney += trade.getMoney().doubleValue();
        }
        holdMoney += makeTradeBean.getMoney().doubleValue();
        if(null != parameterSet) {
            if(parameterSet.getHoldMoney().compareTo(new BigDecimal(holdMoney)) == -1){ //1000  1100
                return  new ResponseErrorEntity(StatusCode.HOLD_MONEY, HttpStatus.BAD_REQUEST);
            }

            if (holdList.size() >= parameterSet.getHoldCount()) {
                return new ResponseErrorEntity(StatusCode.HOLD_POSITIONS, HttpStatus.BAD_REQUEST);
            }
        }

//        // 校验交易密码
//        if(!makeTradeBean.getTradePassword().equals(userById.getTradePassword())){
//            return new ResponseErrorEntity(StatusCode.TRADE_PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
//        }

        if(userById.getMoney().subtract(makeTradeBean.getMoney()).doubleValue() < 0){
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }

        // 获取当前最新行情
        MarketPointBean latest = marketService.latest(makeTradeBean.getCode());

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        Trade trade = ProjectConstants.THREE_SALE_SYS ? new Agent3Trade() : new Trade();
        trade.setSerialNo(serialNo());
        trade.setModel(Constants.Trade.MODEL_POINT);
        trade.setUserId(curAuth.getUserId());
        // 交易类型 资金 资金+代金券
        if(StringUtils.isNotBlank(makeTradeBean.getCouponId())){
            trade.setType(Constants.Trade.TYPE_MONEY_AND_COUPON);
        }else {
            trade.setType(Constants.Trade.TYPE_MONEY);
        }
        trade.setBuyTime(now);

        trade.setBuyPoint(Utils.toBigDecimal(latest.getPrice()));
        trade.setBuyUpDown(makeTradeBean.getBuyUpDown());
        trade.setCode(contract.getCode());
        trade.setContractName(contract.getName());
        trade.setMoney(makeTradeBean.getMoney());

        // 买点位的交易
        //trade.setOffTime(makeTradeBean.getOffTime());
        Integer offPoint = Integer.parseInt(makeTradeBean.getOffPoint());
        Integer precision = getPrecisions2().get(makeTradeBean.getCode());
        String strOffPoint = offPoint/Math.pow(10,precision) +"";
        trade.setOffPoint(strOffPoint);
        //calendar.add(Calendar.MINUTE, Integer.parseInt(trade.getOffTime().replaceAll("M","")));
        //trade.setSellTime(calendar.getTime());
        //trade.setSellPoint(trade.getBuyPoint().add(offPoint));
        //BigDecimal percentProfit = Utils.toBigDecimal(contract.percentProfit(makeTradeBean.getOffTime())).divide(new BigDecimal(100));
        //收益比例
        //BigDecimal percentProfit = Utils.toBigDecimal(contract.percentProfit(makeTradeBean.getOffPoint())).divide(new BigDecimal(100));
        BigDecimal winMoney = makeTradeBean.getMoney().multiply(Utils.toBigDecimal(contract.percentProfit(makeTradeBean.getOffPoint())).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN));
        BigDecimal fee = trade.getMoney().subtract(winMoney);
        trade.setWinMoney(winMoney);
        trade.setFee(fee);

//        String money = makeTradeBean.getMoney().toString();
//        trade.setPointValue(Utils.toBigDecimal(contract.pointMoney(money)));
//        trade.setFee(Utils.toBigDecimal(contract.fee(money)));
//        trade.setProfitMax(makeTradeBean.getProfitMax());
//        trade.setLossMax(makeTradeBean.getLossMax());
//
//        // 计算止盈止损最大点
//        // 购买金额*止盈止损百分比/波动点值 （向上取整）
//        //
//        BigDecimal profitMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(trade.getProfitMax()/100d)).divide(trade.getPointValue()).doubleValue()));
//        BigDecimal lossMaxPoint = new BigDecimal(Math.ceil(trade.getMoney().multiply(new BigDecimal(trade.getLossMax()/100d)).divide(trade.getPointValue()).doubleValue()));
//        if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
//            trade.setProfitMaxPoint(trade.getBuyPoint().add(profitMaxPoint));
//            trade.setLossMaxPoint(trade.getBuyPoint().subtract(lossMaxPoint));
//        }else{
//            trade.setProfitMaxPoint(trade.getBuyPoint().subtract(profitMaxPoint));
//            trade.setLossMaxPoint(trade.getBuyPoint().add(lossMaxPoint));
//        }

        trade.setParent1Id(userById.getParent1Id());
        trade.setParent2Id(userById.getParent2Id());
        trade.setParent3Id(userById.getParent3Id());

        if(trade.getParent1Id() == null && trade.getParent2Id() == null && trade.getParent3Id() == null){
            trade.setBalanceStatus(Constants.Trade.BALANCE_OVER);
        }

        trade.setReturn2Ratio(userById.getReturn2Ratio());
        trade.setReturn3Ratio(userById.getReturn3Ratio());

        trade.preInsert();
        if(ProjectConstants.THREE_SALE_SYS && !Constants.Trade.TYPE_COUPON_MONEY.equals(makeTradeBean.getType())){
            Agent3User agent3User = (Agent3User) userById;
            Agent3Trade agent3Trade = (Agent3Trade) trade;
            agent3Trade.setAgentId(agent3User.getAgentId());
            agent3Trade.setUnitsId(agent3User.getUnitsId());
            agent3Trade.setCenterId(agent3User.getCenterId());
            if(tradeDao.make3(agent3Trade) != 1){
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else {
            if(tradeDao.make(trade) != 1){
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }

        //添加交易总量
        userDao.updateUserTradeCount(curAuth.getUserId());

        BigDecimal difMoney = trade.getMoney().multiply(new BigDecimal(-1));

        if(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType())){
            if(!moneyService.changeCouponMoney(curAuth.getUserId(), difMoney, Constants.UserMessage.TITLE_TRADE,
                    Utils.formatStr("建仓代金券变更{0}元，剩余{1}元，下单流水号：{2}", difMoney.toString(), userById.getCouponMoney().add(difMoney).toString(), trade.getSerialNo()))){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else if(Constants.Trade.TYPE_MONEY.equals(trade.getType()) || Constants.Trade.TYPE_MONEY_AND_COUPON.equals(trade.getType())){
            // 满多少钱代金券抵扣
            CashCoupon cashCoupon = null;
            if(StringUtils.isNotBlank(makeTradeBean.getCouponId())){
                cashCoupon = cashCouponDao.findCashCouponById(makeTradeBean.getCouponId());
                if(!Constants.Coupon.STATUS_DEFAULT.equals(cashCoupon.getStatus()) || !cashCoupon.getUserId().equals(curAuth.getUserId())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new ResponseErrorEntity(StatusCode.COUPON_INVALID, HttpStatus.BAD_REQUEST);
                }
                cashCoupon.setRemark(trade.getSerialNo());
                cashCouponDao.useCashCoupon(cashCoupon);
                difMoney = difMoney.add(cashCoupon.getMoney());
            }

            // cashCoupon == null ? Utils.formatStr("建仓资金变更{0}，下单流水号：{1}", difMoney.toString(), trade.getSerialNo()) : Utils.formatStr("建仓资金变更{0}，下单流水号：{1}，使用代金券{2}元", difMoney.toString(), trade.getSerialNo(), cashCoupon.getMoney().toString());
            String remark = trade.getSerialNo();
            if(!moneyService.changeMoney(curAuth.getUserId(), difMoney, userById.getMoney(), Constants.Money.MONEY_CHANGE_TYPE_TRADE_MAKE, remark)){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

//    /**
//     * 手动平仓
//     * @param sellBean
//     * @return
//     */
//    @Transactional
//    @Deprecated
//    public ResponseEntity sell(SellBean sellBean) {
//        if(!isMarketOpen()){
//            return new ResponseErrorEntity(StatusCode.MARKET_CLOSE, HttpStatus.BAD_REQUEST);
//        }
//
//        AuthEntity curAuth = AuthContext.getCurAuth();
//        User userById = userDao.findUserById(curAuth.getUserId());
//        // 校验交易密码
//        if(!sellBean.getTradePassword().equals(userById.getTradePassword())){
//            return new ResponseErrorEntity(StatusCode.TRADE_PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
//        }
//        UserTradeBean trade = tradeDao.findTradeById(sellBean.getId());
//        trade.setUserMoney(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType()) ? userById.getCouponMoney() : userById.getMoney());
//        // 获取当前最新行情
//        MarketPointBean latest = marketService.latest(trade.getCode());
//
//        if(!doSell(trade, latest, Constants.Trade.SellType.HAND)){
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity(trade, HttpStatus.OK);
//    }

//    /**
//     * 最新行情变更时，触发调用此方法
//     * 加锁
//     * 止盈止损平仓
//     * @param latest
//     */
//    public synchronized void checkToSell(MarketPointBean... latest){
//        for (MarketPointBean marketPointBean : latest){
//            List<UserTradeBean> trades = tradeDao.findStopProfixOrLoss(marketPointBean);
//            if(!trades.isEmpty()){
//                Log.info("发现".concat(trades.size()+"").concat("笔持仓数据需要止盈止损平仓"));
//                for (UserTradeBean trade : trades){
//                    // 自动平仓 TODO 1秒内波动变化很大，如何处理
//                    if(!doSell(trade, marketPointBean, Constants.Trade.SellType.STOP)){
//                        Log.error(trade.getSerialNo().concat("交易止盈止损平仓失败"));
//                    }
//                }
//            }
//        }
//    }

    /**
     * 定时平仓
     * 固定延时
     */
//    @Scheduled(initialDelay = 10000, fixedDelay = 1000)
    public void checkToSell(){
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MILLISECOND, 0);
        Date now = instance.getTime();

        List<MarketPointBean> latest = marketService.latest(_codes);
        //MarketPointBean latest = marketService.latest(makeTradeBean.getCode());
        Map<String, MarketPointBean> map = new HashMap<>();
        latest.forEach(point -> {
            if(point != null){
                if(point.getTime() == null || point.getTime().getTime() - now.getTime() > 60*1000){
                    if(point.getTime() != null){
                        Log.error("定时平仓 > ".concat(point.getCode().concat("时间差：").concat((point.getTime().getTime() - now.getTime())/1000l+"")));
                    }
                }
                point.setTime(null);
                map.put(point.getCode(), point);
            }
        });

        //根据三种产品的不同的合约名和当前价格，分别获取

        String code = null;
        List<MarketPointBean> latests = new ArrayList<>();
        for(Contract contract: contracts){
            // 全年无休市
            if(contract.getExemptClosed().intValue() == 1){
                latests.add(map.get(contract.getCode()));
            }else{
                // 正常休市
                if(isMarketOpen(new String[]{contract.getBeginTime(), contract.getEndTime()})){
                    latests.add(map.get(contract.getCode()));
                }
            }
        }
        List<UserTradeBean> trades = tradeDao.findOffPointTrade(latests);

        final UserTradeBean[] preTrade = {null};
        trades.forEach(trade -> {
            MarketPointBean latestPoint = map.get(trade.getCode());
            if(latestPoint != null){
//                latestPoint = controlTrade(trade, latestPoint);
                if(!doSell(trade, latestPoint, Constants.Trade.SellType.STOP, preTrade[0])){
                    Log.error(trade.getSerialNo().concat("系统自动平仓失败"));
                }
            }else{
                Log.error(trade.getCode().concat(" 无最新行情"));
            }
            preTrade[0] = trade;
        });

        // 移动数据到历史表
        if(!trades.isEmpty()){
            tradeDao.moveTrade();
            instance.add(Calendar.HOUR_OF_DAY, -1);
            tradeDao.deleteHasMove(instance.getTime());
        }
    }

    private String doTradeResult(Trade trade){
        return doTradeResult(trade, false);
    }

    /**
     * 得到盈亏平的结果
     * @param trade
     * @param force
     * @return
     */
    private String doTradeResult(Trade trade, boolean force){
        if(force || (trade != null && trade.getResult() == null)){
            if(trade.getBuyPoint().doubleValue() == trade.getSellPoint().doubleValue()){
                // 平
                trade.setResult(Constants.Trade.RESULT_DRAW);
            }else {
                if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
                    if(trade.getBuyPoint().doubleValue() < trade.getSellPoint().doubleValue()){
                        // 赢
                        trade.setResult(Constants.Trade.RESULT_WIN);
                    }else if(trade.getBuyPoint().doubleValue() > trade.getSellPoint().doubleValue()){
                        // 亏
                        trade.setResult(Constants.Trade.RESULT_LOSE);
                    }
                }else if(Constants.Trade.BUY_DOWN.equals(trade.getBuyUpDown())){
                    if(trade.getBuyPoint().doubleValue() > trade.getSellPoint().doubleValue()){
                        // 赢
                        trade.setResult(Constants.Trade.RESULT_WIN);
                    }else if(trade.getBuyPoint().doubleValue() < trade.getSellPoint().doubleValue()){
                        // 亏
                        trade.setResult(Constants.Trade.RESULT_LOSE);
                    }
                }
            }
            return trade.getResult();
        }
        return trade == null ? null : trade.getResult();
    }

    /**
     * 平仓 手动 止盈止损 结算平仓
     *
     * @param trade
     * @param latest
     * @param preUserTrade
     * @return
     */
    private boolean doSell(UserTradeBean trade, MarketPointBean latest, Constants.Trade.SellType sellType, UserTradeBean preUserTrade){

        preSell(trade, latest, sellType);
        trade.setSellTime(new Date());
        if(tradeDao.sell(trade) != 1){
            return false;
        }
        // 本金+盈亏金额
        if(Constants.Trade.RESULT_DRAW.equals(trade.getResult())){
            trade.setMoney(trade.getMoney().subtract(trade.getFee()));
        }else if(Constants.Trade.RESULT_LOSE.equals(trade.getResult())){
            trade.setDifMoney(trade.getMoney().multiply(new BigDecimal(-1)));
        }else if(Constants.Trade.RESULT_WIN.equals(trade.getResult())){
            trade.setDifMoney(trade.getWinMoney().subtract(trade.getFee()));
        }else{
            //只扣手续费
            trade.setMoney(trade.getMoney().subtract(trade.getFee()));
        }
        BigDecimal difMoney = trade.getMoney().add(trade.getDifMoney());

        if(Constants.Trade.TYPE_COUPON_MONEY.equals(trade.getType())){
            if(!moneyService.changeCouponMoney(trade.getUserId(), difMoney, Constants.UserMessage.TITLE_TRADE,
                    Utils.formatStr("平仓代金券变更{0}，剩余{1}元，下单流水号：{2}", difMoney.toString(), trade.getUserMoney().add(difMoney).toString(), trade.getSerialNo()))){
                return false;
            }
        }else if(Constants.Trade.TYPE_MONEY.equals(trade.getType()) || Constants.Trade.TYPE_MONEY_AND_COUPON.equals(trade.getType())){
            // Utils.formatStr("平仓资金变更{0}，下单流水号：{1}", difMoney.toString(), trade.getSerialNo())
            // 同一用户多单平仓
            if(preUserTrade != null && trade.getUserId().equals(preUserTrade.getUserId())){
                trade.setUserMoney(preUserTrade.getUserMoney());
            }
            if(!moneyService.changeMoney(trade.getUserId(), difMoney, trade.getUserMoney(), Constants.Money.MONEY_CHANGE_TYPE_TRADE_SELL,
                    trade.getSerialNo())){
                return false;
            }else{
                trade.setUserMoney(trade.getUserMoney().add(difMoney));
            }
        }else {
            return false;
        }

        if(Constants.Trade.SellType.TIME_STOP == sellType){
            // 写入消息
            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(trade.getUserId());
            userMessage.setTitle(Constants.UserMessage.TITLE_TRADE);
            userMessage.setContent(Utils.formatStr("系统休市自动平仓，交易流水：{0}", trade.getSerialNo()));
            userMessage.preInsert();
            if(userMessageDao.record(userMessage) != 1){
                return false;
            }
        }

        return true;
    }

    /**
     * 盈利算法
     * point或者point.price为空，则全额返钱
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
//        // 止盈止损按止盈止损点位计算，不按当前时间价格
//        if(Constants.Trade.SellType.STOP == sellType){
//            if(Math.abs(trade.getProfitMaxPoint().subtract(price).doubleValue())
//                    < Math.abs(trade.getLossMaxPoint().subtract(price).doubleValue())){
//                price = trade.getProfitMaxPoint();
//            }else {
//                price = trade.getLossMaxPoint();
//            }
//        }
        trade.setSellPoint(price);
        trade.setSellTime(point.getTime());
        if(point.getTime() == null){
            Log.warn("平仓行情点 ".concat(JSON.toJSONString(point)));
        }
        //平仓类型 0 手动平仓 1 止盈止损平仓 2 休市平仓 3 异常平仓
        trade.setSellType(sellType.val());

        // 买时间的 计算盈亏
        doTradeResult(trade, true);
        if(Constants.Trade.RESULT_WIN.equals(trade.getResult())){
            //trade.setDifMoney(trade.getMoney().subtract(trade.getFee()));
            //盈利金额不减手续费，返钱的时候在减
            trade.setDifMoney(trade.getWinMoney());
        }else if(Constants.Trade.RESULT_LOSE.equals(trade.getResult())){
            trade.setDifMoney(trade.getMoney().multiply(new BigDecimal(-1)).add(trade.getFee()));
        }else {
            trade.setDifMoney(new BigDecimal(0));
            //平仓扣除收益率之外的手续费
            //trade.setDifMoney(trade.getMoney().subtract(trade.getWinMoney()).multiply(new BigDecimal(-1)));
        }

//        // 盈亏=点值*波动值-手续费
//        // 买涨 波动值=平仓价-建仓价
//        // 买跌 波动值=建仓价-平仓价
//        if(Constants.Trade.BUY_UP.equals(trade.getBuyUpDown())){
//            trade.setDifMoney(trade.getPointValue().multiply((trade.getSellPoint().subtract(trade.getBuyPoint()))).subtract(trade.getFee()));
//        }else if(Constants.Trade.BUY_DOWN.equals(trade.getBuyUpDown())){
//            trade.setDifMoney(trade.getPointValue().multiply((trade.getBuyPoint().subtract(trade.getSellPoint()))).subtract(trade.getFee()));
//        }
    }

    /**
     * 结算时间触发
     * 每天定时，未平仓交易，按建仓价平仓，扣除手续费
     */
    //@Scheduled(cron="0 00 4 * * ?")
    public void settlement(){
        List<UserTradeBean> list = tradeDao.findNoSellForSettlement();
        MarketPointBean latest = new MarketPointBean();
        latest.setTime(new Date());
        UserTradeBean preTradeBean = null;
        for(UserTradeBean trade : list){
            latest.setPrice(null);
            doSell(trade, latest, Constants.Trade.SellType.TIME_STOP, preTradeBean);
            preTradeBean = trade;
        }

        Calendar instance = Calendar.getInstance();
        // 移动数据到历史表
        if(!list.isEmpty()){
            tradeDao.moveTrade();
            instance.add(Calendar.HOUR_OF_DAY, -1);
            tradeDao.deleteHasMove(instance.getTime());
        }
    }

    /**
     * 系统启动
     */
//    @Scheduled(initialDelay = 10000, fixedDelay = 600000)
//    public void doErrorTrade(){
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.SECOND, -10);
//        List<UserTradeBean> list = tradeDao.findNotOnTime(instance.getTime());
//        MarketPointBean latest = new MarketPointBean();
//        latest.setTime(new Date());
//        for(UserTradeBean trade : list){
//            latest.setPrice(null);
//            doSell(trade, latest, Constants.Trade.SellType.ERROR);
//        }
//    }

    public boolean isMarketOpen(String[] times){
        return isMarketOpen(times, false);
    }
    public boolean isMarketOpen(String[] times, boolean isExemptClosed){
        if(times == null || times.length != 2 || times[0] == null || times[1] == null){
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        String[] beginStr = times[0].split(":");
        String[] endStr = times[1].split(":");

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginStr[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(beginStr[1]));

        Date begin = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endStr[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(endStr[1]));
        Date end = calendar.getTime();

        // 结束时间小于开始时间，则加一天
        if(end.before(begin)){
            if(now.before(begin)){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                begin = calendar.getTime();
            }else{
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                end = calendar.getTime();
            }
        }

        //判断开始时间是否是周末
        calendar.setTime(begin);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        // 周末休市
        if(!isExemptClosed){
            if(week == 7 || week == 1){
                return false;
            }
        }

        return !now.before(begin) && !now.after(end);
    }

    /**
     * 系统默认 开市时间
     * @return
     */
    public boolean isMarketOpen(){
        return true;
    }

    /**
     * 周末统一休市
     * @return
     */
    public String[] marketOpenTime(){
        // TODO 开盘时间
        return new String[]{"08:00", "04:00"};
    }

    private final Random random = new Random();

    /**
     * 获取交易流水号
     * @return
     */
    private String serialNo(){
        return "JY" + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    /**
     * 保证金浮动
     */
    //@Scheduled(initialDelay = 1000 * 60 * 1, fixedRate = 1000 * 60 * 1)
    public void unitsMoneyFloat(){
        if(ProjectConstants.PRO_DEBUG){
            return;
        }

        Date date = new Date();
        List<Trade> unitsMoneySumList = tradeDao.findUnitsMoneySumList();//查询会员单位总盈亏
        for(Trade unitsMoney : unitsMoneySumList){
            Trade trade = tradeDao.findTradeSumGroupByUnitsId(unitsMoney);
//            if(null == trade){
//                continue;
//            }

            //查询会员单位提现金额
            Ml3MemberUnitsMoneyRecord unitsMoneyRecord = ml3MemberUnitsMoneyRecordDao.findUnitsMoneyRecord(unitsMoney.getUnitsId());
            if(null != unitsMoneyRecord){
                unitsMoney.setSumUnitsDifMoney(unitsMoney.getSumUnitsDifMoney().add(unitsMoneyRecord.getSumMoney())); //会员单位保证金余额  总盈亏+提现
            }

            Ml3MemberUnits memberUnits = ml3MemberUnitsDao.get(unitsMoney.getUnitsId());
            if(ml3MemberUnitsDao.updateUnitsMoneyLossAndProfitById(unitsMoney) == 1) { //计算会员保证金余额  累计保证金-总盈亏
                recordMl3MemberUnitsMoneyChange(unitsMoney, memberUnits, date);
            }

        }

    }

    public void recordMl3MemberUnitsMoneyChange(Trade trade, Ml3MemberUnits ml3MemberUnits, Date date){
        /**
         * 查询当前会员单位保证金
         */

        Ml3MemberUnitsMoneyChange ml3MemberUnitsMoneyChange = new Ml3MemberUnitsMoneyChange();
        ml3MemberUnitsMoneyChange.preInsert();
        ml3MemberUnitsMoneyChange.setUnitsId(trade.getUnitsId());
        ml3MemberUnitsMoneyChange.setUnitsName(ml3MemberUnits.getName());
        ml3MemberUnitsMoneyChange.setType(Constants.UNITS_MONEY_CHANGE_TYPE.FLOAT_MONEY);//1
        ml3MemberUnitsMoneyChange.setDifMoney(trade.getSumUnitsDifMoney());
        ml3MemberUnitsMoneyChange.setBeforeMoney(new BigDecimal(ml3MemberUnits.getBondMoney()));//计算前 当前保证金余额
        ml3MemberUnitsMoneyChange.setAfterMoney(new BigDecimal(ml3MemberUnits.getBondMoney()).subtract(trade.getSumUnitsDifMoney()));//计算后 累计保证金 - 总盈亏
        ml3MemberUnitsMoneyChange.setCreateTime(date);
        ml3MemberUnitsMoneyChange.setEndTime(new Date());
        ml3MemberUnitsMoneyChangeDao.insert(ml3MemberUnitsMoneyChange);
        Log.info(trade.getUnitsId() + "会员单位保证金浮动开始时间：" + date);
        Log.info(trade.getUnitsId() + "会员单位保证金浮动结束时间：" + new Date());

        if(!ProjectConstants.WARNING_DEBUG){
            if(ml3MemberUnitsMoneyChange.getAfterMoney().compareTo(ml3MemberUnits.getMoneyLimit()) == -1){
                String str = "【品道云商】尊敬的会员单位：{0}，您的保证金余额不足{1}，请及时追加， 谢谢合作！";
                smsService.sendSmsRemindUnitsAddMoney(Utils.formatStr(str, ml3MemberUnits.getName(), ml3MemberUnits.getMoneyLimit().toString()), ml3MemberUnits.getMobile());
            }
        }
    }

}
