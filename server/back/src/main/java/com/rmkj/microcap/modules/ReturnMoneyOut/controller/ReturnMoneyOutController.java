package com.rmkj.microcap.modules.ReturnMoneyOut.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.DateUtils;
import com.rmkj.microcap.common.utils.ExcelUtils;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.ReturnMoneyOut.service.ReturnMoneyOutService;
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
* Created by Administrator on 2016-12-7.
*/
@Controller
@RequestMapping("/ReturnMoneyOut")
public class ReturnMoneyOutController extends BaseController {
    @Autowired
    private ReturnMoneyOutService returnMoneyOutService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("ReturnMoneyOut")
    public String listPage() {
        return "/ReturnMoneyOut/ReturnMoneyOut_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("ReturnMoneyOut:add")
    public String addPage() {
         return "/ReturnMoneyOut/ReturnMoneyOut_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("ReturnMoneyOut:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", returnMoneyOutService.get(id));
        return "/ReturnMoneyOut/ReturnMoneyOut_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("ReturnMoneyOut")
    public GridPager listData(ReturnMoneyOutBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<ReturnMoneyOutBean> entityList = returnMoneyOutService.queryList(entity);
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
    @RequiresPermissions("ReturnMoneyOut:add")
    public ExecuteResult save(@Valid ReturnMoneyOutBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        returnMoneyOutService.insert(entity);
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
    @RequiresPermissions("ReturnMoneyOut:edit")
    public ExecuteResult update(@Valid ReturnMoneyOutBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        returnMoneyOutService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("ReturnMoneyOut:delete")
    public ExecuteResult delete(String id) {
        returnMoneyOutService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 提现申请审核不通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/past", method = RequestMethod.POST)
    @RequiresPermissions("ReturnMoneyOut:out:past")
    public ExecuteResult outPast(String id, Integer s,String failureReason) {
        return returnMoneyOutService.outPast(id, s,failureReason);
    }
    /**
     * 提现申请 审核 通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/pastin", method = RequestMethod.POST)
    @RequiresPermissions("ReturnMoneyOut:out:pastin")
    public ExecuteResult outPast1(String id, Integer s) {
        return returnMoneyOutService.outPastIn(id, s);
    }

    /**
     * 提现申请 审核 通过时
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/alaPass", method = RequestMethod.POST)
    @RequiresPermissions("ReturnMoneyOut:out:pastin")
    public String returnMoneyOutPass(String ids, Integer s) {
        return returnMoneyOutService.returnMoneyOutPassIn(ids, s);
    }
    //详细信息展示
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(ReturnMoneyOutBean bean, Map<String,Object> model){
        model.put("model", returnMoneyOutService.get(bean.getId()));
        return "/ReturnMoneyOut/ReturnMoneyOut_info";
    }

    @ResponseBody
    @RequestMapping(value = "/exportAlaExcel", method = RequestMethod.GET)
    public void exportTxt(String ids, HttpServletResponse response){
        HSSFWorkbook wb = returnMoneyOutService.alaPayExcel(ids);
        ExcelUtils.exportExcel("返佣预线下代付名单".concat(DateUtils.getDate("yyyyMMddHHmmss")), response, wb);
    }
}
