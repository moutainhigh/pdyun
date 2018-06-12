package com.rmkj.microcap.modules.trade.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3OperateCenter.service.Ml3OperateCenterService;
import com.rmkj.microcap.modules.trade.dao.ITradeDao;
import com.rmkj.microcap.modules.trade.dao.TradeStatisticsDao;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import com.rmkj.microcap.modules.trade.entity.Units;
import com.rmkj.microcap.modules.trade.service.TradeService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.modules.trade.service.TradeStatisticsService;
import com.rmkj.microcap.modules.user.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private UserService userService;
    @Autowired
    private TradeStatisticsDao tradeStatisticsDao;
    @Autowired
    private TradeStatisticsService tradeStatisticsService;
    @Autowired
    private Ml3OperateCenterService ml3OperateCenterService;

    @Autowired
    private ITradeDao tradeDao;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String listPage() {
        return "/trade/trade_list";
    }

    //当前持仓页面
    @RequestMapping(value = "/listHold", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String listHoldPage(Model model) {
        List<Units> list = tradeStatisticsService.findUnits();
        model.addAttribute("units", list);
        model.addAttribute("centerList", ml3OperateCenterService.queryList(null));
        return "/trade/tradeHold_list";
    }
    //当前持仓列表数据
    @ResponseBody
    @RequestMapping(value = "/listHold", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public Map<String, Object> listHoldData(TradeBean entity) {
        if(StringUtils.isNotBlank(entity.getMobile())){
            entity.setJunId(tradeStatisticsDao.findUserIdByMobile(entity.getMobile()));
        }
        if(StringUtils.isNotBlank(entity.getAgentMobile())){
            entity.setAgentId(tradeStatisticsDao.findAgentIdByMobile(entity.getAgentMobile()));
        }
        if(null == entity.getStatus()){
            entity.setStatus(1);
        }
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.tradeHoldList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        Map<String, Object> data = new HashedMap();
        data.put("page", g);
        data.put("tradeBean", tradeService.queryHoldFeeAndMoney(entity));
        return data;
    }

    //平仓明细页面
    @RequestMapping(value = "/listCover", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String listCoverPage() {
        return "/trade/tradeCover_list";
    }
    //平仓明细列表数据
    @ResponseBody
    @RequestMapping(value = "/listCover", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public GridPager listCoverData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        MybatisPagerInterceptor.useAutoGetPageTotal(false);
        List<TradeBean> entityList = tradeService.tradeCoverList(entity);
        GridPager g = new GridPager();
        g.setTotal((int)tradeDao.tradeCoverListCount(entity));
        g.setRows(entityList);
        return g;
    }
    //当前持仓数据导出
    @RequestMapping("exportExcelHold")
    public void exportExcel(TradeBean bean, HttpServletResponse response){
        HSSFWorkbook wb = tradeService.exportExcelHold(bean);
        ExcelUtils.exportExcel("当前持仓", response, wb);
    }
    //平仓明细数据导出
    @RequestMapping("exportExcelCover")
    public void exportExcelCover(TradeBean bean, HttpServletResponse response){
        HSSFWorkbook wb = tradeService.exportExcelCover(bean);
        ExcelUtils.exportExcel("平仓明细", response, wb);
    }










    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("trade:add")
    public String addPage() {
         return "/trade/trade_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("trade:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", tradeService.get(id));
        return "/trade/trade_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public GridPager listData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("trade:add")
    public ExecuteResult save(@Valid TradeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        tradeService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 更新
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("trade:edit")
    public ExecuteResult update(@Valid TradeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        tradeService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("trade:delete")
    public ExecuteResult delete(String id) {
        tradeService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    //军团长下的返佣明细
    @RequestMapping(value = "/fanyong", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String fanyongPage() {
        return "/trade/juntuanTrade_list";
    }
    //军团长下的返佣明细列表数据
    @ResponseBody
    @RequestMapping(value = "/fanyong", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public Map<String, Object> fanyongData(@Valid TradeBean entity) throws Exception{
        String jtMobile = request.getParameter("jtMobile");
        String userChnName = request.getParameter("userChnName");
        String userMobile = request.getParameter("userMobile");
        String money = request.getParameter("money");
        String serialNo = request.getParameter("serialNo");
        String sellTimeMin = request.getParameter("sellTimeMin");
        String sellTimeMax = request.getParameter("sellTimeMax");
        TradeBean tradeBean = new TradeBean();
        tradeBean.setJtMobile(jtMobile);
        if(userChnName != null){
            tradeBean.setUserChnName(userChnName);
        }
        if(userMobile != null){
            tradeBean.setJtMobile(userMobile);
        }
        if(money != null){
            BigDecimal bigDecimal = new BigDecimal(money);
            tradeBean.setMoney(bigDecimal);
        }
        if(serialNo != null){
            tradeBean.setSerialNo(serialNo);
        }
        if(sellTimeMin != null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date1=sdf.parse(sellTimeMin);
            tradeBean.setSellTimeMin(date1);
        }
        if(sellTimeMax != null){
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date2=sdf1.parse(sellTimeMax);
            tradeBean.setSellTimeMax(date2);
        }
        List<TradeBean> list = tradeService.fanYongMingXiList(tradeBean);
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanYongMingXiList(entity);
        TradeBean fanyongTotal = tradeService.fanyongTotal(tradeBean);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(list.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("fanyongTotal",fanyongTotal);
        return map;
    }

    //代理商下的返佣明细
    @RequestMapping(value = "/fanyongMl3Agent", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String fanyongMl3AgentPage() {
        return "/trade/fanyongMl3Agent_list";
    }
    @ResponseBody
    @RequestMapping(value = "/fanyongMl3Agent", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public Map<String,Object> fanyongMl3AgentData(@Valid TradeBean entity) throws Exception {
        String jtMobile = request.getParameter("jtMobile");
        String userChnName = request.getParameter("userChnName");
        String userMobile = request.getParameter("userMobile");
        String money = request.getParameter("money");
        String serialNo = request.getParameter("serialNo");
        String sellTimeMin = request.getParameter("sellTimeMin");
        String sellTimeMax = request.getParameter("sellTimeMax");
        TradeBean tradeBean = new TradeBean();
        tradeBean.setJtMobile(jtMobile);
        if(userChnName != null){
            tradeBean.setUserChnName(userChnName);
        }
        if(userMobile != null){
            tradeBean.setJtMobile(userMobile);
        }
        if(money != null){
            BigDecimal bigDecimal = new BigDecimal(money);
            tradeBean.setMoney(bigDecimal);
        }
        if(serialNo != null){
            tradeBean.setSerialNo(serialNo);
        }
        if(sellTimeMin != null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date1=sdf.parse(sellTimeMin);
            tradeBean.setSellTimeMin(date1);
        }
        if(sellTimeMax != null){
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date2=sdf1.parse(sellTimeMax);
            tradeBean.setSellTimeMax(date2);
        }
        List<TradeBean> list = tradeService.fanYongMl3AgentList(tradeBean);
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanYongMl3AgentList(entity);
        TradeBean fanyongUnitsTotal = tradeService.fanyongMl3AgentTotal(tradeBean);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(list.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",fanyongUnitsTotal);
        return map;
    }

    //会员单位下的返佣明细
    @RequestMapping(value = "/fanyongUnits", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String fanyongUnitsPage() {
        return "/trade/fanyongUnits_list";
    }
    @ResponseBody
    @RequestMapping(value = "/fanyongUnits", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public Map<String, Object> fanyongUnitsData(@Valid TradeBean entity) throws Exception{
        String unitsName = request.getParameter("unitsName");
        String sellTimeMin = request.getParameter("sellTimeMin");
        String sellTimeMax = request.getParameter("sellTimeMax");
        TradeBean tradeBean = new TradeBean();
        if(unitsName != null){
            tradeBean.setUnitsName(unitsName);
        }
        if(sellTimeMin != null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date1=sdf.parse(sellTimeMin);
            tradeBean.setSellTimeMin(date1);
        }
        if(sellTimeMax != null){
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            Date date2=sdf1.parse(sellTimeMax);
            tradeBean.setSellTimeMax(date2);
        }
        List<TradeBean> list = tradeService.fanYongUnitsList(tradeBean);
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanYongUnitsList(entity);
        TradeBean fanyongUnitsTotal = tradeService.fanyongUnitsTotal(tradeBean);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(list.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",fanyongUnitsTotal);
        return map;
    }
//    public GridPager fanyongUnitsData(@Valid TradeBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<TradeBean> entityList = tradeService.fanYongUnitsList(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }
    //代理商管理下的盈亏统计
    @RequestMapping(value = "/yingkuitotal", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String yingkuitotalPage() {
        return "/trade/yingkuiMl3Agent_list";
    }
    @ResponseBody
    @RequestMapping(value = "/yingkuitotal", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public  Map<String, Object> yingkuitotalData(@Valid TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.yingkuitotal(entity);
        TradeBean yingkuiTotal = tradeService.yingkuiTotal(entity);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(entityList.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",yingkuiTotal);
        return map;
    }
    //会员单位管理下的盈亏统计
    @RequestMapping(value = "/yingkuiInnerUnits", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String yingkuiInnerUnitsPage() {
        return "/trade/yingkuiInnerUnits_list";
    }
    @ResponseBody
    @RequestMapping(value = "/yingkuiInnerUnits", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public  Map<String, Object> yingkuiInnerUnitsData(@Valid TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.yingkuiInnerUnits(entity);
        TradeBean yingkuiTotal = tradeService.yingkuiInnerUnitsTotal(entity);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(entityList.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",yingkuiTotal);
        return map;
    }
    //军团长管理下的盈亏统计
    @RequestMapping(value = "/yingkuijuntuan", method = RequestMethod.GET)
    @RequiresPermissions("trade")
    public String yingkuijuntuanPage() {
        return "/trade/yingkuijuntuan_list";
    }
    @ResponseBody
    @RequestMapping(value = "/yingkuijuntuan", method = RequestMethod.POST)
    @RequiresPermissions("trade")
    public  Map<String, Object> yingkuijuntuanData(@Valid TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.yingkuijuntuan(entity);
        TradeBean yingkuiTotal = tradeService.yingkuijuntuanTotal(entity);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(entityList.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",yingkuiTotal);
        return map;
    }


    /**
     * 持仓转化
     * @param id
     * @return
     */
    @RequestMapping(value = "/transHold", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("trade")
    public ExecuteResult transHold(String id, String mobile, String transNum){
        return tradeService.transHold(id, mobile, transNum);
    }

    @RequestMapping(value = "/transHold", method = RequestMethod.GET)
    public String goTransHold(String id, Model model){
        model.addAttribute("id", id);
        return "/trade/trade_trans";
    }
}
