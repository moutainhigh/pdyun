package com.rmkj.microcap.modules.trade.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.GridPagerExt;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.Ml3RoleBean;
import com.rmkj.microcap.common.modules.sys.service.Ml3AgentService;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import com.rmkj.microcap.modules.Ml3OperateCenter.entity.Ml3OperateCenterBean;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
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

    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model){
//        List<Units> list = tradeStatisticsService.findUnits();
//        model.addAttribute("units", list);
        tradeStatisticsService.findMl3AgentList(model);
        filterRole(model);
        return "/trade/statistics";
    }

    @RequestMapping(value = "/operateCenterPage", method = RequestMethod.GET)
    public String operateCenterPage(Model model){
        //List<Units> list = tradeStatisticsService.findUnits();
        model.addAttribute("units", ml3MemberUnitsService.findMemberUnitsByAgentId());
        //model.addAttribute("operateCenter", ml3OperateCenterService.queryList(new Ml3OperateCenterBean()));
        filterRole(model);
        return "/trade/operateCenterStatistics";
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

        initTradeStatisticsParams(tradeStatisticsParams);
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

        initTradeStatisticsParams(tradeStatisticsParams);
        return tradeStatisticsService.findTradesResult(tradeStatisticsParams);
    }

    private void initTradeStatisticsParams(TradeStatisticsParams tradeStatisticsParams){
        com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());

        List<Ml3RoleBean> roleList = UserUtils.getUser().getRoleList();
        roleList.forEach(ml3RoleBean -> {
            if("2".equals(ml3RoleBean.getId()) || "5".equals(ml3RoleBean.getId())){
                tradeStatisticsParams.setUnitsId(ml3AgentBean.getUnitsId());
            }else if("3".equals(ml3RoleBean.getId()) || "4".equals(ml3RoleBean.getId())){
                //com.rmkj.microcap.common.modules.sys.bean.Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
                tradeStatisticsParams.setAgentId(ml3AgentBean.getId());
            }else if("1".equals(ml3RoleBean.getId())){
                tradeStatisticsParams.setCenterId(ml3AgentBean.getCenterId());
            }
        });
    }


}
