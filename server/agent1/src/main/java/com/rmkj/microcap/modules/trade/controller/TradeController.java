package com.rmkj.microcap.modules.trade.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.trade.entity.TradeBean;
import com.rmkj.microcap.modules.trade.service.TradeService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
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
* Created by Administrator on 2016-11-22.
*/
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private Ml3AgentService ml3AgentService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage(String id, Map<String, Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/trade/trade_list";
    }
    //客户收益统计页面
    @RequestMapping(value = "/ShouYi", method = RequestMethod.GET)
    public String ShouYiPage(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/trade/trade_ShouYiList";
    }
    @ResponseBody
    @RequestMapping(value = "/ShouYi", method = RequestMethod.POST)
    public GridPager ShouYiData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.tradeList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    //客户持仓列表
    @RequestMapping(value = "/hold", method = RequestMethod.GET)
    public String holdPage(String id, Map<String, Object> model) {
        model.put("id", UserUtils.getUser().getId());
        return "/trade/trade_holdList";
    }
    @ResponseBody
    @RequestMapping(value = "/hold", method = RequestMethod.POST)
    public GridPager holdData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.holdList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
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

    //合计返佣金额页面
//    @RequestMapping(value = "/juntuanfanyong", method = RequestMethod.GET)
//    public String fanyongtotalPage(String id,Map<String,Object> model) {
//        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
//        TradeBean tradeBean = tradeService.fanyongTotal(ml3AgentBean.getUserId());
//        TradeBean tradeBean1 = tradeService.fanyongMl3Agent(ml3AgentBean.getUserId());
//        model.put("model", tradeBean);
//        model.put("model1",tradeBean1);
//        return "/trade/juntuanTrade_list";
//    }
    //合计返佣下的军团返佣列表页面
    @RequestMapping(value = "/juntuanfanyong", method = RequestMethod.GET)
    public String fanyongPage(String id,Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUserId());
        return "/trade/juntuanTrade_list";
    }
    //合计返佣的军团返佣列表页面
    @ResponseBody
    @RequestMapping(value = "/juntuanfanyong", method = RequestMethod.POST)
    public Map<String, Object> fanyongData(@Valid TradeBean entity,Model model) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanYongMingXiList(entity);
        TradeBean fanyongTotal = tradeService.fanyongTotal(entity.getJtMobile());
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        map.put("g",g);
        map.put("fanyongTotal",fanyongTotal);
        return map;
    }

    //合计返佣下的代理返佣
    @RequestMapping(value = "/fanyongMl3Agent", method = RequestMethod.GET)
    public String fanyongMl3AgentPage(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getId());
        return "/trade/fanyongMl3Agent_list";
    }
    @ResponseBody
    @RequestMapping(value = "/fanyongMl3Agent", method = RequestMethod.POST)
    public Map<String, Object> fanyongMl3AgentData(@Valid TradeBean entity) throws Exception {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanYongMl3AgentList(entity);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        map.put("g",g);
        if(entity.getPage() == 1){
            TradeBean fanyongUnitsTotal = tradeService.fanyongMl3AgentTotal(entity);
            map.put("yingkuiTotal", fanyongUnitsTotal);
        }
        return map;
    }
//    public GridPager ffanyongMl3AgentData(@Valid TradeBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<TradeBean> entityList = tradeService.fanYongMl3AgentList(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }


    //当前持仓页面
    @RequestMapping(value = "/listHold", method = RequestMethod.GET)
    public String listHoldPage() {
        return "/trade/tradeHold_list";
    }
    //当前持仓列表数据
    @ResponseBody
    @RequestMapping(value = "/listHold", method = RequestMethod.POST)
    public GridPager listHoldData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setId(ml3AgentBean.getAgentInviteCode());
        List<TradeBean> entityList = tradeService.tradeHoldList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }
    //当前持仓页面
    @RequestMapping(value = "/tradeHoldInnerUnits", method = RequestMethod.GET)
    public String listHold1Page(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/trade/tradeHoldInnerUnits_list";
    }
    //当前持仓列表数据
    @ResponseBody
    @RequestMapping(value = "/tradeHoldInnerUnits", method = RequestMethod.POST)
    public GridPager listHold1Data(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        entity.setUnitsId(entity.getId());
        List<TradeBean> entityList = tradeService.tradeHoldInnerUnitsList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    //市场管理部当前持仓页面
    @RequestMapping(value = "/tradeHoldOperateCenter", method = RequestMethod.GET)
    public String listHoldOperateCenterPage(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getCenterId());
        return "/trade/tradeHoldOperateCenter_list";
    }
    //市场管理部当前持仓列表数据
    @ResponseBody
    @RequestMapping(value = "/tradeHoldOperateCenter", method = RequestMethod.POST)
    public GridPager listHoldOperateCenterData(TradeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        if(null == entity.getStatus()){
            entity.setStatus(1);
        }
        List<TradeBean> entityList = tradeService.tradeHoldOperateCenter(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    //当前持仓数据导出
    @RequestMapping("exportExcelHold")
    public void exportExcel(TradeBean bean, HttpServletResponse response){
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        bean.setAgentId(ml3AgentBean.getUserId());
        HSSFWorkbook wb = tradeService.exportExcelHold(bean);
        ExcelUtils.exportExcel("当前持仓", response, wb);
    }

    //会员单位下的军团返佣列表页面
    @RequestMapping(value = "/fanyongInnerUnits", method = RequestMethod.GET)
    public String fanyongInnerUnitsPage(String id,Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getId());
        return "/trade/juntuanfyInnerUnits_list";
    }
    //会员单位的军团返佣列表页面
    @ResponseBody
    @RequestMapping(value = "/fanyongInnerUnits", method = RequestMethod.POST)
    public Map<String, Object> fanyongInnerUnitsData(@Valid TradeBean entity,Model model) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanyongInnerUnits(entity);
        TradeBean fanyongTotal = tradeService.fanyongTotal(entity.getJtMobile());
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        map.put("g",g);
        map.put("fanyongTotal",fanyongTotal);
        return map;
    }
    //会员单位下的代理返佣
    @RequestMapping(value = "/fanyongMl3AgentUnits", method = RequestMethod.GET)
    public String fanyongMl3AgentUnitsPage(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/trade/fanyongMl3AgentUnits_list";
    }

    @ResponseBody
    @RequestMapping(value = "/fanyongMl3AgentUnits", method = RequestMethod.POST)
    public Map<String, Object> fanyongMl3AgentUnitsData(@Valid TradeBean entity) throws Exception{
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setUnitsId(ml3AgentBean.getUnitsId());

        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanyongMl3AgentUnits(entity);

        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);

        Map<String, Object> map = new HashMap<>();
        map.put("g",g);

        if(entity.getPage() == 1){
            TradeBean fanyongTotal = tradeService.fanyongUnitsTotal(entity);
            map.put("yingkuiTotal",fanyongTotal);
        }
        return map;
    }

    //市场管理部下的代理返佣
    @RequestMapping(value = "/fanyongMl3AgentOperateCenter", method = RequestMethod.GET)
    public String fanyongMl3AgentOperateCenter(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getCenterId());
        return "/trade/fanyongMl3AgentUnits_list";
    }
    @ResponseBody
    @RequestMapping(value = "/fanyongMl3AgentOperateCenter", method = RequestMethod.POST)
    public Map<String, Object> fanyongMl3AgentOperateCenter(@Valid TradeBean entity) throws Exception{
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        entity.setCenterId(ml3AgentBean.getCenterId());

        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanyongMl3AgentUnits(entity);

        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);

        Map<String, Object> map = new HashMap<>();
        map.put("g",g);

        if(entity.getPage() == 1){
            TradeBean fanyongTotal = tradeService.fanyongUnitsTotal(entity);
            map.put("yingkuiTotal",fanyongTotal);
        }
        return map;
    }

//    public GridPager fanyongMl3AgentUnitsData(@Valid TradeBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<TradeBean> entityList = tradeService.fanyongMl3AgentUnits(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }
    //会员单位下的会员单位返佣
    @RequestMapping(value = "/fanyong", method = RequestMethod.GET)
    public String fanyongPage(Map<String,Object> model) {
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        model.put("id", ml3AgentBean.getUnitsId());
        return "/trade/fanyong_list";
    }
    @ResponseBody
    @RequestMapping(value = "/fanyong", method = RequestMethod.POST)
    public Map<String, Object> fanyongData(@Valid TradeBean entity) throws Exception{
        TradeBean tradeBean = new TradeBean();
        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
        tradeBean.setUnitsId(ml3AgentBean.getUnitsId());
        String sellTimeMin = request.getParameter("sellTimeMin");
        String sellTimeMax = request.getParameter("sellTimeMax");
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
        List<TradeBean> list = tradeService.fanyong(tradeBean);

        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBean> entityList = tradeService.fanyong(entity);
        TradeBean fanyongTotal = tradeService.fanyongTotal01(tradeBean);
        Map<String, Object> map = new HashMap<>();
        GridPager g = new GridPager();
        g.setTotal(list.size());
        g.setRows(entityList);
        map.put("g",g);
        map.put("yingkuiTotal",fanyongTotal);
        return map;
    }
//    public GridPager fanyongData(@Valid TradeBean entity) {
//        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
//        List<TradeBean> entityList = tradeService.fanyong(entity);
//        GridPager g = new GridPager();
//        g.setTotal(MybatisPagerInterceptor.getTotal());
//        g.setRows(entityList);
//        return g;
//    }
}
