
package com.rmkj.microcap.modules.trade.service;

import com.rmkj.microcap.common.base.IBaseService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.holdTransTrade.dao.HoldTransTradeDao;
import com.rmkj.microcap.modules.holdTransTrade.entity.HoldTransTradeBean;
import com.rmkj.microcap.modules.parameterset.dao.IParameterSetDao;
import com.rmkj.microcap.modules.parameterset.entity.ParameterSetBean;
import com.rmkj.microcap.modules.subGoods.entity.TakeGoodsBean;
import com.rmkj.microcap.modules.trade.dao.ITradeDao;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.user.entity.UserBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.Object;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
* Created by Administrator on 2016-10-17.
*/
@Service
@Transactional
public class TradeService implements IBaseService<TradeBean> {
    @Autowired
    private ITradeDao tradeDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IParameterSetDao parameterSetDao;
    @Autowired
    private HoldTransTradeDao holdTransTradeDao;

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

    //当前持仓列表
    public List<TradeBean> tradeHoldList(TradeBean bean){
        return tradeDao.tradeHoldList(bean);
    }

    public TradeBean queryHoldFeeAndMoney(TradeBean tradeBean){
        return tradeDao.queryHoldFeeAndMoney(tradeBean);
    }

    //平仓明细列表
    public List<TradeBean> tradeCoverList(TradeBean bean){return tradeDao.tradeCoverList(bean);}
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

    //平仓明细数据导出
    public HSSFWorkbook exportExcelCover
    (TradeBean bean) {
        bean.setPage(1);
        bean.setRows(ExcelUtils.SHEET_MAX_SIZE);

        HSSFWorkbook wb = new HSSFWorkbook();
        String[] header = new String[]{"流水号","会员姓名","会员手机号","购买金额","类型","手续费","合约代码","合约名称","点值","盈利百分比","止损百分比","买涨买跌","状态","建仓时间","建仓价","平仓价","盈亏金额","平仓类型","平仓时间"};
        String[] headerColumn = new String[]{"serialNo","uname","mobile","money","type","fee","code","contractName","pointValue","profitMax","lossMax","buyUpDown","status","buyTime","buyPoint","sellPoint","difMoney","sellType","sellTime"};
        int[] columnWidth = new int[]{20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*180,20*280,20*180,20*180,20*180,20*180,20*280};
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
        }).add("sellType",(Object obj) ->{
            TradeBean u = (TradeBean)obj;
            return u.getSellType() == 0 ? "手动平仓":(u.getSellType() == 1 ? "止盈止损平仓":"休市平仓");
        })
        ;

        ExcelUtils.work(new ExcelUtils.DataFromDB<TradeBean>(){

            private int page = 1;

            @Override
            public List<TradeBean> find() {
                List<TradeBean> list = tradeDao.tradeCoverList(bean);
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
    public List<TradeBean> fanYongMingXiList(TradeBean bean){
        return tradeDao.fanYongMingXiList(bean);
    }
    public TradeBean fanyongTotal(TradeBean bean){
        return tradeDao.fanyongTotal(bean);
    }
    public List<TradeBean> fanYongMl3AgentList(TradeBean bean){
        return tradeDao.fanYongMl3AgentList(bean);
    }
    public List<TradeBean> fanYongUnitsList(TradeBean bean){
        return tradeDao.fanYongUnitsList(bean);
    }
    public TradeBean fanyongUnitsTotal(String name){
        return tradeDao.fanyongUnitsTotal(name);
    }
    //代理商管理下的盈亏统计
    public List<TradeBean> yingkuitotal(TradeBean bean){
        return tradeDao.yingkuitotal(bean);
    }
    //代理商管理下的盈亏统计的交易总和和盈亏总和
    public TradeBean yingkuiTotal(TradeBean bean){
        return tradeDao.yingkuiTotal(bean);
    }
    //会员单位管理下的盈亏统计
    public List<TradeBean> yingkuiInnerUnits(TradeBean bean){
        return tradeDao.yingkuiInnerUnits(bean);
    }
    //会员单位管理下的盈亏统计的交易总和和盈亏总和
    public TradeBean yingkuiInnerUnitsTotal(TradeBean bean){
        return tradeDao.yingkuiInnerUnitsTotal(bean);
    }
    //军团长管理下的盈亏统计
    public List<TradeBean> yingkuijuntuan(TradeBean bean){
        return tradeDao.yingkuijuntuan(bean);
    }
    //军团长管理下的盈亏痛就的交易总和和盈亏总和
    public TradeBean yingkuijuntuanTotal(TradeBean bean){
        return tradeDao.yingkuijuntuanTotal(bean);
    }
    //代理商下的返佣明细盈亏综合
    public TradeBean fanyongMl3AgentTotal(TradeBean bean){
        return tradeDao.fanyongMl3AgentTotal(bean);
    }
    //会员单位下的返佣明细盈亏综合
    public TradeBean fanyongUnitsTotal(TradeBean bean){
        return tradeDao.fanyongUnitsTotal(bean);
    }


    public ExecuteResult transHold(String id, String mobile, String transNum){
        TradeBean tradeBean = tradeDao.getHoldTradeById(id, Constants.TRADE_HOLD_FLAG.TRADE_BUY);
        if(null == tradeBean){
            return new ExecuteResult(StatusCode.FAILED, "不存在的订单");
        }
        if(tradeBean.getHoldNum() < Integer.parseInt(transNum)){
            return new ExecuteResult(StatusCode.FAILED, "订单数量不足");
        }
        UserBean user = userDao.getUserByMobile(mobile);
        if(null == user){
            return new ExecuteResult(StatusCode.FAILED, "输入用户不存在");
        }
        //获取认购服务费，买入手续费比例
        ParameterSetBean parameterSetBean = parameterSetDao.queryList(null).get(0);
        BigDecimal subServiceScale = parameterSetBean.getSubServiceScale().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal subFeeScale = parameterSetBean.getSubFeeScale().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_DOWN);
        //单笔认购服务费
        BigDecimal serviceFee = tradeBean.getServiceFee().divide(new BigDecimal(tradeBean.getHoldNum()), 2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal feeBuy = tradeBean.getFeeBuy().divide(new BigDecimal(tradeBean.getHoldNum()), 2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal money = tradeBean.getMoney().divide(new BigDecimal(tradeBean.getHoldNum()), 2, BigDecimal.ROUND_HALF_DOWN);

        /**
         * 验证此用户是否存在， 存在则创建该用户订单，并记录
         */

        TradeBean trade = new TradeBean();
        trade.preInsert();
        trade.setSerialNo(serialNo());
        trade.setOldTradeSerialNo(tradeBean.getSerialNo());
        trade.setUserId(user.getId());
        trade.setHoldNum(Integer.parseInt(transNum));
        trade.setGoodsId(tradeBean.getGoodsId());
        trade.setGoodsName(tradeBean.getGoodsName());
        trade.setBuyPoint(tradeBean.getBuyPoint());
        trade.setBuyTime(new Date());
        trade.setStatus(Integer.valueOf(Constants.SUB_GOODS_STATUS.SUB));
        trade.setHoldFlag(Constants.TRADE_HOLD_FLAG.TRANS_BUY);

        //服务费
        trade.setServiceFee(serviceFee.multiply(new BigDecimal(transNum)));
        trade.setMoney(money.multiply(new BigDecimal(transNum)));
        trade.setFeeBuy(feeBuy.multiply(new BigDecimal(transNum)));
        trade.setFeeSell(new BigDecimal(0));

        trade.setParent1Id(user.getParent1Id());
        trade.setParent2Id(user.getParent2Id());
        trade.setParent3Id(user.getParent3Id());
        trade.setCenterId(user.getCenterId());
        trade.setUnitsId(user.getUnitsId());
        trade.setAgentId(user.getAgentId());

        //添加记录
        HoldTransTradeBean transBean = new HoldTransTradeBean();
        transBean.setId(Utils.uuid());
        transBean.setHoldTradeId(tradeBean.getId());
        transBean.setHoldUserId(tradeBean.getUserId());
        transBean.setTransUserMobile(user.getMobile());
        transBean.setHoldSerialNo(tradeBean.getSerialNo());

        transBean.setTransHoldNum(trade.getHoldNum());
        transBean.setServiceFee(trade.getServiceFee());
        transBean.setBuyFee(trade.getFeeBuy());
        transBean.setMoney(trade.getMoney());

        transBean.setGoodsId(tradeBean.getGoodsId());
        transBean.setGoodsName(tradeBean.getGoodsName());
        transBean.setBuyPoint(tradeBean.getBuyPoint());
        transBean.setCreateTime(new Date());
        transBean.setHoldFlag(tradeBean.getHoldFlag());
        transBean.setTransUserId(user.getId());

        int balanceNum = tradeBean.getHoldNum() - Integer.parseInt(transNum);

        if(tradeDao.transHold(trade) != 1 || (balanceNum > 0 ? tradeDao.updateTransHold(trade) != 1 : tradeDao.updateTransHoldSell(trade) != 1) || holdTransTradeDao.insert(transBean) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ExecuteResult(StatusCode.FAILED, "持仓划转失败");
        }

        return new ExecuteResult(StatusCode.OK, "持仓划转成功");
    }

    private final Random random = new Random();

    /**
     * 获取交易流水号
     * @return
     */
    private String serialNo(){
        return "JY" + new Date().getTime()+""+String.format("%1$03d",random.nextInt(1000));
    }


}
