package com.rmkj.microcap.modules.syslog.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.syslog.entity.SysLogBean;
import com.rmkj.microcap.modules.syslog.service.SysLogService;
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
* Created by Administrator on 2016-10-21.
*/
@Controller
@RequestMapping("/syslog")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("syslog")
    public String listPage() {
        return "/syslog/syslog_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("syslog:add")
    public String addPage() {
         return "/syslog/syslog_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("syslog:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", sysLogService.get(id));
        return "/syslog/syslog_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("syslog")
    public GridPager listData(SysLogBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<SysLogBean> entityList = sysLogService.queryList(entity);
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
    @RequiresPermissions("syslog:add")
    public ExecuteResult save(@Valid SysLogBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysLogService.insert(entity);
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
    @RequiresPermissions("syslog:edit")
    public ExecuteResult update(@Valid SysLogBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysLogService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("syslog:delete")
    public ExecuteResult delete(String id) {
        sysLogService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //导出到Excel
    @RequestMapping("/exportExcel")
    public void exportExcel(SysLogBean bean, HttpServletResponse response){
        HSSFWorkbook wb = sysLogService.exportExcel(bean);
        ExcelUtils.exportExcel("操作日志", response, wb);
    }
}
