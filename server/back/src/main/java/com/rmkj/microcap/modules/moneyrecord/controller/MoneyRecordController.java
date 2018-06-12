package com.rmkj.microcap.modules.moneyrecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyInRecordPageBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordBean;
import com.rmkj.microcap.modules.moneyrecord.service.MoneyRecordService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/moneyrecord")
public class MoneyRecordController extends BaseController {
    @Autowired
    private MoneyRecordService moneyRecordService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("moneyrecord")
    public String listPage() {
        return "/moneyrecord/moneyrecord_list";
    }
    /**
     * 入金管理页面
     * @return
     */
    @RequestMapping(value = "/listin", method = RequestMethod.GET)
    @RequiresPermissions("moneyrecord")
    public String listInPage(Model model) {
        model.addAttribute("channelList", moneyRecordService.queryRechargeChannel());
        return "/moneyrecord/moneyrecordin_list";
    }
    /**
     * 入金列表数据
     * @param entity
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/listin", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord")
    public Map<String,Object> listInData(MoneyRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<MoneyRecordBean> entityList = moneyRecordService.moneyInList(entity);
        Map<String,Object> map = new HashedMap();
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        map.put("g",g);

        if(entity.getPage() == 1){
            BigDecimal moneyInTotal = moneyRecordService.moneyInTotal(entity);
            BigDecimal noMoneyInTotal = moneyRecordService.noMoneyInTotal(entity);
            BigDecimal alreadyInTotal = moneyRecordService.alreadyMoneyInTotal(entity);
            map.put("moneyInTotal", moneyInTotal == null ? 0 : moneyInTotal);
            map.put("noMoneyInTotal",noMoneyInTotal == null ? 0 : noMoneyInTotal);
            map.put("alreadyMoneyInTotal",alreadyInTotal == null ? 0 : alreadyInTotal);
        }
        return map;
    }*/

    @ResponseBody
    @RequestMapping(value = "/listin", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord")
    public Map<String, Object> queryMoneyInRecordPage(MoneyInRecordPageBean entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        return moneyRecordService.queryMoneyInRecordPage(entity);
    }

    /**
     * 出金管理页面
     * @return
     */
    @RequestMapping(value = "/listout", method = RequestMethod.GET)
    @RequiresPermissions("moneyrecord")
    public String listOutPage() {
        return "/moneyrecord/moneyrecordout_list";
    }
    /**
     * 出金列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listout", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord")
    public Map<String,Object> listOutData(MoneyRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        Map<String,Object> map = new HashedMap();
        List<MoneyRecordBean> entityList = moneyRecordService.moneyOutList(entity);
        GridPager g = new GridPager();
        g.setRows(entityList);
        g.setTotal(MybatisPagerInterceptor.getTotal());
        map.put("g",g);
        if(entity.getPage() == 1){
            BigDecimal moneyOutTotal = moneyRecordService.moneyOutTotal(entity);
            BigDecimal noMoneyOutTotal = moneyRecordService.noMoneyOutTotal(entity);
            BigDecimal alreadyOutTotal = moneyRecordService.alreadyMoneyOutTotal(entity);
            map.put("moneyOutTotal", moneyOutTotal == null ? 0 : moneyOutTotal);
            map.put("noMoneyOutTotal", noMoneyOutTotal == null ? 0 : noMoneyOutTotal);
            map.put("alreadyMoneyOutTotal", alreadyOutTotal == null ? 0 : alreadyOutTotal);
        }
        return map;
    }




    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("moneyrecord:add")
    public String addPage() {
         return "/moneyrecord/moneyrecord_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("moneyrecord:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", moneyRecordService.get(id));
        return "/moneyrecord/moneyrecord_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord")
    public GridPager listData(MoneyRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<MoneyRecordBean> entityList = moneyRecordService.queryList(entity);
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
    @RequiresPermissions("moneyrecord:add")
    public ExecuteResult save(@Valid MoneyRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        moneyRecordService.insert(entity);
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
    @RequiresPermissions("moneyrecord:edit")
    public ExecuteResult update(@Valid MoneyRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        moneyRecordService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord:delete")
    public ExecuteResult delete(String id) {
        moneyRecordService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
    //入金数据导出
    @RequestMapping("exportExcelin")
    public void exportExcel(MoneyRecordBean bean, HttpServletResponse response){
        HSSFWorkbook wb = moneyRecordService.exportExcel(bean);
        ExcelUtils.exportExcel("入金明细", response, wb);
    }
    //出金数据导出
    @RequestMapping("exportExcelout")
    public void exportExcelout(MoneyRecordBean bean, HttpServletResponse response){
        HSSFWorkbook wb = moneyRecordService.exportExcelout(bean,null);
        ExcelUtils.exportExcel("出金明细", response, wb);
    }

    /**
     * TODO 导出,出金审核通过的数据
     */
    @RequestMapping(value = "exportPassExcel")
    public void exportPassExcel(MoneyRecordBean bean, HttpServletResponse response){
        HSSFWorkbook workbook = moneyRecordService.exportExcelout(bean,1);
        ExcelUtils.exportExcel("出金明细", response, workbook);
    }

    /**
     * 出金 审核不通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/past", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord:out:past")
    public ExecuteResult outPast(String id, Integer s, String failureReason) {
        return moneyRecordService.outPast(id, s, failureReason);
    }
    /**
     * 出金 审核 通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/pasmtin", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord:out:pastpastin")
    public ExecuteResult outPast(String id, Integer s) {
        return moneyRecordService.outPastIn(id, s);
    }

    /**
     * 出金 审核 通过时 线下出金
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/aLaPassIn", method = RequestMethod.POST)
    @RequiresPermissions("moneyrecord:out:pastpastin")
    public String aLaoutPast(String ids, Integer s) {
        return moneyRecordService.passIn(ids, s);
    }

    @ResponseBody
    @RequestMapping(value = "/exportAlaExcel", method = RequestMethod.GET)
    public void exportTxt(String ids, HttpServletResponse response){
        HSSFWorkbook wb = moneyRecordService.alaPayExcel(ids);
        ExcelUtils.exportExcel("预线下代付名单".concat(DateUtils.getDate("yyyyMMddHHmmss")), response, wb);
    }

}
