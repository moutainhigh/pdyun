package com.rmkj.microcap.modules.cashcoupon.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.common.modules.sys.service.SysDictService;
import com.rmkj.microcap.modules.cashcoupon.entity.CashCouponBean;
import com.rmkj.microcap.modules.cashcoupon.service.CashCouponService;
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
@RequestMapping("/cashcoupon")
public class CashCouponController extends BaseController {
    @Autowired
    private CashCouponService cashCouponService;
    @Autowired
    private SysDictService sysDictService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("cashcoupon")
    public String listPage() {
        return "/cashcoupon/cashcoupon_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("cashcoupon:add")
    public String addPage() {
        List<SysDictBean> entityList = cashCouponService.dictList();
        request.setAttribute("list",entityList);
        return "/cashcoupon/cashcoupon_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("cashcoupon:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", cashCouponService.get(id));
        return "/cashcoupon/cashcoupon_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("cashcoupon")
    public GridPager listData(CashCouponBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<CashCouponBean> entityList = cashCouponService.queryList(entity);
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
    @RequiresPermissions("cashcoupon:add")
    public ExecuteResult save(@Valid CashCouponBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        cashCouponService.insert(entity);
        System.out.println(entity.getType());
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
    @RequiresPermissions("cashcoupon:edit")
    public ExecuteResult update(@Valid CashCouponBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        cashCouponService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("cashcoupon:delete")
    public ExecuteResult delete(String id) {
        cashCouponService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
