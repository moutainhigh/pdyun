package com.rmkj.microcap.modules.chanong.sub.service;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.ResultError;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.chanong.sub.bean.*;
import com.rmkj.microcap.modules.chanong.sub.dao.GoodsTypeDao;
import com.rmkj.microcap.modules.chanong.sub.dao.SubDao;
import com.rmkj.microcap.modules.chanong.trade.dao.TradeDao;
import com.rmkj.microcap.modules.chanong.trade.entity.TradeBean;
import com.rmkj.microcap.modules.index.service.ScheduleService;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.user.dao.UserDao;
import com.rmkj.microcap.modules.user.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jinghao on 2018/4/24.
 */
@Service
@Lazy(false)
public class SubService {
    private static Logger logger = Logger.getLogger(SubService.class);
    private final Random random = new Random();
    @Autowired
    private SubDao subDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TradeDao tradeDao;
    @Autowired
    private GoodsTypeDao goodsTypeDao;
    @Autowired
    private MoneyService moneyService;
    @Autowired
    private ScheduleService scheduleService;

    /**
     * 获取认购商品
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getSubGoods(String status, String goodsTypeId, String isStore){
        Map<String,Object> ret = new HashedMap();
        List<SubGoodsBean> subGoodsList;
        if("1".equals(isStore)){
            subGoodsList = subDao.getStoreGoodsList(AuthContext.getUserId());
        }else{
            subGoodsList = subDao.getSubGoods(status, goodsTypeId);
        }
        if(status.equals(Constants.SubGoodsStatus.SUB)){//认购
            List<SubGoodsBean> retSubGoodsList = new ArrayList<>();
            subGoodsList.forEach(subGoodsBean -> {
                //认购总数量、认购剩余数量：
                int subGoodsTotalNum = new BigDecimal(subGoodsBean.getGoodsTotalNum()).multiply(subGoodsBean.getSubScale()).divide(new BigDecimal(100),0,BigDecimal.ROUND_HALF_DOWN).intValue();
                int subGoodsLeftNum = subGoodsTotalNum - (subGoodsBean.getGoodsTotalNum() - subGoodsBean.getGoodsLeftNum());
                subGoodsBean.setSubGoodsNum(subGoodsTotalNum);
                subGoodsBean.setSubGoodsLeftNum(subGoodsLeftNum);

                //规格：
//                List<String> specList = new ArrayList<String>();
                String[] specTotalNum = subGoodsBean.getSubTotalNum().split(",");
                String[] specHoldNum  = subGoodsBean.getSubMakeNum().split(",");
                String[] specSendNum  = subGoodsBean.getSubSendNum().split(",");
                for(int i=0;i<specTotalNum.length;i++){
                    String spec = "买".concat(specTotalNum[i]).concat("存").concat(specHoldNum[i]).concat("提").concat(specSendNum[i]);
//                    specList.add(spec);
//                    subGoodsBean.setSubGoodsSpec(spec);
                    SubGoodsBean newSubGoodsBean = new SubGoodsBean();
//                    newSubGoodsBean = subGoodsBean;
                    ReBeanUtils.copyProperties(newSubGoodsBean,subGoodsBean);
//                    try {
//                        PropertyUtils.copyProperties(newSubGoodsBean,subGoodsBean);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    newSubGoodsBean.setSubGoodsSpec(spec);
                    newSubGoodsBean.setSubBuyNum(Integer.valueOf(specTotalNum[i]).intValue());
                    retSubGoodsList.add(newSubGoodsBean);
                }
//                subGoodsBean.setSpecList(specList);
            });
            ret.put("list",retSubGoodsList);
        }else{//购销、下架
            //购销中商品添加卖出价和最新价
            List<TradeBean> buyPointList = tradeDao.findGroupTodayBuyPointMin(null);
            List<TradeBean> sellPointList = tradeDao.findGroupTodaySellPoint(null);
            for(SubGoodsBean subGoodsBean : subGoodsList){
                //最低卖出价
                for (TradeBean tradeBean : buyPointList){
                    if(subGoodsBean.getId().equals(tradeBean.getGoodsId())){
                        subGoodsBean.setBuyPointMin(Utils.toBigDecimal(tradeBean.getBuyPoint()));
                        continue;
                    }
                }
                //最新成交价
                for (TradeBean tradeBean : sellPointList){
                    if(subGoodsBean.getId().equals(tradeBean.getGoodsId())){
                        subGoodsBean.setSellPointNew(tradeBean.getSellPoint());
                        continue;
                    }
                }
            }
            if(subGoodsList.isEmpty()){
                SubGoodsBean subGoodsBean = new SubGoodsBean();
                subGoodsBean.setId("");
                subGoodsBean.setGoodsName("");
                subGoodsBean.setImgLoadPath("");
                subGoodsBean.setGoodsSubPrice(new BigDecimal(0));
                subGoodsBean.setSellPointNew(new BigDecimal(0));
                subGoodsBean.setBuyPointMin(new BigDecimal(0));
                subGoodsList.add(subGoodsBean);
            }

            ret.put("list",subGoodsList);
        }

        return ret;
    }

    /**
     * 更改商品的认购天数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updGoodsSubLeftDays(){
        //修改剩余认购天数
        subDao.updGoodsSubLeftDays();
        //把未认购完的商品添加到所属用户的持仓订单中
        List<GoodsBean> list = subDao.getDaysEndGoods();
        list.forEach(goodsBean -> {
            Map m = new HashedMap();
            m.put("goodsId",goodsBean.getId());
            m.put("userId",goodsBean.getUserId());
            m.put("leftNum",goodsBean.getGoodsLeftNum());
            subDao.updUserHoldTrade(m);
        });
        //积分返还
        ScalesBean scalesBean = subDao.getScales();
        List<User> integralList = subDao.getUserIntegralList();
        integralList.forEach(user -> {
            BigDecimal changeIntegral = user.getIntegralUnpos().multiply(scalesBean.getIntegralReturnScale());
            user.setIntegralPos(user.getIntegralPos().add(changeIntegral));
            user.setIntegralUnpos(user.getIntegralUnpos().subtract(changeIntegral));
            subDao.updUserReturnIntegral(user);
            //添加积分明细
            IntegralBean integralBean = new IntegralBean();
            integralBean.setId(Utils.uuid());
            integralBean.setUserId(user.getId());
            integralBean.setIntegralBefore(user.getIntegralPos());
            integralBean.setType("2");//状态： 1-正积分 2-负积分
            integralBean.setIntegral(changeIntegral);
            integralBean.setIntegralAfter(user.getIntegralPos().add(changeIntegral));
            integralBean.setCreateTime(new Date());
            integralBean.setRemark("日返负积分");
            subDao.addIntegral(integralBean);
        });
        //修改认购天数为0的商品状态
        subDao.updGoodsStatus();

        //每天凌晨撤销全部挂单
        List<SubMakeBean> hangList = subDao.getHangTradeList();
        hangList.forEach(subMakeBean -> {
            ResponseEntity responseEntity = cancelHangTrade(subMakeBean.getSerialNo());
            if(!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
               return;
            }
        });
    }

    /**
     * 通过id获取商品
     * @param id
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getSubGoodsById(String id){
        Map<String,Object> ret = new HashedMap();
        GoodsBean goodsBean = subDao.getSubGoodsById(id);
        ret.put("goodsInfo",goodsBean);
        if(null == goodsBean){
            ret.put("retailPrice", new BigDecimal(0));
            ret.put("basePrice", new BigDecimal(0));
            ret.put("newPrice", new BigDecimal(0));
            return ret;
        }
        //添加商品最新零售价 秒杀基准价 当前最新成交价
        getNewPrice(id, goodsBean, ret);
        return ret;
    }

    /**
     * 获取最新零售价，基准价，最新成交价
     * @param goodsId
     * @param goodsBean
     * @param ret
     * @return
     */
    public Map<String, Object> getNewPrice(String goodsId, GoodsBean goodsBean, Map<String, Object> ret){
        /**
         * 获取开盘时间段，不在开盘时间段内获取零售价 * 10,在开盘时间段内则获取基准价，开盘时间内一旦有最新成交则获取最新成交价
         */
        BigDecimal retailPrice = new BigDecimal(0);
        BigDecimal basePrice = new BigDecimal(0);
        BigDecimal newPrice = new BigDecimal(0);

        String openTimeStr = (String) scheduleService.getParameter(Constants.PARAMETER_SET_KEY.OPEN_TIME);
        String weekDaySet = (String) scheduleService.getParameter(Constants.PARAMETER_SET_KEY.WEEK_DAY_SET);
        //验证是否在规定开盘日期
        String[] weekDay = weekDaySet.split(",");
        String[] openTime = openTimeStr.split(",");
        //休市则返回零售价
        /*if(!isMarketOpen(openTime, weekDay)){*/
            retailPrice  = goodsBean.getGoodsSubPrice().multiply(new BigDecimal(10));
        /*}*/
        /**
         * 开盘返回基准价，或最新成交价
         * 最新成交价为空时返回基准价
         */
        TradeBean todaySellPoint = tradeDao.getTodaySellPoint(goodsId);
        if(null == todaySellPoint){
            basePrice = goodsBean.getGoodsCost();
        }else{
            basePrice = goodsBean.getGoodsCost();
            newPrice = todaySellPoint.getSellPoint();
        }

        ret.put("retailPrice", retailPrice);
        ret.put("basePrice", basePrice);
        ret.put("newPrice", newPrice);
        return ret;
    }

    public boolean isMarketOpen(String[] openTime, String[] weekDay){
        Calendar calendar = Calendar.getInstance();
        //验证星期几
        for (String week : weekDay){
            calendar.setTime(new Date());
            int we = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if(week.equals("7")){
                week = "0";
            }
            if(we != Integer.valueOf(week)){
                System.out.println(week + "不在开盘日期中");
            }else {
                System.out.println(week + "今日开盘");
                //验证开盘收盘时间
                //String[] openTime = new String[]{"9:00-12:00","13:30-16:30"};
                for (String times : openTime){
                    Calendar nowCalendar = Calendar.getInstance();
                    String[] time = times.split("-");
                    //判断是否在两个时间段内
                    int hour = nowCalendar.get(Calendar.HOUR);
                    int minute = nowCalendar.get(Calendar.MINUTE);
                    Date now = nowCalendar.getTime();

                    //封装设置时间
                    String[] startTime = time[0].split(":");
                    String[] endTime = time[1].split(":");
                    //开盘时间
                    nowCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
                    nowCalendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));
                    Date startDate = nowCalendar.getTime();
                    //收盘时间
                    nowCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endTime[0]));
                    nowCalendar.set(Calendar.MINUTE, Integer.valueOf(endTime[1]));
                    Date endDate = nowCalendar.getTime();
                    //当前时间小于
                    if(now.before(startDate) || now.after(endDate)){
                        System.out.println("不在开盘时间内");
                    }else{
                        System.out.println("获取最新价格");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*public boolean isMarketOpen(String[] openTime, String[] weekDay){
        Calendar calendar = Calendar.getInstance();
        //验证星期几
        for (String week : weekDay){
            calendar.setTime(new Date());
            int we = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if(we != Integer.valueOf(week)){
                System.out.println(week + "不在开盘日期中");
                return false;
            }else {
                System.out.println(week + "今日开盘");
            }
        }

        //验证开盘收盘时间
        //String[] openTime = new String[]{"9:00-12:00","13:30-16:30"};
        for (String times : openTime){
            Calendar nowCalendar = Calendar.getInstance();
            String[] time = times.split("-");
            //判断是否在两个时间段内
            int hour = nowCalendar.get(Calendar.HOUR);
            int minute = nowCalendar.get(Calendar.MINUTE);
            Date now = nowCalendar.getTime();

            //封装设置时间
            String[] startTime = time[0].split(":");
            String[] endTime = time[1].split(":");
            //开盘时间
            nowCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
            nowCalendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));
            Date startDate = nowCalendar.getTime();
            //收盘时间
            nowCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endTime[0]));
            nowCalendar.set(Calendar.MINUTE, Integer.valueOf(endTime[1]));
            Date endDate = nowCalendar.getTime();
            //当前时间小于
            if(now.before(startDate) || now.after(endDate)){
                System.out.println("不在开盘时间内");
                return false;
            }else{
                System.out.println("获取最新价格");
            }
        }
        return true;
    }*/

    /**
     * 获取服务费比例及手续费比例
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getScales(){
        Map<String,Object> ret = new HashedMap();
        ScalesBean scalesBean = subDao.getScales();
        ret.put("scalesBean",scalesBean);
        return ret;
    }


    /**
     * 认购
     * @param subMakeBean
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity subMake(SubMakeBean subMakeBean){
        subMakeBean.setUserId(AuthContext.getUserId());
        User user = userDao.findUserById(subMakeBean.getUserId());
        //判断用户是否被停用
        if(user.getStatus()!=0) {
            return new ResponseErrorEntity(StatusCode.USER_CLOSE,HttpStatus.BAD_REQUEST);
        }
        //判断该用户能否认购
        if(!user.getSubFlag().equals(Constants.Sub.SUB_OK)){
            return new ResponseErrorEntity(StatusCode.USER_SUB_NO_CHANCE, HttpStatus.BAD_REQUEST);
        }
        //判断商品可认购数量
        GoodsBean goodsBean = subDao.getSubGoodsById(subMakeBean.getGoodsId());
        int subAllNum = new BigDecimal(goodsBean.getGoodsTotalNum()).multiply(goodsBean.getSubScale()).intValue();
        int subLeftNum = subAllNum - (goodsBean.getGoodsTotalNum() - goodsBean.getGoodsLeftNum());
        if(subMakeBean.getHoldNum() > subLeftNum){
            logger.info("认购数量="+subMakeBean.getHoldNum()+",商品剩余认购数量="+subLeftNum);
            return new ResponseErrorEntity(StatusCode.USER_SUBGOODS_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }
        //验证购买金额=单价*数量
        BigDecimal money = subMakeBean.getBuyPoint().multiply(new BigDecimal(subMakeBean.getHoldNum()));
        if(subMakeBean.getMoney().compareTo(money) != 0){
            logger.info("购买金额="+subMakeBean.getMoney()+",验证金额="+money);
            return new ResponseErrorEntity(StatusCode.USER_SUBGOODS_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }
        //获取服务费比例、手续费比例、骑兵步兵返佣比例
        ScalesBean scalesBean = subDao.getScales();
        //验证服务费
        BigDecimal serviceFee = subMakeBean.getMoney().multiply(scalesBean.getSubServiceScale()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        if(subMakeBean.getServiceFee().compareTo(serviceFee)!=0){
            logger.info("服务费="+subMakeBean.getServiceFee()+",验证服务费="+serviceFee);
            return new ResponseErrorEntity(StatusCode.USER_SUBGOODS_SERVICEFEE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //验证用户余额
        if(user.getMoney().compareTo(subMakeBean.getMoney().add(subMakeBean.getServiceFee())) == -1){
            logger.info("用户余额="+user.getMoney()+",认购总费用="+subMakeBean.getMoney().add(subMakeBean.getServiceFee()));
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH, HttpStatus.BAD_REQUEST);
        }
        //获取商品规格
        SubGoodsSpec subGoodsSpec = subDao.getSubGoodsSpec(subMakeBean.getGoodsId());
        String[] subTotalNum = subGoodsSpec.getSubTotalNum().split(",");
        String[] subMakeNum = subGoodsSpec.getSubMakeNum().split(",");
        int originalHoldNum = subMakeBean.getHoldNum();
        BigDecimal originalMoney = subMakeBean.getMoney();
        BigDecimal takeMoney = new BigDecimal(0);
        for(int i=0; i<subTotalNum.length;i++){
            int num = Integer.valueOf(subTotalNum[i]).intValue();
            if(num!=subMakeBean.getHoldNum()){
                continue;
            }else{
                int leftHoldNum = Integer.valueOf(subMakeNum[i]).intValue();
                subMakeBean.setHoldNum(leftHoldNum);
                subMakeBean.setMoney(subMakeBean.getBuyPoint().multiply(new BigDecimal(leftHoldNum)));
                takeMoney = new BigDecimal(num-leftHoldNum).multiply(subMakeBean.getBuyPoint()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                break;
            }
        }

        subMakeBean.setId(Utils.uuid());
        subMakeBean.setSerialNo(serialNo("RG"));
        subMakeBean.setFeeBuy(new BigDecimal(0));
        subMakeBean.setFeeSell(new BigDecimal(0));
        subMakeBean.setBuyTime(new Date());
        subMakeBean.setStatus(Constants.SubMake.HOLD);
        subMakeBean.setCenterId(user.getCenterId());
        subMakeBean.setUnitsId(user.getUnitsId());
        subMakeBean.setAgentId(user.getAgentId());
        subMakeBean.setHoldFlag(Constants.holdFlag.RG);
        subMakeBean.setParent1Id(user.getParent1Id());
        subMakeBean.setParent2Id(user.getParent2Id());
        subMakeBean.setParent3Id(user.getParent3Id());
        //认购
        if(subDao.subMake(subMakeBean) != 1){
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //商品可认购数量变更
        Map<String,Object> m = new HashedMap();
        m.put("id",subMakeBean.getGoodsId());
        m.put("num",originalHoldNum);
        if(subDao.updGoodsSubLeftNum(m) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.CHANGE_SUB_LEFTNUM_ERROR, HttpStatus.BAD_REQUEST);
        }
        //可认购数量为0时，改为购销状态
        if(originalHoldNum == subLeftNum){
            m.put("status","2");
            if(subDao.updGoodsStatusById(m)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.CHANGE_SUB_GOODS_STATUS_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        //添加资金变更记录，用户余额变更
        BigDecimal userSubChangeMoney = (subMakeBean.getServiceFee().add(originalMoney)).multiply(new BigDecimal(-1));
        if(!moneyService.changeMoney(subMakeBean.getUserId(),userSubChangeMoney, user.getMoney(),
                Constants.Money.MONEY_CHANGE_TYPE_SUB_MAKE,
                Utils.formatStr("认购资金变更{0}，认购流水号：{1}", userSubChangeMoney.toString(), subMakeBean.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.USER_SUB_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }

        //用户只能认购一次，修改认购标志
        m.clear();
        m.put("id",subMakeBean.getUserId());
        m.put("subFlag","2");
        if(subDao.updUserSubFlag(m) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.CHANGE_USER_SUBFLAG_ERROR, HttpStatus.BAD_REQUEST);
        }

        //money返给商品初始所属人
        User goodsUser = userDao.findUserById(goodsBean.getUserId());
        if(!moneyService.changeMoney(goodsBean.getUserId(),subMakeBean.getMoney().add(takeMoney), goodsUser.getMoney(),
                Constants.Money.MONEY_CHANGE_SUB_RETURN_USER,
                Utils.formatStr("认购返现资金变更{0}，认购流水号：{1}", subMakeBean.getMoney().add(takeMoney).toString(), subMakeBean.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.SUB_RETURN_USER_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }

        //骑兵步兵返佣
  /*      List<Map<String,Object>> returnList = new ArrayList<>();
        Map<String,Object> returnMap = new HashedMap();
        if(!StringUtils.isEmpty(user.getParent2Id())){
            returnMap.put("userId",user.getParent2Id());
            returnMap.put("returnMoney",scalesBean.getPercentQiBing().multiply(originalMoney).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(!StringUtils.isEmpty(user.getParent3Id())){
            returnMap = new HashedMap();
            returnMap.put("userId",user.getParent3Id());
            returnMap.put("returnMoney",scalesBean.getPercentBuBing().multiply(originalMoney).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(returnList.size()>0){
            if(subDao.updUserReturnMoney(returnList) != returnList.size()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_MONEY_ERROR, HttpStatus.BAD_REQUEST);
            }
        }*/

        //服务费返佣
        if(subMakeBean.getServiceFee().compareTo(new BigDecimal(0))!=0){
            ResponseEntity responseEntity = this.calculateFees(Constants.SubFeesType.SERVICE,subMakeBean.getServiceFee(),subMakeBean.getUserId());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity;
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 返佣计算
     * @param type
     * @param money
     * @param userId
     * @return
     */
    private ResponseEntity calculateFees(String type, BigDecimal money, String userId){
        //查询用户所属vip、高级、普通会员及对应的手续费比例和服务费比例
        ReturnFeesBean returnFeesBean = subDao.getReturnFees(userId);
        if(type.equals(Constants.SubFeesType.SERVICE)){//服务费
            BigDecimal centerMoney = money.multiply(returnFeesBean.getCenterServicePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal unitsMoney =  money.multiply(returnFeesBean.getUnitsServicePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal agentMoney =  money.multiply(returnFeesBean.getAgentServicePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            Map<String,Object> moneyMap = new HashedMap();
            moneyMap.put("agentId",returnFeesBean.getAgentId());
            moneyMap.put("agentMoney",agentMoney);
            ReturnFeesChange returnFeesChange = new ReturnFeesChange();
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setTotalFee(money);
            returnFeesChange.setBeforMoney(returnFeesBean.getAgentServiceMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getAgentServiceMoney().add(agentMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getAgentServicePercent());
            returnFeesChange.setType(3);// 3服务费返佣  4手续费返佣
            returnFeesChange.setCenterId(returnFeesBean.getCenterId());
            returnFeesChange.setAgentId(returnFeesBean.getAgentId());
            returnFeesChange.setUnitsId(returnFeesBean.getUnitsId());
            returnFeesChange.setCreateTime(new Date());
            if(subDao.updAgentReturnMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_SERVICE_AGENT_ERROR, HttpStatus.BAD_REQUEST);
            }
            moneyMap.clear();
            moneyMap.put("unitsId",returnFeesBean.getUnitsId());
            moneyMap.put("unitsMoney",unitsMoney);
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setBeforMoney(returnFeesBean.getUnitsServiceMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getUnitsServiceMoney().add(unitsMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getUnitsServicePercent());
            returnFeesChange.setAgentId(null);
            if(subDao.updUnitsReturnMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_SERVICE_UNITS_ERROR, HttpStatus.BAD_REQUEST);
            }
            moneyMap.clear();
            moneyMap.put("centerId",returnFeesBean.getCenterId());
            moneyMap.put("centerMoney",centerMoney);
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setBeforMoney(returnFeesBean.getCenterServiceMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getCenterServiceMoney().add(centerMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getCenterServicePercent());
            returnFeesChange.setUnitsId(null);
            if(subDao.updCenterReturnMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_SERVICE_CENTER_ERROR, HttpStatus.BAD_REQUEST);
            }
            //平台服务费：
            BigDecimal platServiceMoney = money.subtract(centerMoney).subtract(unitsMoney).subtract(agentMoney);
            if(subDao.updPlatServiceMoney(platServiceMoney)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.PLAT_SERVICE_FEE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }else{//手续费
            BigDecimal centerMoney = money.multiply(returnFeesBean.getCenterFeePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal unitsMoney = money.multiply(returnFeesBean.getUnitsFeePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal agentMoney = money.multiply(returnFeesBean.getAgentFeePercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            Map<String,Object> moneyMap = new HashedMap();
            moneyMap.put("agentId",returnFeesBean.getAgentId());
            moneyMap.put("agentMoney",agentMoney);
            ReturnFeesChange returnFeesChange = new ReturnFeesChange();
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setTotalFee(money);
            returnFeesChange.setBeforMoney(returnFeesBean.getAgentFeeMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getAgentFeeMoney().add(agentMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getAgentFeePercent());
            returnFeesChange.setType(4);// 3服务费返佣  4手续费返佣
            returnFeesChange.setCenterId(returnFeesBean.getCenterId());
            returnFeesChange.setAgentId(returnFeesBean.getAgentId());
            returnFeesChange.setUnitsId(returnFeesBean.getUnitsId());
            returnFeesChange.setCreateTime(new Date());
            if(subDao.updAgentReturnFeeMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_FEE_AGENT_ERROR, HttpStatus.BAD_REQUEST);
            }
            moneyMap.clear();
            moneyMap.put("unitsId",returnFeesBean.getUnitsId());
            moneyMap.put("unitsMoney",unitsMoney);
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setBeforMoney(returnFeesBean.getUnitsFeeMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getUnitsFeeMoney().add(unitsMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getUnitsFeePercent());
            returnFeesChange.setAgentId(null);
            if(subDao.updUnitsReturnFeeMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_FEE_UNITS_ERROR, HttpStatus.BAD_REQUEST);
            }
            moneyMap.clear();
            moneyMap.put("centerId",returnFeesBean.getCenterId());
            moneyMap.put("centerMoney",centerMoney);
            returnFeesChange.setId(Utils.uuid());
            returnFeesChange.setBeforMoney(returnFeesBean.getCenterFeeMoney());
            returnFeesChange.setAfterMoney(returnFeesBean.getCenterFeeMoney().add(centerMoney));
            returnFeesChange.setReturnFeePercent(returnFeesBean.getCenterFeePercent());
            returnFeesChange.setUnitsId(null);
            if(subDao.updCenterReturnFeeMoney(moneyMap)!=1 || subDao.addReturnFeeChange(returnFeesChange)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_SERVICE_CENTER_ERROR, HttpStatus.BAD_REQUEST);
            }
            //获取骑兵步兵返佣比例
            ScalesBean scalesBean = subDao.getScales();
            BigDecimal qibingMoney = money.multiply(scalesBean.getPercentQiBing()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
            BigDecimal bubingMoney = money.multiply(scalesBean.getPercentBuBing()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_DOWN);

            //平台手续费：
            BigDecimal platFeeMoney = money.subtract(centerMoney).subtract(unitsMoney).subtract(agentMoney).subtract(qibingMoney).subtract(bubingMoney);
            if(subDao.updPlatFeeMoney(platFeeMoney)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.PLAT_SERVICE_FEE_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 生成交易流水号
     * @return
     */
    public String serialNo(String type){
        String prefix = type;
        return type + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }

    /**
     * 获取挂单商品
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getHangGoods(String goodsId){
        Map<String,Object> ret = new HashedMap();
        ret.put("list",subDao.getHangGoods(goodsId));
        return ret;
    }

    /**
     * 购买挂单商品_旧
     * @param buyHangBean
     * @return
     */
   /* @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity buyHangGoods(BuyHangBean buyHangBean){
        buyHangBean.setUserId(AuthContext.getUserId());
        User user = userDao.findUserById(buyHangBean.getUserId());

        //判断用户是否被停用
        if(user.getStatus()!=0) {
            return new ResponseErrorEntity(StatusCode.USER_CLOSE,HttpStatus.BAD_REQUEST);
        }
        //验证金额
        BigDecimal buyMoney = buyHangBean.getCurPrice().multiply(new BigDecimal(buyHangBean.getHoldNum()));
        if(buyMoney.compareTo(buyHangBean.getBuyMoney()) != 0){
            return new ResponseErrorEntity(StatusCode.USER_BUY_MONEY_ERROR,HttpStatus.BAD_REQUEST);
        }
        //获取服务费比例及手续费比例
        ScalesBean scalesBean = subDao.getScales();
        //验证手续费
        BigDecimal fee = buyHangBean.getBuyMoney().multiply(scalesBean.getSubFeeScale()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        if(buyHangBean.getFeeBuy().compareTo(fee) != 0){
            return new ResponseErrorEntity(StatusCode.USER_BUY_FEE_ERROR,HttpStatus.BAD_REQUEST);
        }
        //验证用户余额
        if(user.getMoney().compareTo(buyMoney.add(fee)) == -1){
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH,HttpStatus.BAD_REQUEST);
        }
        SubMakeBean subMakeBean = new SubMakeBean();
        subMakeBean.setId(Utils.uuid());
        subMakeBean.setSerialNo(serialNo("JY"));
        subMakeBean.setUserId(buyHangBean.getUserId());
        subMakeBean.setGoodsId(buyHangBean.getGoodsId());
        subMakeBean.setGoodsName(buyHangBean.getGoodsName());
        subMakeBean.setHoldNum(buyHangBean.getHoldNum());
        subMakeBean.setMoney(buyHangBean.getBuyMoney());
        subMakeBean.setServiceFee(new BigDecimal(0));
        subMakeBean.setFeeBuy(fee);
        subMakeBean.setFeeSell(new BigDecimal(0));
        subMakeBean.setBuyPoint(buyHangBean.getCurPrice());
        subMakeBean.setBuyTime(new Date());
        subMakeBean.setStatus(Constants.SubMake.HOLD);
        subMakeBean.setCenterId(user.getCenterId());
        subMakeBean.setUnitsId(user.getUnitsId());
        subMakeBean.setAgentId(user.getAgentId());
        subMakeBean.setHoldFlag(Constants.holdFlag.MR);
        //购买
        if(subDao.subMake(subMakeBean) != 1){
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //添加资金变更记录，用户余额变更
        BigDecimal userSubChangeMoney = (subMakeBean.getFeeBuy().add(subMakeBean.getMoney())).multiply(new BigDecimal(-1));
        if(!moneyService.changeMoney(subMakeBean.getUserId(),userSubChangeMoney, user.getMoney(),
                Constants.Money.MONEY_CHANGE_TYPE_SUB_MAKE,
                Utils.formatStr("购买资金变更{0}，认购流水号：{1}", userSubChangeMoney.toString(), subMakeBean.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.USER_CHANGE_BUY_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }
        //骑兵步兵返佣
        List<Map<String,Object>> returnList = new ArrayList<>();
        Map<String,Object> returnMap = new HashedMap();
        if(!StringUtils.isEmpty(user.getParent2Id())){
            returnMap.put("userId",user.getParent2Id());
            returnMap.put("returnMoney",scalesBean.getPercentQiBing().multiply(subMakeBean.getMoney()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(!StringUtils.isEmpty(user.getParent3Id())){
            returnMap = new HashedMap();
            returnMap.put("userId",user.getParent3Id());
            returnMap.put("returnMoney",scalesBean.getPercentBuBing().multiply(subMakeBean.getMoney()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(returnList.size()>0){
            if(subDao.updUserReturnMoney(returnList) != returnList.size()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_MONEY_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        //买入服务费返佣
        if(fee.compareTo(new BigDecimal(0))!=0){
            ResponseEntity responseEntity = this.calculateFees(Constants.SubFeesType.FEE,fee,buyHangBean.getUserId());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity;
            }
        }


        //获取挂单用户记录
        User userHang = userDao.findUserByMobile(buyHangBean.getHangUserPhone());
        //获取原挂单记录：
        SubMakeBean hangTrade = subDao.getSubTradeById(buyHangBean.getHangOrderId());
        //原挂单记录计算盈利：
        BigDecimal difMoney = buyHangBean.getCurPrice().subtract(hangTrade.getBuyPoint()).multiply(new BigDecimal(hangTrade.getHoldNum())).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        //卖出手续费
        BigDecimal feeSell = buyHangBean.getCurPrice().multiply(new BigDecimal(hangTrade.getHoldNum())).multiply(scalesBean.getSubFeeScale()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        Map<String,Object> buyMap = new HashedMap();
        buyMap.put("id",hangTrade.getId());
        buyMap.put("sellPoint",buyHangBean.getCurPrice());
        buyMap.put("sellTime",new Date());
        buyMap.put("status",Constants.SubMake.SELL);
        buyMap.put("difMoney",difMoney);
        buyMap.put("feeSell",feeSell);
        //卖出
        if(subDao.subSell(buyMap) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //计算用户余额
        BigDecimal userSellChangeMoney = hangTrade.getMoney().add(difMoney).subtract(feeSell);
        if(!moneyService.changeMoney(hangTrade.getUserId(),userSellChangeMoney, userHang.getMoney(),
                Constants.Money.MONEY_CHANGE_TYPE_SUB_SELL,
                Utils.formatStr("卖出资金变更{0}，交易流水号：{1}", userSellChangeMoney.toString(), hangTrade.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.USER_CHANGE_SELL_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }
        //积分计算
        if(difMoney.compareTo(new BigDecimal(0))!=0){
            //添加加分明细
            IntegralBean integralBean = new IntegralBean();
            integralBean.setId(Utils.uuid());
            integralBean.setUserId(hangTrade.getUserId());
            integralBean.setIntegralBefore(userHang.getIntegralNeg());
            if(difMoney.compareTo(new BigDecimal(0)) == 1){
                integralBean.setType("1");//状态： 1-正积分 2-负积分
                integralBean.setIntegral(difMoney);
                integralBean.setIntegralBefore(userHang.getIntegralNeg());
                integralBean.setIntegralAfter(userHang.getIntegralNeg().add(difMoney));
            }else if(difMoney.compareTo(new BigDecimal(0)) == -1){
                integralBean.setType("2");
                integralBean.setIntegral(difMoney.multiply(new BigDecimal(-1)));
                integralBean.setIntegralBefore(userHang.getIntegralUnpos());
                integralBean.setIntegralAfter(userHang.getIntegralUnpos().add(difMoney.multiply(new BigDecimal(-1))));
            }
            integralBean.setCreateTime(new Date());
            integralBean.setRemark(hangTrade.getSerialNo());
            if(subDao.addIntegral(integralBean) != 1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.ADD_INTEGRAL_DETAIL_ERROR, HttpStatus.BAD_REQUEST);
            }
            //更改用户积分
            buyMap.clear();
            buyMap.put("id",hangTrade.getUserId());
            buyMap.put("integral",difMoney);
            if(subDao.updUserIntegral(buyMap)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.USER_CHANGE_INTEGRAL_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        //卖出服务费返佣
        if(feeSell.compareTo(new BigDecimal(0))!=0){
            ResponseEntity responseEntity = this.calculateFees(Constants.SubFeesType.FEE,feeSell,hangTrade.getUserId());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity;
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }*/

    /**
     * 获取用户的积分
     * @param
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getUserIntegral(){
        Map<String,Object> ret = new HashedMap();
        System.out.println(AuthContext.getCurAuth().getUserId()+",");
        ret.put("integral",subDao.getUserIntegral(AuthContext.getUserId()));
        return ret;
    }

    /**
     * 获取积分明细
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getUserIntegralDetail(){
        Map<String,Object> ret = new HashedMap();
        ret.put("integral",subDao.getUserIntegralDetail(AuthContext.getUserId()));
        return ret;
    }

    /**
     * 获取可认购商品剩余比例
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getLeftScale(String goodsId){
        Map<String,Object> ret = new HashedMap();
        GoodsBean goodsBean = subDao.getSubGoodsById(goodsId);
        if(!StringUtils.isEmpty(goodsBean)){
            int subNum = goodsBean.getGoodsTotalNum() - goodsBean.getGoodsLeftNum();
            int subTotalNum = new BigDecimal(goodsBean.getGoodsTotalNum()).multiply(goodsBean.getSubScale()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(0,BigDecimal.ROUND_HALF_DOWN).intValue();
            BigDecimal scale = new BigDecimal(1).subtract(new BigDecimal(subNum).divide(new BigDecimal(subTotalNum),2,BigDecimal.ROUND_HALF_DOWN));
            ret.put("leftPercent",scale.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_DOWN).toString().concat("%"));
        }
        return ret;
    }

    /**
     * 提货
     * @param takeGoodsBean
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity takeGoodsAway(TakeGoodsBean takeGoodsBean){
        if(StringUtils.isEmpty(takeGoodsBean.getGoodsNum())){
            return new ResponseErrorEntity(StatusCode.TAKE_NUM_NOT_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isEmpty(takeGoodsBean.getAddressId())){
            return new ResponseErrorEntity(StatusCode.TAKE_GOODS_SEND_ADDRESS_NOT_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isEmpty(takeGoodsBean.getSerialNo())){
            return new ResponseErrorEntity(StatusCode.TAKE_GOODS_SERIAL_NO_NOT_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        //获取持仓订单
        SubMakeBean holdTrade = subDao.getSubTradeBySerialNo(takeGoodsBean.getSerialNo());
        if(StringUtils.isEmpty(holdTrade)){
            return new ResponseErrorEntity(StatusCode.SERIAL_NO_NOT_EXISTS, HttpStatus.BAD_REQUEST);
        }
        //验证提货数量
        if(takeGoodsBean.getGoodsNum() > holdTrade.getHoldNum()){
            return new ResponseErrorEntity(StatusCode.TAKE_NUM_ERROR, HttpStatus.BAD_REQUEST);
        }
        takeGoodsBean.setId(Utils.uuid());
        takeGoodsBean.setUserId(holdTrade.getUserId());
        takeGoodsBean.setGoodsId(holdTrade.getGoodsId());
        takeGoodsBean.setGoodsName(holdTrade.getGoodsName());
        takeGoodsBean.setCreateTime(new Date());
        takeGoodsBean.setStatus(Constants.TakeGoodsType.TAKE);
        takeGoodsBean.setMoney(new BigDecimal(takeGoodsBean.getGoodsNum()).multiply(holdTrade.getBuyPoint()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        BigDecimal scale = new BigDecimal(takeGoodsBean.getGoodsNum()).divide(new BigDecimal(holdTrade.getHoldNum()),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        takeGoodsBean.setServiceFee(scale.multiply(holdTrade.getServiceFee()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        takeGoodsBean.setBuyFee(scale.multiply(holdTrade.getFeeBuy()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        //添加提货记录
        if(subDao.insertTakeGoods(takeGoodsBean)!=1){
            return new ResponseErrorEntity(StatusCode.TAKE_NUM_ERROR, HttpStatus.BAD_REQUEST);
        }

        //修改原订单
        holdTrade.setMoney(holdTrade.getMoney().subtract(new BigDecimal(takeGoodsBean.getGoodsNum()).multiply(holdTrade.getBuyPoint())).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        holdTrade.setHoldNum(holdTrade.getHoldNum() - takeGoodsBean.getGoodsNum());
        holdTrade.setServiceFee(holdTrade.getServiceFee().subtract(takeGoodsBean.getServiceFee()));
        holdTrade.setFeeBuy(holdTrade.getFeeBuy().subtract(takeGoodsBean.getBuyFee()));
        if(holdTrade.getHoldNum() == 0){
            holdTrade.setStatus("3"); //平仓
            holdTrade.setSellPoint(new BigDecimal(0));
            holdTrade.setSellTime(new Date());
            holdTrade.setDifMoney(new BigDecimal(0));
        }
        if(subDao.updateHoldTrade(holdTrade)!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.TAKE_NUM_ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 撤销挂单
     * @param serialNo
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseEntity cancelHangTrade(String serialNo){
        if(StringUtils.isEmpty(serialNo)){
            return new ResponseErrorEntity(StatusCode.TAKE_GOODS_SERIAL_NO_NOT_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        //获取挂单订单
        SubMakeBean hangTrade = subDao.getSubTradeBySerialNo2(serialNo,Constants.SubMake.HANG);
        //获取原订单
        if(StringUtils.isEmpty(hangTrade) || StringUtils.isEmpty(hangTrade.getOldTradeSerialNo())){
            return new ResponseErrorEntity(StatusCode.OLD_SERIAL_NO_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        SubMakeBean oldTrade = subDao.getSubTradeBySerialNo2(hangTrade.getOldTradeSerialNo(),Constants.SubMake.HOLD);
        if(!StringUtils.isEmpty(oldTrade)){

        }else {
            oldTrade = subDao.getSubTradeBySerialNo2(hangTrade.getOldTradeSerialNo(),Constants.SubMake.DELETE);
        }

        oldTrade.setStatus(Constants.SubMake.HOLD);
        oldTrade.setHoldNum(oldTrade.getHoldNum()+hangTrade.getHoldNum());
        oldTrade.setMoney(oldTrade.getMoney().add(hangTrade.getMoney()));
        oldTrade.setSellPoint(null);
        oldTrade.setSellTime(null);
        oldTrade.setDifMoney(null);

        //修改原订单
        if(subDao.cancelHangTrade(oldTrade)!=1){
            return new ResponseErrorEntity(StatusCode.CANCLE_HANG_TRADE_OLD_ERROR, HttpStatus.BAD_REQUEST);
        }
        //下架挂单订单
        hangTrade.setRemark("挂单撤销");
        hangTrade.setStatus(Constants.SubMake.DELETE);
        if(subDao.cancel(hangTrade)!=1){
            return new ResponseErrorEntity(StatusCode.CANCLE_HANG_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * 获取挂单商品信息
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getHangDetail(String goodsId){
        Map<String,Object> ret = new HashedMap();
        List<HangGoodsDetail> list = subDao.getHangList(goodsId);
        GoodStatistics goodStatistics = subDao.getStatistice(goodsId);
        goodStatistics.setGoodsId(goodsId);
        ret.put("list",list);
        goodStatistics.setTotalLeftNum(subDao.countTradeHoldNum(goodsId));
        ret.put("goodStatistics",goodStatistics);
        return ret;
    }

//       @Scheduled(cron = "0 */1 * * * ?")
//    public void test(){
        //每天凌晨撤销全部挂单
//        List<SubMakeBean> hangList = subDao.getHangTradeList();
//        System.out.println("=============>>>hangList="+hangList.size());
//        hangList.forEach(subMakeBean -> {
//            ResponseEntity responseEntity = cancelHangTrade(subMakeBean.getSerialNo());
//            if(!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
//                return;
//            }
//        });

//           String goodsId = "a4ebff2134a74b06ad2c95d10f045e15";
//           List<HangGoodsDetail> list = subDao.getHangList(goodsId);
//           GoodStatistics goodStatistics = subDao.getStatistice(goodsId);
//           goodStatistics.setGoodsId(goodsId);
//           System.out.println("=================="+goodStatistics.toString());
//    }

    /**
     * 获取全部商品类型
     * @return
     */
    public Map<String, Object> findGoodsType(){
        Map<String, Object> result = new HashedMap();
        result.put("goodsTypeList", goodsTypeDao.findGoodsType());
        return result;
    }


    /**
     * 买入——新接口
     * @param buyBean
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public synchronized ResponseEntity buyHangGoodsNew(BuyBean buyBean){
        if(buyBean.getBuyNum() == 0){
            return new ResponseErrorEntity(StatusCode.BUY_NUM_ERROR, HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isEmpty(buyBean.getGoodsId())){
            return new ResponseErrorEntity(StatusCode.GOODSID_NOT_NULL_ERROR, HttpStatus.BAD_REQUEST);
        }
        String[] prices = buyBean.getBuyPoints().split(",");
        if(prices.length == 0){
            return new ResponseErrorEntity(StatusCode.GOODS_BUY_PRICE_ERROR, HttpStatus.BAD_REQUEST);
        }
        User user = userDao.findUserById(AuthContext.getUserId());
        //判断用户是否被停用
        if(user.getStatus()!=0) {
            return new ResponseErrorEntity(StatusCode.USER_CLOSE,HttpStatus.BAD_REQUEST);
        }
        GoodsBean goodsBean = subDao.getSubGoodsById(buyBean.getGoodsId());
        Map<String,Object> param = new HashedMap();
        //获取服务费比例及手续费比例
        ScalesBean scalesBean = subDao.getScales();
        //保存购买数量，防止重新赋值错误 (新添)
        int  buynum = buyBean.getBuyNum();
        for (String str : prices) {
            if(StringUtils.isEmpty(str)){
                continue;
            }
            buyBean.setBuyNum(buynum);

            param.put("goodsId",buyBean.getGoodsId());
            param.put("buyPoint",new BigDecimal(str).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            param.put("status",Constants.SubMake.HANG);
            //修改两个sql
            List<SubMakeBean> list = subDao.getHangGoodsTradeList(param);

            int hangNum = subDao.getHangNum(param);
            if(buyBean.getBuyNum() >= hangNum){//购买数量>挂单总数量
                buyBean.setBuyNum(hangNum);
                ResponseEntity responseEntity = buyHangGoods(user,str,buyBean,scalesBean,goodsBean);
                if(!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    return responseEntity;
                }
                for(SubMakeBean subMakeBean : list) {
                    ResponseEntity reponse = sellHangGoods(subMakeBean,scalesBean);
                    if (!reponse.getStatusCode().equals(HttpStatus.OK)) {
                        return responseEntity;
                    }
                }
            }else{//购买数量<挂单总数量
                ResponseEntity responseEntity = buyHangGoods(user,str,buyBean,scalesBean,goodsBean);
                if(!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    return responseEntity;
                }
                int totalBuyNum = buyBean.getBuyNum();
                for(SubMakeBean subMakeBean : list) {
                    if(totalBuyNum >= subMakeBean.getHoldNum()){
                        ResponseEntity response = sellHangGoods(subMakeBean,scalesBean);
                        if (!response.getStatusCode().equals(HttpStatus.OK)) {
                            return responseEntity;
                        }
                        totalBuyNum = totalBuyNum - subMakeBean.getHoldNum();
                    }else{
                        if(totalBuyNum!=0){
                            ResponseEntity response = seperateHangTrade(subMakeBean,scalesBean,totalBuyNum);
                            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                                return responseEntity;
                            }
                        }
                    }
                }
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 买入
     * @param user
     * @param buyPoint
     * @param buyBean
     * @param scalesBean
     * @param goodsBean
     * @return
     */
    private ResponseEntity buyHangGoods(User user,String buyPoint,BuyBean buyBean,ScalesBean scalesBean,GoodsBean goodsBean){
        //购买金额
        BigDecimal buyMoney = new BigDecimal(buyPoint).multiply(new BigDecimal(buyBean.getBuyNum()));
        //购买手续费
        BigDecimal buyFee = buyMoney.multiply((scalesBean.getSubFeeScale())).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        //验证用户余额
        if(user.getMoney().compareTo(buyMoney.add(buyFee)) == -1){
            return new ResponseErrorEntity(StatusCode.MONEY_NOT_ENOUGH,HttpStatus.BAD_REQUEST);
        }
        SubMakeBean subMakeBean = new SubMakeBean();
        subMakeBean.setId(Utils.uuid());
        subMakeBean.setSerialNo(serialNo("JY"));
        subMakeBean.setUserId(user.getId());
        subMakeBean.setGoodsId(buyBean.getGoodsId());
        subMakeBean.setGoodsName(subDao.getGoodsName(buyBean.getGoodsId()));
        subMakeBean.setHoldNum(buyBean.getBuyNum());
        subMakeBean.setMoney(buyMoney);
        subMakeBean.setServiceFee(new BigDecimal(0));
        subMakeBean.setFeeBuy(buyFee);
        subMakeBean.setFeeSell(new BigDecimal(0));
        subMakeBean.setBuyPoint(new BigDecimal(buyPoint));
        subMakeBean.setBuyTime(new Date());
        subMakeBean.setStatus(Constants.SubMake.HOLD);
        subMakeBean.setCenterId(user.getCenterId());
        subMakeBean.setUnitsId(user.getUnitsId());
        subMakeBean.setAgentId(user.getAgentId());
        subMakeBean.setHoldFlag(Constants.holdFlag.MR);
        subMakeBean.setParent1Id(user.getParent1Id());
        subMakeBean.setParent2Id(user.getParent2Id());
        subMakeBean.setParent3Id(user.getParent3Id());
        //购买
        if(subDao.subMake(subMakeBean) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //添加资金变更记录，用户余额变更
        BigDecimal userSubChangeMoney = (subMakeBean.getFeeBuy().add(subMakeBean.getMoney())).multiply(new BigDecimal(-1));
        if(!moneyService.changeMoney(subMakeBean.getUserId(),userSubChangeMoney, user.getMoney(),
                Constants.Money.MONEY_CHANGE_TYPE_SUB_MAKE,
                Utils.formatStr("购买资金变更{0}，认购流水号：{1}", userSubChangeMoney.toString(), subMakeBean.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.USER_CHANGE_BUY_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }
        //骑兵步兵返佣
        List<Map<String,Object>> returnList = new ArrayList<>();
        Map<String,Object> returnMap = new HashedMap();
        if(!StringUtils.isEmpty(user.getParent2Id())){
            returnMap.put("userId",user.getParent2Id());
            returnMap.put("returnMoney",scalesBean.getPercentQiBing().multiply(subMakeBean.getMoney()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(!StringUtils.isEmpty(user.getParent3Id())){
            returnMap = new HashedMap();
            returnMap.put("userId",user.getParent3Id());
            returnMap.put("returnMoney",scalesBean.getPercentBuBing().multiply(subMakeBean.getMoney()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(returnList.size()>0){
            if(subDao.updUserReturnMoney(returnList) != 1){
            /*if(subDao.updUserReturnMoney(returnList) != returnList.size()){*/
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_MONEY_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        //买入服务费返佣
        if(buyFee.compareTo(new BigDecimal(0))!=0){
            ResponseEntity responseEntity = this.calculateFees(Constants.SubFeesType.FEE,buyFee,subMakeBean.getUserId());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity;
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 卖出
     * @param subMakeBean
     * @param scalesBean
     * @return
     */
    private ResponseEntity sellHangGoods(SubMakeBean subMakeBean,ScalesBean scalesBean){
        //获取原挂单用户
        User userHang = userDao.findUserById(subMakeBean.getUserId());
        //原挂单记录计算盈利
        BigDecimal difMoney = subMakeBean.getSellPoint().subtract(subMakeBean.getBuyPoint()).multiply(new BigDecimal(subMakeBean.getHoldNum())).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        //卖出手续费
        BigDecimal feeSell = subMakeBean.getSellPoint().multiply(new BigDecimal(subMakeBean.getHoldNum())).multiply(scalesBean.getSubFeeScale()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        Map<String,Object> buyMap = new HashedMap();
        buyMap.put("id",subMakeBean.getId());
        buyMap.put("sellTime",new Date());
        buyMap.put("status",Constants.SubMake.SELL);
        buyMap.put("difMoney",difMoney);
        buyMap.put("feeSell",feeSell);
        //卖出
        if(subDao.subSell(buyMap) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //计算用户余额(修改产品所属客户 直接挂单后金额只加difmoney的bug)
//        BigDecimal userSellChangeMoney = subMakeBean.getMoney().add(difMoney).subtract(feeSell);
        //减亏损金额的(错误)
//        BigDecimal userSellChangeMoney = (subMakeBean.getSellPoint().multiply(new BigDecimal(subMakeBean.getHoldNum()))).add(difMoney).subtract(feeSell);
        BigDecimal userSellChangeMoney = (subMakeBean.getSellPoint().multiply(new BigDecimal(subMakeBean.getHoldNum()))).subtract(feeSell);
        //盈利金额*70% (确定做在解开注释)
        //userSellChangeMoney = userSellChangeMoney.multiply(new BigDecimal(0.7));
        if(!moneyService.changeMoney(subMakeBean.getUserId(),userSellChangeMoney, userHang.getMoney(),
                Constants.Money.MONEY_CHANGE_TYPE_SUB_SELL,
                Utils.formatStr("卖出资金变更{0}，交易流水号：{1}", userSellChangeMoney.toString(), subMakeBean.getSerialNo()))){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.USER_CHANGE_SELL_MONEY_ERROR, HttpStatus.BAD_REQUEST);
        }

        //骑兵步兵返佣
        List<Map<String,Object>> returnList = new ArrayList<>();
        Map<String,Object> returnMap = new HashedMap();
        if(!StringUtils.isEmpty(userHang.getParent2Id())){
            returnMap.put("userId",userHang.getParent2Id());
            returnMap.put("returnMoney",scalesBean.getPercentQiBing().multiply(subMakeBean.getSellPoint().multiply(new BigDecimal(subMakeBean.getHoldNum()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(!StringUtils.isEmpty(userHang.getParent3Id())){
            returnMap = new HashedMap();
            returnMap.put("userId",userHang.getParent3Id());
            returnMap.put("returnMoney",scalesBean.getPercentBuBing().multiply(subMakeBean.getSellPoint().multiply(new BigDecimal(subMakeBean.getHoldNum()))).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
            returnList.add(returnMap);
        }
        if(returnList.size()>0){
            if(subDao.updUserReturnMoney(returnList) != 1){
            /*if(subDao.updUserReturnMoney(returnList) != returnList.size()){*/
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.RETURN_MONEY_ERROR, HttpStatus.BAD_REQUEST);
            }
        }

        //积分计算
        if(difMoney.compareTo(new BigDecimal(0))!=0){
            //添加加分明细
            IntegralBean integralBean = new IntegralBean();
            integralBean.setId(Utils.uuid());
            integralBean.setUserId(subMakeBean.getUserId());
            integralBean.setIntegralBefore(userHang.getIntegralNeg());
            if(difMoney.compareTo(new BigDecimal(0)) == 1){
                //正积分为盈利金额的30% (确定做在解开注释)
                difMoney = difMoney.multiply(new BigDecimal(0.3));
                integralBean.setType("1");//状态： 1-正积分 2-负积分
                integralBean.setIntegral(difMoney);
                integralBean.setIntegralBefore(userHang.getIntegralNeg());
                integralBean.setIntegralAfter(userHang.getIntegralNeg().add(difMoney));
            }else if(difMoney.compareTo(new BigDecimal(0)) == -1){
                integralBean.setType("2");
                integralBean.setIntegral(difMoney.multiply(new BigDecimal(-1)));
                integralBean.setIntegralBefore(userHang.getIntegralUnpos());
                integralBean.setIntegralAfter(userHang.getIntegralUnpos().add(difMoney.multiply(new BigDecimal(-1))));
            }
            integralBean.setCreateTime(new Date());
            integralBean.setRemark(subMakeBean.getSerialNo());
            if(subDao.addIntegral(integralBean) != 1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.ADD_INTEGRAL_DETAIL_ERROR, HttpStatus.BAD_REQUEST);
            }
            //更改用户积分
            buyMap.clear();
            buyMap.put("id",subMakeBean.getUserId());
            buyMap.put("integral",difMoney);
            if(subDao.updUserIntegral(buyMap)!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResponseErrorEntity(StatusCode.USER_CHANGE_INTEGRAL_ERROR, HttpStatus.BAD_REQUEST);
            }
        }
        //卖出服务费返佣
        if(feeSell.compareTo(new BigDecimal(0))!=0){
            ResponseEntity responseEntity = this.calculateFees(Constants.SubFeesType.FEE,feeSell,subMakeBean.getUserId());
            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity;
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 拆分挂单订单
     * @param subMakeBean
     * @param scalesBean
     * @param totalBuyNum
     * @return
     */
    private ResponseEntity seperateHangTrade(SubMakeBean subMakeBean,ScalesBean scalesBean,int totalBuyNum){
        //获取原挂单用户
        User userHang = userDao.findUserById(subMakeBean.getUserId());
        //分离出剩余的挂单
        SubMakeBean leftTrade = new SubMakeBean();
        leftTrade.setId(Utils.uuid());
        leftTrade.setSerialNo(serialNo("GD"));
        leftTrade.setUserId(userHang.getId());
        leftTrade.setGoodsId(subMakeBean.getGoodsId());
        leftTrade.setGoodsName(subMakeBean.getGoodsName());
        int leftNum = subMakeBean.getHoldNum() - totalBuyNum;
        leftTrade.setHoldNum(leftNum);
        BigDecimal scale = new BigDecimal(leftNum).divide(new BigDecimal(subMakeBean.getHoldNum()),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal leftMoney = subMakeBean.getMoney().multiply(scale).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal leftServiceFee = subMakeBean.getServiceFee().multiply(scale).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        BigDecimal leftFeeBuy = subMakeBean.getFeeBuy().multiply(scale).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        leftTrade.setMoney(leftMoney);
        leftTrade.setServiceFee(leftServiceFee);
        leftTrade.setFeeBuy(leftFeeBuy);
        leftTrade.setFeeSell(new BigDecimal(0));
        //leftTrade.setBuyPoint(subMakeBean.getBuyPoint());
        leftTrade.setBuyPoint(subMakeBean.getSellPoint());
        leftTrade.setBuyTime(subMakeBean.getBuyTime());
        leftTrade.setStatus(Constants.SubMake.HANG);
        leftTrade.setCenterId(subMakeBean.getCenterId());
        leftTrade.setUnitsId(subMakeBean.getUnitsId());
        leftTrade.setAgentId(subMakeBean.getAgentId());
        leftTrade.setHoldFlag(null);
        leftTrade.setSellPoint(subMakeBean.getSellPoint());
        leftTrade.setOldTradeSerialNo(subMakeBean.getOldTradeSerialNo());
        leftTrade.setHangTime(subMakeBean.getHangTime());
        leftTrade.setParent1Id(subMakeBean.getParent1Id());
        leftTrade.setParent2Id(subMakeBean.getParent2Id());
        leftTrade.setParent3Id(subMakeBean.getParent3Id());
        if(subDao.subMake(leftTrade) != 1){
//        if(subDao.subMake(subMakeBean) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.MAKE_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        //修改原挂单订单
        subMakeBean.setHoldNum(totalBuyNum);
        subMakeBean.setMoney(subMakeBean.getMoney().subtract(leftMoney));
        subMakeBean.setServiceFee(subMakeBean.getServiceFee().subtract(leftServiceFee));
        subMakeBean.setFeeBuy(subMakeBean.getFeeBuy().subtract(leftFeeBuy));
        if(subDao.seperateHangTrade(subMakeBean)!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseErrorEntity(StatusCode.UPDATE_HANG_TRADE_ERROR, HttpStatus.BAD_REQUEST);
        }

        //修改后挂单订单->平仓
        ResponseEntity responseEntity = sellHangGoods(subMakeBean,scalesBean);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity;
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 收藏
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String,Object> goodsStore(String goodsId){
        Map<String,Object> ret = new HashedMap();
        StoreBean storeBean = new StoreBean();
        storeBean.setId(Utils.uuid());
        storeBean.setUserId(AuthContext.getUserId());
        storeBean.setGoodsId(goodsId);
        storeBean.setCreateTime(new Date());
        storeBean.setStatus(Constants.GOODS_STORE.STORE);
        if(subDao.goodsStore(storeBean)!=1){
            ret.put("error","收藏失败");
        }
        return ret;
    }

    /**
     * 取消收藏
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String,Object> goodsStoreCancel(String goodsId){
        Map<String,Object> ret = new HashedMap();
        ret.put("userId",AuthContext.getUserId());
        ret.put("goodsId",goodsId);
//        ret.put("status",Constants.GOODS_STORE.CANCEL);
//        ret.put("updateTime",new Date());
//        if(subDao.goodsStoreCancel(ret)!=1){
//            ret.clear();
//            ret.put("error","取消收藏失败");
//            return ret;
//        }
        subDao.goodsStoreCancel(ret);
        ret.clear();
        return ret;
    }

    /**
     * 我的收藏
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getMyGoodsStore(){
        Map<String,Object> ret = new HashedMap();
        ret.put("list",subDao.getMyGoodsStore(AuthContext.getUserId()));
        return ret;
    }

    /**
     * 判断商品是否已收藏
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> judgeGoodsStore(String goodsId){
        Map<String,Object> ret = new HashedMap();
        int num = subDao.judgeGoodsStore(AuthContext.getUserId(),goodsId);
        ret.put("exist",num==0?false:true);
        return ret;
    }

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getGoodsDetailImg(String goodsId){
        Map<String,Object> ret = new HashedMap();
        ret.put("imgDetailPath",subDao.getGoodsDetailImg(goodsId));
        return ret;
    }

    /**
     * 获取挂单价格浮动比例
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> getUpAndDownPercent(){
        Map<String,Object> ret = new HashedMap();
        BigDecimal upAndDownPercent = tradeDao.getUpAndDownPercent();
        ret.put("upAndDownPercent", upAndDownPercent.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_DOWN).setScale(2,BigDecimal.ROUND_HALF_DOWN));
        return ret;
    }

    /**
     * 获取开市时间
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> openWeekAndTime(){
        Map<String,Object> ret = new HashedMap();
        ret.put("openWeekAndTime", subDao.openWeekAndTime());
        return ret;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String,Object> judgeOpenTime(){
        Map<String,Object> ret = new HashedMap();
        //验证是否在开盘时间内
        OpenTime openTime = subDao.openWeekAndTime();
        String openTimeStr = openTime.getOpenTime();
        String weekDaySet = openTime.getWeekDaySet();
        //验证是否在规定开盘日期
        String[] weekDays = weekDaySet.split(",");
        String[] openTimes = openTimeStr.split(",");
        ret.put("isOpenTime", isMarketOpen(openTimes, weekDays));
        return ret;
    }


    /**
     * 转余额
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String,Object> converUserMoney(){
        Map<String,Object> ret = new HashedMap();
        subDao.converUserMoney(AuthContext.getUserId());
        return ret;
    }


}
