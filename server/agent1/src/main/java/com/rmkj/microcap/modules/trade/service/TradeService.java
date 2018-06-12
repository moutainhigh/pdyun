package com.rmkj.microcap.modules.trade.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.trade.dao.ITradeDao;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Background;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.Object;
import java.util.List;


/**
* Created by Administrator on 2016-11-22.
*/
@Service
@Transactional
public class TradeService implements IBaseService<TradeBean> {
    @Autowired
    private ITradeDao tradeDao;

    @Override
    public TradeBean get(String id){
        return tradeDao.get(id);
    }
    @Override
    public int update(TradeBean tradeBean){
        tradeBean.preUpdate();
        return tradeDao.update(tradeBean);
    }
    @Override
    public int delete(String id){
        return tradeDao.delete(id);
    }
    @Override
    public int insert(TradeBean tradeBean){
        tradeBean.preInsert();
        return tradeDao.insert(tradeBean);
    }
    @Override
    public List<TradeBean> queryList(TradeBean tradeBean){
        return tradeDao.queryList(tradeBean);
    }
    public List<TradeBean> tradeList(TradeBean bean){return tradeDao.tradeList(bean);}
    public List<TradeBean> holdList(TradeBean bean){
        return tradeDao.holdList(bean);
    }

    //当前持仓列表
    public List<TradeBean> tradeHoldList(TradeBean bean){
        return tradeDao.tradeHoldList(bean);
    }
    //当前持仓数据导出
    public HSSFWorkbook exportExcelHold
    (TradeBean bean) {
        bean.setPage(1);
        bean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"流水号","会员姓名","会员手机号","购买金额","类型","手续费","合约代码","合约名称","点值","盈利百分比","止损百分比","买涨买跌","状态","建仓时间","建仓价"};
        String[] headerColumn = new String[]{"serialNo","uname","mobile","money","type","fee","code","contractName","pointValue","profitMax","lossMax","buyUpDown","status","buyTime","buyPoint"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*280,20*180};
        ExcelUtils.RenderBox renderBox = new ExcelUtils.RenderBox();
        renderBox.add("type", (Object obj) -> {
            TradeBean u = (TradeBean)obj;
            return u.getType() == 0 ? "资金" : "代金券";
        }).add("buyUpDown",(Object obj) ->{
            TradeBean u = (TradeBean)obj;
            return u.getBuyUpDown() == 0 ? "买涨":"买跌";
        }).add("status",(Object obj) ->{
            TradeBean u = (TradeBean)obj;
            return u.getStatus() == 0 ? "持仓":"平仓(交割)";
        })
        ;

        ExcelUtils.work(new ExcelUtils.DataFromDB<TradeBean>(){

            private int page = 1;

            @Override
            public List<TradeBean> find() {
                List<TradeBean> list = tradeDao.tradeHoldList(bean);
                bean.setPage(++page);
                return list;
            }

            @Override
            public long totalPage() {
                return (long)Math.ceil(MybatisPagerInterceptor.getTotal()/bean.getRows().doubleValue());
            }

        }, TradeBean.class, wb, header, headerColumn, columnWidth, renderBox);

        return wb;
    }
    //合计返佣
    public TradeBean fanyongTotal(String id){
        return tradeDao.fanyongTotal(id);
    }
    public TradeBean fanyongMl3Agent(String id){
        return tradeDao.fanyongMl3Agent(id);
    }
    //返佣合计下的军团返佣列表数据
    public List<TradeBean> fanYongMingXiList(TradeBean bean){
        return tradeDao.fanYongMingXiList(bean);
    }
    //返佣合计下的代理返佣列表数据
    public List<TradeBean> fanYongMl3AgentList(TradeBean bean){
        return tradeDao.fanYongMl3AgentList(bean);
    }
    public List<TradeBean> tradeHoldInnerUnitsList(TradeBean bean){
        return tradeDao.tradeHoldInnerUnitsList(bean);
    }
    public List<TradeBean> tradeHoldOperateCenter(TradeBean bean){
        return tradeDao.tradeHoldOperateCenter(bean);
    }
    public List<TradeBean> fanyongInnerUnits(TradeBean bean){
        return tradeDao.fanyongInnerUnits(bean);
    }

    public List<TradeBean> fanyong(TradeBean bean){
        return tradeDao.fanyong(bean);
    }
    //代理商下的返佣明细盈亏综合
    public TradeBean fanyongMl3AgentTotal(TradeBean bean){
        return tradeDao.fanyongMl3AgentTotal(bean);
    }
    //会员单位下的返佣明细盈亏综合
    public TradeBean fanyongUnitsTotal(TradeBean bean){
        return tradeDao.fanyongUnitsTotal(bean);
    }

    public List<TradeBean> fanyongMl3AgentUnits(TradeBean bean){
        return tradeDao.fanyongMl3AgentUnits(bean);
    }
    //会员单位下的会员单位统计的盈亏综合
    public TradeBean fanyongTotal01(TradeBean bean){
        return tradeDao.fanyongTotal01(bean);
    }
}
