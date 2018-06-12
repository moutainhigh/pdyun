package com.rmkj.microcap.modules.agentreviewrecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.agentreviewrecord.entity.AgentReviewRecordBean;
import com.rmkj.microcap.modules.agentreviewrecord.service.AgentReviewRecordService;
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
* Created by Administrator on 2016-11-7.
*/
@Controller
@RequestMapping("/agentreviewrecord")
public class AgentReviewRecordController extends BaseController {
    @Autowired
    private AgentReviewRecordService agentReviewRecordService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("agentreviewrecord")
    public String listPage() {
        return "/agentreviewrecord/agentreviewrecord_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("agentreviewrecord:add")
    public String addPage() {
         return "/agentreviewrecord/agentreviewrecord_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("agentreviewrecord:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", agentReviewRecordService.get(id));
        return "/agentreviewrecord/agentreviewrecord_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("agentreviewrecord")
    public GridPager listData(AgentReviewRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentReviewRecordBean> entityList = agentReviewRecordService.queryList(entity);
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
    @RequiresPermissions("agentreviewrecord:add")
    public ExecuteResult save(@Valid AgentReviewRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentReviewRecordService.insert(entity);
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
    @RequiresPermissions("agentreviewrecord:edit")
    public ExecuteResult update(@Valid AgentReviewRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentReviewRecordService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("agentreviewrecord:delete")
    public ExecuteResult delete(String id) {
        agentReviewRecordService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
