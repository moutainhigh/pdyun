package com.rmkj.microcap.modules.tradebalancetime.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.tradebalancetime.entity.TradeBalanceTimeBean;
import com.rmkj.microcap.modules.tradebalancetime.service.TradeBalanceTimeService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/tradebalancetime")
public class TradeBalanceTimeController extends BaseController {
    @Autowired
    private TradeBalanceTimeService tradeBalanceTimeService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("tradebalancetime")
    public String listPage() {
        return "/tradebalancetime/tradebalancetime_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("tradebalancetime:add")
    public String addPage() {
         return "/tradebalancetime/tradebalancetime_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("tradebalancetime:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", tradeBalanceTimeService.get(id));
        return "/tradebalancetime/tradebalancetime_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("tradebalancetime")
    public GridPager listData(TradeBalanceTimeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<TradeBalanceTimeBean> entityList = tradeBalanceTimeService.queryList(entity);
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
    @RequiresPermissions("tradebalancetime:add")
    public ExecuteResult save(@Valid TradeBalanceTimeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        tradeBalanceTimeService.insert(entity);
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
    @RequiresPermissions("tradebalancetime:edit")
    public ExecuteResult update(@Valid TradeBalanceTimeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        System.out.println("开始时间"+entity.getOpenTime());
        System.out.println("结束时间"+entity.getCloseTime());
        tradeBalanceTimeService.update(entity);

        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("tradebalancetime:delete")
    public ExecuteResult delete(String id) {
        tradeBalanceTimeService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
