package com.rmkj.microcap.modules.holiday.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.holiday.entity.HolidayModelBean;
import com.rmkj.microcap.modules.holiday.service.HolidayModelService;
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
@RequestMapping("/holiday")
public class HolidayModelController extends BaseController {
    @Autowired
    private HolidayModelService holidayModelService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("holiday")
    public String listPage() {
        return "/holiday/holiday_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("holiday:add")
    public String addPage() {
         return "/holiday/holiday_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("holiday:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", holidayModelService.get(id));
        return "/holiday/holiday_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("holiday")
    public GridPager listData(HolidayModelBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<HolidayModelBean> entityList = holidayModelService.queryList(entity);
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
    @RequiresPermissions("holiday:add")
    public ExecuteResult save(@Valid HolidayModelBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        holidayModelService.insert(entity);
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
    @RequiresPermissions("holiday:edit")
    public ExecuteResult update(@Valid HolidayModelBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        holidayModelService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("holiday:delete")
    public ExecuteResult delete(String id) {
        holidayModelService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
