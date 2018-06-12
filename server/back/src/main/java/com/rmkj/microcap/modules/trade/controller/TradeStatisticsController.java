package com.rmkj.microcap.modules.trade.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.GridPagerExt;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.trade.dao.TradeStatisticsDao;
import com.rmkj.microcap.modules.trade.entity.TradeStatistics;
import com.rmkj.microcap.modules.trade.entity.TradeStatisticsParams;
import com.rmkj.microcap.modules.trade.entity.TradeStatisticsResult;
import com.rmkj.microcap.modules.trade.entity.Units;
import com.rmkj.microcap.modules.trade.service.TradeStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by renwp on 2016/12/30.
 */
@Controller
@RequestMapping("/tradestatistics")
public class TradeStatisticsController extends BaseController {

    @Autowired
    private TradeStatisticsService tradeStatisticsService;

    @Autowired
    private TradeStatisticsDao tradeStatisticsDao;

    @Autowired
    private Ml3AgentService ml3AgentService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model){
        List<Units> list = tradeStatisticsService.findUnits();
        tradeStatisticsService.findOperateCenter(model);
        model.addAttribute("units", list);
        model.addAttribute("agent", ml3AgentService.findAgentList());
        return "/trade/statistics";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public GridPager page(TradeStatisticsParams tradeStatisticsParams) throws Exception {
        tradeStatisticsParams.setStart(evalStart(tradeStatisticsParams.getPage(), tradeStatisticsParams.getRows()));
        if(StringUtils.isNotBlank(tradeStatisticsParams.getUserMobile())){
            tradeStatisticsParams.setUserId(tradeStatisticsDao.findUserIdByMobile(tradeStatisticsParams.getUserMobile()));
        }
        if(StringUtils.isNotBlank(tradeStatisticsParams.getMobile())){
            tradeStatisticsParams.setJunId(tradeStatisticsDao.findUserIdByMobile(tradeStatisticsParams.getMobile()));
        }
        if(StringUtils.isNotBlank(tradeStatisticsParams.getAgentMobile())){
            tradeStatisticsParams.setAgentId(tradeStatisticsDao.findAgentIdByMobile(tradeStatisticsParams.getAgentMobile()));
        }

        return tradeStatisticsService.findTrades(tradeStatisticsParams);
    }


    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public TradeStatisticsResult result(TradeStatisticsParams tradeStatisticsParams) throws Exception {
        if(StringUtils.isNotBlank(tradeStatisticsParams.getUserMobile())){
            tradeStatisticsParams.setUserId(tradeStatisticsDao.findUserIdByMobile(tradeStatisticsParams.getUserMobile()));
        }
        if(StringUtils.isNotBlank(tradeStatisticsParams.getMobile())){
            tradeStatisticsParams.setJunId(tradeStatisticsDao.findUserIdByMobile(tradeStatisticsParams.getMobile()));
        }
        if(StringUtils.isNotBlank(tradeStatisticsParams.getAgentMobile())){
            tradeStatisticsParams.setAgentId(tradeStatisticsDao.findAgentIdByMobile(tradeStatisticsParams.getAgentMobile()));
        }

        return tradeStatisticsService.findTradesResult(tradeStatisticsParams);
    }

}
