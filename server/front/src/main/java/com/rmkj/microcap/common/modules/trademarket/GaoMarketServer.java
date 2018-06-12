package com.rmkj.microcap.common.modules.trademarket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.asm.Type;
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

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by renwp on 2016/10/27.
 * 行情服务
 * 定时更新行情数据、K线图数据
 */
@Service
@Lazy(false)
public class GaoMarketServer extends AbstractMarketServer {

    @HttpService
    protected GaoLastestApi gaoLastestApi;

    @HttpService
    protected GaoKDataApi gaoKDataApi;

    /**
     *
     * @param obj
     * @return
     */
    private MarketPointBean toMarketPointBean(JSONObject obj, Integer precision, String code){
        MarketPointBean marketPointBean = new MarketPointBean();

        marketPointBean.setCode(obj.getString("StockCode"));
        marketPointBean.setPrice(filterNumber(obj.getString("Price"), precision, code));
        marketPointBean.setClose(filterNumber(obj.getString("LastClose"), precision, code));
        marketPointBean.setOpen(filterNumber(obj.getString("Open"), precision, code));
        marketPointBean.setHigh(filterNumber(obj.getString("High"), precision, code));
        marketPointBean.setLow(filterNumber(obj.getString("Low"), precision, code));
        marketPointBean.setTimestamp(obj.getDouble("LastTime").longValue()*1000);
        marketPointBean.setTime(new Date(marketPointBean.getTimestamp()));

        return marketPointBean;
    }

    /**
     * 每隔一秒都去获取最新行情
     */
    void refreshNew(String code){
        Response<String> response;
        String body = null;
        JSONObject jsonObject = null;

        // http请求获取行情
        try {
            response = gaoLastestApi.getPrice(code).execute();
            if(response.isSuccessful()){
                body = response.body();
                if(!"No Stock Data".equals(body)){
                    jsonObject = JSON.parseObject(body);

                    Map<String, Integer> precisions = TradeService.getPrecisions();

                    // 高大总管数据
                    final MarketPointBean marketPointBean = toMarketPointBean(jsonObject, precisions.get(code), code);
                    // 触发行情变更事件
                    MarketPointBean prePoint = _cacheNew.get(code);
                    // 放入缓存
                    _cacheNew.put(code, marketPointBean);
                    if(prePoint != null && !prePoint.getPrice().equals(marketPointBean.getPrice())){
                        threadPoolTaskExecutor.execute(() ->  {
                            marketService.changeNew(marketPointBean);
                        });
                    }
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
        List<MarketPointBean> list = null;
        // http请求获取行情
        try {
            response = gaoKDataApi.getChart(code, interval, interval_rows[position]).execute();

            Map<String, Integer> precisions = TradeService.getPrecisions();

            if(response.isSuccessful()){
                body = response.body();
                Log.info(new Date().toLocaleString().concat(" ").concat(interval+" ").concat(body));
                list = toList(body, precisions.get(code), code);
                if(!list.isEmpty()){
                    _cacheKData.put(code.concat("_").concat(interval), list);
                    interval_init[position] = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MarketPointBean> toList(String jsonStr, Integer precision, String code){
        List<List> list = JSON.parseArray(jsonStr, List.class);
        List<MarketPointBean> l = new ArrayList<>();
        MarketPointBean m = null;
        List<Object> s = null;
        for (int i = 0; i < list.size(); i ++){
            s = list.get(i);
            m = new MarketPointBean();
            m.setTimestamp(Long.parseLong(s.get(0)+""));
            m.setOpen(filterNumber(Double.parseDouble(s.get(1)+""), precision, code));
            m.setHigh(filterNumber(Double.parseDouble(s.get(2)+""), precision, code));
            m.setLow(filterNumber(Double.parseDouble(s.get(3)+""), precision, code));
            m.setClose(filterNumber(Double.parseDouble(s.get(4)+""), precision, code));
            m.setTime(new Date(m.getTimestamp()));
            l.add(m);
        }
        return l;
    }

    @Override
    String[] reqIntervalsParams() {
        return new String[]{"1", "5", "15", "30", "60", "1440"};
    }

    private final static double gram = 31.1035f;
    private final static double exchange = 6.617f;
    private final static double kg = 1000f;

    protected String filterNumber(Object obj, Integer precision, String code){
//        return String.format("%1.0".concat(precision.toString()).concat("f"), Double.parseDouble(obj.toString())*11) + "";
        String format = "";
        if("ZYAU".equals(code) || "PMAG".equals(code) || "PMAU".equals(code)){
            format = String.format("%.0f", Double.parseDouble(Double.parseDouble(obj.toString()) / gram * exchange * kg + ""));
            //Log.info("白银汇率:".concat(format));

        }else if("ZYLCA3M".equals(code) || "CA3M".equals(code)){
            format = String.format("%.0f", Double.parseDouble(Double.parseDouble(obj.toString()) * 6.7f + ""));
        }else if("ZYCL".equals(code) || "CLMASTER".equals(code)){
            format = String.format("%.0f", Double.parseDouble(Double.parseDouble(obj.toString()) * 103 + ""));
        }else if("USDJPY_MT5".equals(code) || "EURJPY_MT5".equals(code)){
            format = String.format("%.0f", Double.parseDouble(Double.parseDouble(obj.toString()) * 1000 + ""));
        }else{
//            String val = String.format("%1.00f", Double.parseDouble(Double.parseDouble(obj.toString())*Math.pow(10, precision) + ""));
//            return val.length() > 6 ? val.substring(val.length() - 6, val.length()) : val;
            //format = String.format("%1.0".concat(precision.toString()).concat("f"), Double.parseDouble(obj.toString())) + "";

            format = String.format("%.0f", Double.parseDouble(obj.toString())) + "";
           /* String val = String.format("%1.00f", Double.parseDouble(Double.parseDouble(obj.toString())*Math.pow(10, precision) + ""));
            return val.length() > 6 ? val.substring(val.length() - 6, val.length()) : val;*/
        }
        return format;
//        return Math.round(Double.parseDouble(obj.toString())*Math.pow(11d, Double.parseDouble(precision+"")))+"";
    }

}
