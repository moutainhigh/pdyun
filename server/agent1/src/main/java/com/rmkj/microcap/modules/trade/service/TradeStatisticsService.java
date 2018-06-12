package com.rmkj.microcap.modules.trade.service;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.trade.dao.TradeStatisticsDao;
import com.rmkj.microcap.modules.trade.entity.*;
import com.rmkj.microcap.modules.user.entity.RoleBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    private Ml3AgentService ml3AgentService;
    /**
     *
     * @param tradeStatisticsParams
     * @return
     */
    public GridPager findTrades(TradeStatisticsParams tradeStatisticsParams) throws Exception {
        filter(tradeStatisticsParams);
        GridPager gridPager = new GridPager();
        gridPager.setRows(tradeStatisticsDao.findTrades(tradeStatisticsParams));
        gridPager.setTotal((int)tradeStatisticsDao.findTradesTotal(tradeStatisticsParams));
        return gridPager;
    }

    public TradeStatisticsResult findTradesResult(TradeStatisticsParams tradeStatisticsParams) throws Exception {
        filter(tradeStatisticsParams);
        TradeStatisticsResult tradeStatisticsResult = new TradeStatisticsResult();
        tradeStatisticsResult.setTrade(tradeStatisticsDao.tradeStatistics(tradeStatisticsParams));

        FeesBean feesBean = tradeStatisticsDao.getFeesCount(tradeStatisticsParams);
        tradeStatisticsResult.getTrade().setFeeSellSum(feesBean.getSellFeeSum());
        tradeStatisticsResult.getTrade().setFeeBuySum(feesBean.getBuyFeeSum());
        tradeStatisticsResult.getTrade().setServiceFeeSum(feesBean.getServiceFeeSum());

        tradeStatisticsResult.setUser(tradeStatisticsDao.userStatistics(tradeStatisticsParams));
        tradeStatisticsResult.setMoneyRecord(tradeStatisticsDao.moneyRecordStatistics(tradeStatisticsParams));
        tradeStatisticsResult.setHoldTrade(tradeStatisticsDao.tradeHoldStatistics(tradeStatisticsParams));

        BigDecimal rechargeSum = tradeStatisticsDao.queryRechargeSum(tradeStatisticsParams);
        if(null != rechargeSum){
            tradeStatisticsResult.getUser().setInMoney(rechargeSum.toString());
        }else {
            TradeStatisticsResult2 user = tradeStatisticsResult.getUser();
            if(null != user){
                tradeStatisticsResult.getUser().setInMoney("0");
            }
        }
        return tradeStatisticsResult;
    }

    public List<Units> findUnits() {
        return tradeStatisticsDao.findUnits();
    }

    private void filter(TradeStatisticsParams tradeStatisticsParams) throws Exception{
        Ml3AgentBean user = UserUtils.getUser();
        Ml3AgentBean user1 = ml3AgentService.get(user.getId());
        if(StringUtils.isBlank(user1.getUnitsId()) && StringUtils.isBlank(user1.getCenterId())){
            throw new Exception("登录用户不合法");
        }
        List<RoleBean> userRoleById = tradeStatisticsDao.findUserRoleById(user.getId());
        userRoleById.forEach(roleBean -> {
            if("2".equals(roleBean.getId()) || "5".equals(roleBean.getId())){
                tradeStatisticsParams.setUnitsId(user1.getUnitsId());
            }else if("3".equals(roleBean.getId()) || "4".equals(roleBean.getId())){
                tradeStatisticsParams.setAgentId(user.getId());
            }else if("1".equals(roleBean.getId())){
                tradeStatisticsParams.setCenterId(user1.getCenterId());
            }
        });
    }

    public void findMl3AgentList(Model model){
        com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        List<com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean> ml3AgentList = tradeStatisticsDao.findMl3AgentList(ml3AgentBean.getUnitsId());
        model.addAttribute("agentList", ml3AgentList);
    }
}
