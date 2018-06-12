package com.rmkj.microcap.common.modules.trademarket;

import com.rmkj.microcap.common.constants.ProjectConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;

/**
 * Created by renwp on 2016/12/15.
 */
public abstract class AbstractMarketServer {

    protected final Logger Log = Logger.getLogger(AbstractMarketServer.class);

    // 行情数据缓存
    protected final Map<String, MarketPointBean> _cacheNew = new HashMap<>();
    protected final Map<String, List<MarketPointBean>> _cacheKData = new HashMap<>();

    @Autowired
    protected AbstractMarketService marketService;

    @Autowired
    protected MarketDebugServer marketDebugServer;

    public MarketPointBean getCacheNew(String code){
        return _cacheNew.get(code);
    }

    public List<MarketPointBean> getCacheKDatak(String key){
        return _cacheKData.get(key);
    }

    /**
     *
     * @return
     */
    public String[] _codes(){
        return marketService.codes();
    }

    // 止损止盈平仓 线程池
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor = null;

    /**
     * 每隔一秒都去获取最新行情
     */
//    @Scheduled(initialDelay = 6000, fixedDelay = 350)
    private void _refreshNew(){
        // debug模式
        if(ProjectConstants.MARKET_DEBUG){
            String[] codes = _codes();
            List<MarketPointBean> latest = marketDebugServer.latest(codes);
            for (MarketPointBean point : latest){
                MarketPointBean prePoint = _cacheNew.get(point.getCode());
                _cacheNew.put(point.getCode(), point);

                if(prePoint != null && !prePoint.getPrice().equals(point.getPrice())){
                    threadPoolTaskExecutor.execute(() ->  {
                        marketService.changeNew(point);
                    });
                }
            }
        }else {
            // http请求获取行情
            for (String code : _codes()){
                threadPoolTaskExecutor.execute(() ->  {
                    refreshNew(code);
                });
            }
        }
    }

    /**
     * 获取最新行情数据
     */
    abstract void refreshNew(String code);

    /**
     * 定时获取K线图
     * 不同K线图，获取频率不一样，获取条数不一样
     *
     * 获取失败，总是会每分钟都去重新获取数据。无数据也算作失败
     */
    protected final String[] interval_rows = new String[]{"200", "20", "20", "20", "20", "20"};
    protected boolean [] interval_init = new boolean[]{false,false,false,false,false,false};

    protected String cacheKey(String code, String interval){
        return code.concat("_").concat(interval);
    }

    /**
     * 重置K线图缓存数据
     */
    public void reset(){
        interval_init = new boolean[]{false,false,false,false,false,false};
    }

    /**
     * 清空code缓存数据
     * @param code
     */
    public void clearCodeCache(String code){
        String[] intervalsParams = reqIntervalsParams();
        for(int i = 0; i < intervalsParams.length; i ++){
            _cacheKData.remove(cacheKey(code, intervalsParams[i]));
        }
        _cacheNew.remove(code);
    }

    /**
     * 每隔一秒都去判断是否获取最新K线图，满足条件才发送请求
     * 分线图，秒=0
     * 5分K线图，秒=1，分%5=0
     * 15分K线图，秒=2，分%15=0
     * 30分K线图，秒=3，分%30=0
     * 1小时K线图，秒=4，分=0
     * 1天K线图，秒=5，分=0，时=0
     */
//    @Scheduled(initialDelay = 6000, fixedRate = 1000)
    private void refreshKData(){
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String[] intervalsParams = reqIntervalsParams();
        // 分线图
        if(!interval_init[0] || second == 3){
            _refreshKData(intervalsParams[0], 0);
        }
        // 5分钟
        if(!interval_init[1] || (second == 4 && minute%5 == 0)){
            _refreshKData(intervalsParams[1], 1);
        }
        // 15分钟
        if(!interval_init[2] || (second == 5 && minute%15 == 0)){
            _refreshKData(intervalsParams[2], 2);
        }
        // 30分钟
        if(!interval_init[3] || (second == 6 && minute%30 == 0)){
            _refreshKData(intervalsParams[3], 3);
        }
        // 1小时
        if(!interval_init[4] || (second == 7 && minute == 0)){
            _refreshKData(intervalsParams[4], 4);
        }
        // 1天
        if(!interval_init[5] || (second == 8 && minute == 0 && hour == 0)){
            _refreshKData(intervalsParams[5], 5);
        }
    }

    private void _refreshKData(String interval, int position){
        // debug模式
        if(ProjectConstants.MARKET_DEBUG){
            Map<String, List<MarketPointBean>> codeListMap = marketDebugServer.latestKData(interval, Integer.parseInt(interval_rows[position]), _codes());
            Set<String> codes = codeListMap.keySet();
            for (String code : codes){
                _cacheKData.put(cacheKey(code, interval), codeListMap.get(code));
            }
            interval_init[position] = true;
        }else{
            // http请求获取行情
            for (String code : _codes()){
                threadPoolTaskExecutor.execute(() ->  {
                    refreshKData(code, interval, position);
                });
            }
        }
    }

    /**
     * 获取K线图数据
     * @param interval
     * @param position
     */
    abstract void refreshKData(String code, String interval, int position);

    abstract String[] reqIntervalsParams();

}
