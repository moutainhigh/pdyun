package com.rmkj.microcap.common.modules.trademarket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.retrofit.annotation.HttpService;
import com.rmkj.microcap.common.modules.trademarket.http.GaoKDataApi;
import com.rmkj.microcap.common.modules.trademarket.http.GaoLastestApi;
import com.rmkj.microcap.common.modules.trademarket.http.MarketHttpApi;
import com.rmkj.microcap.modules.trade.service.TradeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by renwp on 2016/10/27.
 * 行情服务
 * 定时更新行情数据、K线图数据
 */
//@Service
//@Lazy(false)
public class HaiJieMarketServer extends AbstractMarketServer {

    @Value("${market_icairon_uName}")
    protected String _uName;

    @Value("${market_icairon_uPass}")
    protected String _uPass;

    @HttpService
    protected MarketHttpApi marketHttpApi;

    /**
     *
     * @param obj
     * @return
     */
    private MarketPointBean toMarketPointBean(JSONObject obj, Integer precision){
        MarketPointBean marketPointBean = new MarketPointBean();
        marketPointBean.setPrice(filterNumber(obj.getString("Price"), precision));
        marketPointBean.setClose(filterNumber(obj.getString("Close"), precision));
        marketPointBean.setOpen(filterNumber(obj.getString("Open"), precision));
        marketPointBean.setHigh(filterNumber(obj.getString("High"), precision));
        marketPointBean.setLow(filterNumber(obj.getString("Low"), precision));
        marketPointBean.setCode(obj.getString("Code"));
        marketPointBean.setTimestamp(obj.getLong("UpdateTime")*1000);
        marketPointBean.setTime(new Date(marketPointBean.getTimestamp()));


        return marketPointBean;
    }

    void refreshNew(String code){
        Response<String> response;
        String body = null;
        JSONObject jsonObject = null;

        // http请求获取行情
        try {
            response = marketHttpApi.getPrice(_uName, _uPass, code).execute();
            if(response.isSuccessful()){
                body = response.body();
                jsonObject = JSON.parseObject(body);
                Map<String, Integer> precisions = TradeService.getPrecisions();
                if("1".equals(jsonObject.getString("Status"))){
                    final MarketPointBean marketPointBean = toMarketPointBean(jsonObject.getJSONObject("Data"), precisions.get(code));
                    // 触发行情变更事件
                    MarketPointBean prePoint = _cacheNew.get(code);
                    // 放入缓存
                    _cacheNew.put(code, marketPointBean);
                    if(prePoint != null && !prePoint.getPrice().equals(marketPointBean.getPrice())){
                        threadPoolTaskExecutor.execute(() ->  {
                            marketService.changeNew(marketPointBean);
                        });
                    }
                }else {
                    Log.error("行情：".concat(URLDecoder.decode(body, "utf-8")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void refreshKData(String code, String interval, int position){
        // 获取最新行情
        Response<String> response;
        String body = null;
        JSONObject jsonObject = null;
        List<MarketPointBean> list = null;
        // http请求获取行情
        try {
            response = marketHttpApi.getChart(_uName, _uPass, code, interval, interval_rows[position]).execute();

            Map<String, Integer> precisions = TradeService.getPrecisions();

            if(response.isSuccessful()){
                body = response.body();
                if(body != null && body.length() > 2){
                    body = body.substring(1, body.length()-1);
                }
                list = toList(body, precisions.get(code));
                if(!list.isEmpty()){
                    _cacheKData.put(code.concat("_").concat(interval), list);
                    interval_init[position] = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MarketPointBean> toList(String jsonStr, Integer precision){
        List<List> list = JSON.parseArray(jsonStr, List.class);
        List<MarketPointBean> l = new ArrayList<>();
        MarketPointBean m = null;
        List<Object> s = null;
        for (int i = 0; i < list.size(); i++){
            s = list.get(i);
            m = new MarketPointBean();
            m.setTimestamp(Long.parseLong(s.get(0)+""));
            m.setOpen(filterNumber(s.get(1).toString(), precision));
            m.setHigh(filterNumber(s.get(2).toString(), precision));
            m.setLow(filterNumber(s.get(3).toString(), precision));
            m.setClose(filterNumber(s.get(4).toString(), precision));
            m.setTime(new Date(m.getTimestamp()));
            l.add(m);
        }
        return l;
    }

    @Override
    String[] reqIntervalsParams() {
        return new String[]{"1", "5", "15", "30", "1h", "1d"};
    }

    protected String filterNumber(Object obj, Integer precision){
        return Double.parseDouble(obj.toString())*1 + "";
    }
}
