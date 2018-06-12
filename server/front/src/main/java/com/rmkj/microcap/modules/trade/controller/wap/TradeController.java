package com.rmkj.microcap.modules.trade.controller.wap;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.index.service.BroadcastService;
import com.rmkj.microcap.modules.index.service.HomeService;
import com.rmkj.microcap.modules.index.service.RootService;
import com.rmkj.microcap.modules.market.service.MarketService;
import com.rmkj.microcap.modules.money.service.CashCouponService;
import com.rmkj.microcap.modules.trade.bean.TradeHistoryBean;
import com.rmkj.microcap.modules.trade.entity.Contract;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.trade.service.TradeService;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by renwp on 2016/10/20.
 */
@Controller
@RequestMapping("${wap}/trade")
public class TradeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private RootService rootService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private CashCouponService cashCouponService;

    @RequestMapping(value = "")
    @LoginAnnot
    public String trade(Model model){
        return rootService.toTrade(model);
    }

    @RequestMapping(value = "/hold")
    @LoginAnnot
    public String hold(String flag, Model model){
        homeService.index(model);
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        List<Contract> contracts = tradeService.contractList();
        model.addAttribute("contracts", contracts);

        List<Trade> list = tradeService.tradeList();
        List<Trade> toDayTradeList = tradeService.toDayTradeList();

        Map<String,Integer> map = tradeService.getPrecisions2();
        model.addAttribute("map",map);
        model.addAttribute("list", list);
        model.addAttribute("_list", JSON.toJSONString(list));
        model.addAttribute("toDayTradeList",toDayTradeList);
        String [] _codes = marketService.codes();
        model.addAttribute("_codes", JSON.toJSONString(_codes));

        model.addAttribute("flag",flag);

        rootService.countNewMessage(model);
        return "/wap/hold";
    }

    @RequestMapping(value = "/tradeHistory")
    @LoginAnnot
    public String tradeHistory(TradeHistoryBean tradeHistoryBean, Model model){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String currentDate = formatter.format(cal.getTime());
        List<Trade> list = null;
        if(tradeHistoryBean.getSelDate() == null){
            tradeHistoryBean.setSelDate(currentDate);
            list = tradeService.tradeHistory(tradeHistoryBean);
        }else {
            list = tradeService.tradeHistory(tradeHistoryBean);
        }
        Map<String,Integer> map = tradeService.getPrecisions2();
        model.addAttribute("map",map);
        model.addAttribute("list", list);
        model.addAttribute("date",tradeHistoryBean.getSelDate());
        return "/wap/tradeHistory";
    }

    @RequestMapping(value = "/tradeRule")
    @LoginAnnot
    public String tradeRule(Model model){
        return "/wap/user/trade_rule/tradeRule";
    }

}
