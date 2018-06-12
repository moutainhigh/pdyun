package com.rmkj.microcap.modules.moneychange.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangeBean;
import com.rmkj.microcap.modules.moneychange.entity.MoneyChangePageBean;
import com.rmkj.microcap.modules.moneychange.service.MoneyChangeService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/moneychange")
public class MoneyChangeController extends BaseController {
    @Autowired
    private MoneyChangeService moneyChangeService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("moneychange")
    public String listPage() {
        return "/moneychange/moneychange_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("moneychange:add")
    public String addPage() {
         return "/moneychange/moneychange_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("moneychange:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", moneyChangeService.get(id));
        return "/moneychange/moneychange_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
   /* @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("moneychange")
    public GridPager listData(MoneyChangeBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<MoneyChangeBean> entityList = moneyChangeService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }*/
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("moneychange")
    public GridPager listData(MoneyChangePageBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        return moneyChangeService.findMoneyChangePage(entity);
    }
    /**
    * 保存
    * @param entity
    * @param errors
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("moneychange:add")
    public ExecuteResult save(@Valid MoneyChangeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        moneyChangeService.insert(entity);
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
    @RequiresPermissions("moneychange:edit")
    public ExecuteResult update(@Valid MoneyChangeBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        moneyChangeService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("moneychange:delete")
    public ExecuteResult delete(String id) {
        moneyChangeService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //导出到Excel
    @RequestMapping("/exportExcel")
    public void exportExcel(MoneyChangeBean bean, HttpServletResponse response){
        HSSFWorkbook wb = moneyChangeService.exportExcel(bean);
        ExcelUtils.exportExcel("资金变更明细", response, wb);
    }
}
