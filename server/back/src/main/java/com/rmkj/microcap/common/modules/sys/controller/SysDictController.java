package com.rmkj.microcap.common.modules.sys.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.bean.SysDictBean;
import com.rmkj.microcap.common.modules.sys.service.SysDictService;
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
 * Created by zhangbowen on 2015-8-11.
 * 字典
 */
@Controller
@RequestMapping("/dic")
public class SysDictController extends BaseController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/dic/sysdict_list";
    }

    /**
     * 新增页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage() {
        return "/dic/dic_add";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Map<String, Object> model) {
        model.put("model", sysDictService.get(id));
        return "/dic/dic_edit";
    }

    /**
     * 列表数据
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(SysDictBean entity) {
        List<SysDictBean> entityList = sysDictService.queryList(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }

    /**
     * 保存
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid SysDictBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysDictService.insert(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 更新
     *
     * @param entity
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExecuteResult update(@Valid SysDictBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        sysDictService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ExecuteResult delete(String id) {
        sysDictService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
