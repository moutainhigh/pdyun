package com.rmkj.microcap.common.modules.version.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.version.entity.VersionBean;
import com.rmkj.microcap.common.modules.version.service.VersionService;
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
 * Created by zhangbowen on 2015-12-25.
 */
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {
    @Autowired
    private VersionService versionService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/version/version_list";
    }

    /**
     * 新增页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage() {
        return "/version/version_add";
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
        model.put("model", versionService.get(id));
        return "/version/version_edit";
    }

    /**
     * 列表数据
     *
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(VersionBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<VersionBean> entityList = versionService.queryList(entity);
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
    public ExecuteResult save(@Valid VersionBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        versionService.insert(entity);
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
    public ExecuteResult update(@Valid VersionBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        versionService.update(entity);
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
        versionService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
