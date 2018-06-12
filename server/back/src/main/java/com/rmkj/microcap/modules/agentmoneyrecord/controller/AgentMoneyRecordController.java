package com.rmkj.microcap.modules.agentmoneyrecord.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.agentmoneyrecord.entity.AgentMoneyRecordBean;
import com.rmkj.microcap.modules.agentmoneyrecord.service.AgentMoneyRecordService;
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
@RequestMapping("/agentmoneyrecord")
public class AgentMoneyRecordController extends BaseController {
    @Autowired
    private AgentMoneyRecordService agentMoneyRecordService;
    //详细信息展示
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(AgentMoneyRecordBean bean, Map<String,Object> model){
        model.put("model", agentMoneyRecordService.get(bean.getId()));
        return "/agentmoneyrecord/agentmoneyrecord_info";
    }
    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneyrecord")
    public String listPage() {
        return "/agentmoneyrecord/agentmoneyrecord_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneyrecord:add")
    public String addPage() {
         return "/agentmoneyrecord/agentmoneyrecord_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("agentmoneyrecord:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", agentMoneyRecordService.get(id));
        return "/agentmoneyrecord/agentmoneyrecord_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("agentmoneyrecord")
    public GridPager listData(AgentMoneyRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentMoneyRecordBean> entityList = agentMoneyRecordService.queryList(entity);
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
    @RequiresPermissions("agentmoneyrecord:add")
    public ExecuteResult save(@Valid AgentMoneyRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentMoneyRecordService.insert(entity);
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
    @RequiresPermissions("agentmoneyrecord:edit")
    public ExecuteResult update(@Valid AgentMoneyRecordBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentMoneyRecordService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("agentmoneyrecord:delete")
    public ExecuteResult delete(String id) {
        agentMoneyRecordService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 提现申请审核不通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/past", method = RequestMethod.POST)
    @RequiresPermissions("agent:out:past")
    public ExecuteResult outPast(String id, Integer s,String failureReason) {
        return agentMoneyRecordService.outPast(id, s,failureReason);
    }
    /**
     * 提现申请 审核 通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/pastin", method = RequestMethod.POST)
    @RequiresPermissions("agent:out:pastin")
    public ExecuteResult outPast1(String id, Integer s) {
        return agentMoneyRecordService.outPastIn(id, s);
    }

}
