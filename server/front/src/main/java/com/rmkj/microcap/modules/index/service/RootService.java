package com.rmkj.microcap.modules.index.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.modules.trademarket.MarketPointBean;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.market.bean.KDataBean;
import com.rmkj.microcap.modules.market.service.MarketService;
import com.rmkj.microcap.modules.money.service.CashCouponService;
import com.rmkj.microcap.modules.trade.entity.Contract;
import com.rmkj.microcap.modules.trade.service.TradeService;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by renwp on 2016/11/10.
 */
@Service
public class RootService {

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private CashCouponService cashCouponService;
    /**
     * 跳转首页
     * @param model
     */
    public String toHome(Model model){
        homeService.index(model);
        List<Broadcast> broadcasts = broadcastService.findList();
        model.addAttribute("broadcastsJson", JSON.toJSONString(broadcasts));
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        List<Contract> contracts = tradeService.contractList();
        model.addAttribute("contracts", contracts);
        //String [] _codes = marketService.codes();
        //model.addAttribute("_codes", JSON.toJSONString(_codes));
        model.addAttribute("_user_can_use_coupons", cashCouponService.findUserCashCouponCanUse());
        boolean isMarketOpen = tradeService.isMarketOpen();
        model.addAttribute("isMarketOpen", isMarketOpen);
        model.addAttribute("_isMarketOpen", JSON.toJSONString(isMarketOpen));
        JSONObject jsonObject = new JSONObject();
        KDataBean kDataBean = new KDataBean();
        contracts.forEach(c ->{
            kDataBean.setCode(c.getCode());
            kDataBean.setInterval("1");
            List<MarketPointBean> list = marketService.kdata(kDataBean);
            jsonObject.put(c.getCode(),list);
        });
        model.addAttribute("data",jsonObject.toJSONString());
        countNewMessage(model);
        return "/wap/home";
    }

    /**
     *
     * 跳转到交易页
     */
    public String toTrade(Model model){
        homeService.index(model);

        List<Broadcast> broadcasts = broadcastService.findList();
        model.addAttribute("broadcastsJson", JSON.toJSONString(broadcasts));
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        String [] _codes = marketService.codes();
        model.addAttribute("_codes", JSON.toJSONString(_codes));
        model.addAttribute("_user_can_use_coupons", cashCouponService.findUserCashCouponCanUse());

        boolean isMarketOpen = tradeService.isMarketOpen();
        model.addAttribute("isMarketOpen", isMarketOpen);
        model.addAttribute("_isMarketOpen", JSON.toJSONString(isMarketOpen));
        model.addAttribute("_user_coupons", JSON.toJSONString(cashCouponService.findUserCashCouponCanUse()));
        //从首页跳转到交易页需要的交易种类的id
        model.addAttribute("countNewMessage", userService.countNewMessage());
        return "/wap/trade";
    }

    /**
     *
     * 跳转到交易市场
     */
    public String toMarketTrade(Model model){
        homeService.indexMarket(model);

        List<Broadcast> broadcasts = broadcastService.findList();
        model.addAttribute("broadcastsJson", JSON.toJSONString(broadcasts));
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        String [] _codes = marketService.codes();
        model.addAttribute("_codes", JSON.toJSONString(_codes));
        model.addAttribute("_user_can_use_coupons", cashCouponService.findUserCashCouponCanUse());

        boolean isMarketOpen = tradeService.isMarketOpen();
        model.addAttribute("isMarketOpen", isMarketOpen);
        model.addAttribute("_isMarketOpen", JSON.toJSONString(isMarketOpen));
        model.addAttribute("_user_coupons", JSON.toJSONString(cashCouponService.findUserCashCouponCanUse()));
        //从首页跳转到交易页需要的交易种类的id
        model.addAttribute("countNewMessage", userService.countNewMessage());
        return "/wap/marketTrade";
    }

    /**
     * TODO 注册完成跳转带有二维码的页面
     * @param write
     * @return
     */
    public String QRCodeImage(Model model, HttpServletRequest request, HttpServletResponse response){
        return "wap/RQCodeImage";
    }



    public void countNewMessage(Model model){
        model.addAttribute("countNewMessage", userService.countNewMessage());
    }
}
