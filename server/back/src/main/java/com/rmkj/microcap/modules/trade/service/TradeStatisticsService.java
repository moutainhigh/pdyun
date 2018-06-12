package com.rmkj.microcap.modules.trade.service;

import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3OperateCenter.dao.IMl3OperateCenterDao;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.trade.dao.TradeStatisticsDao;
import com.rmkj.microcap.modules.trade.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by renwp on 2016/12/30.
 */
@Service
public class TradeStatisticsService {

    @Autowired
    private TradeStatisticsDao tradeStatisticsDao;

    @Autowired
    private IMl3OperateCenterDao ml3OperateCenterDao;

    /**
     *
     * @param tradeStatisticsParams
     * @return
     */
    public GridPager findTrades(TradeStatisticsParams tradeStatisticsParams) {
        GridPager gridPager = new GridPager();
        gridPager.setRows(tradeStatisticsDao.findTrades(tradeStatisticsParams));
        gridPager.setTotal((int)tradeStatisticsDao.findTradesTotal(tradeStatisticsParams));
        return gridPager;
    }

    public TradeStatisticsResult findTradesResult(TradeStatisticsParams tradeStatisticsParams) {
        TradeStatisticsResult tradeStatisticsResult = new TradeStatisticsResult();
        tradeStatisticsResult.setTrade(tradeStatisticsDao.tradeStatistics(tradeStatisticsParams));
        FeesBean feesBean = tradeStatisticsDao.getFeesCount(tradeStatisticsParams);

        tradeStatisticsResult.getTrade().setServiceFeeSum(feesBean.getServiceFeeSum());
        tradeStatisticsResult.getTrade().setFeeBuySum(feesBean.getBuyFeeSum());
        tradeStatisticsResult.getTrade().setFeeSellSum(feesBean.getSellFeeSum());

        tradeStatisticsResult.setUser(tradeStatisticsDao.userStatistics(tradeStatisticsParams));
        tradeStatisticsResult.setMoneyRecord(tradeStatisticsDao.moneyRecordStatistics(tradeStatisticsParams));
        tradeStatisticsResult.setHoldTrade(tradeStatisticsDao.tradeHoldStatistics(tradeStatisticsParams));


        BigDecimal rechargeSum = tradeStatisticsDao.queryRechargeSum(tradeStatisticsParams);
        if(null != rechargeSum){
            tradeStatisticsResult.getUser().setInMoney(rechargeSum.toString());
        }else {
            tradeStatisticsResult.getUser().setInMoney("0");
        }
        //计算所得交易管理费
        /*BigDecimal tradeManageMoney = tradeStatisticsResult.getTrade().getTradeManageMoney();
        if(null != tradeManageMoney){
            TradeStatisticsResult5 tradeReturnFee = tradeStatisticsDao.findTradeReturnChangeByCenterId(tradeStatisticsParams);
            tradeReturnFee.setWinTradeFee(tradeManageMoney.subtract(tradeReturnFee.getTotalFee()));*/
        /*TradeStatisticsResult5 exChangeWinFee = tradeStatisticsDao.findExChangeWinFee(tradeStatisticsParams);
        if(StringUtils.isNotBlank(tradeStatisticsParams.getCenterId()) && StringUtils.isNotBlank(tradeStatisticsParams.getUnitsId())){//同时选择市场管理部和会员单位

        }else{ //只选择会员单位

        }*/

        tradeStatisticsResult.setWinTradeFee(tradeStatisticsDao.findExChangeWinFee(tradeStatisticsParams));
            /*tradeStatisticsResult.setWinTradeFee(tradeReturnFee);
        }*/

        return tradeStatisticsResult;
    }

    public List<Units> findUnits() {
        return tradeStatisticsDao.findUnits();
    }

    public void findOperateCenter(Model model){
        model.addAttribute("operateCenter", ml3OperateCenterDao.queryList(new Ml3OperateCenterBean()));
    }

}
