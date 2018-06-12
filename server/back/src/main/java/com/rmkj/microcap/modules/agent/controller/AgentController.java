package com.rmkj.microcap.modules.agent.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.agent.entity.AgentBean;
import com.rmkj.microcap.modules.agent.service.AgentService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.modules.agentreviewrecord.entity.AgentReviewRecordBean;
import com.rmkj.microcap.modules.agentuser.entity.AgentUserBean;
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
* Created by Administrator on 2016-11-4.
*/
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {
    @Autowired
    private AgentService agentService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("agent")
    public String listPage() {
        return "/agent/agent_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("agent:add")
    public String addPage() {
         return "/agent/agent_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("agent:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", agentService.get(id));
        return "/agent/agent_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("agent")
    public GridPager listData(AgentBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentBean> entityList = agentService.queryList(entity);
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
    @RequiresPermissions("agent:add")
    public ExecuteResult save(@Valid AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentService.insert(entity);
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
    @RequiresPermissions("agent:edit")
    public ExecuteResult update(@Valid AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("agent:delete")
    public ExecuteResult delete(String id) {
        agentService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }

    /**
     * 经纪人用户列表页面
     * @return
     */
    @RequestMapping(value = "/getAgentUser", method = RequestMethod.GET)
    public String agentUserPage(String id, Map<String, Object> model){
        model.put("id", id);
        return "/agent/agentUser_list";
    }
    /**
     * 列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAgentUser", method = RequestMethod.POST)
    public GridPager agentUserData(AgentUserBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentUserBean> entityList = agentService.getAgentUser(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }


    /**
     * 审核列表页面
     * @return
     */
    @RequestMapping(value = "/getAgentReview", method = RequestMethod.GET)
    public String agentReviewPage(String id, Map<String, Object> model){
        model.put("id", id);
        return "/agent/agentreviewrecord_list";
    }
    /**
     * 列表数据
     * @param entity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAgentReview", method = RequestMethod.POST)
    public GridPager agentReviewData(AgentReviewRecordBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<AgentReviewRecordBean> entityList = agentService.getAgentReview(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(entityList);
        return g;
    }



    /**
     * 经纪人 审核不通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/past", method = RequestMethod.POST)
    @RequiresPermissions("agent:out:past")
    public ExecuteResult outPast(String id, Integer s,String failureReason) {
        return agentService.outPast(id, s,failureReason);
    }
    /**
     * 经纪人 审核 通过时
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/out/pastin", method = RequestMethod.POST)
    @RequiresPermissions("agent:out:pastin")
    public ExecuteResult outPast1(String id, Integer s) {
        return agentService.outPastIn(id, s);
    }

    //开启用户
    @ResponseBody
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @RequiresPermissions("agent")
    public ExecuteResult open(AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentService.open(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    //关闭合约
    @ResponseBody
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @RequiresPermissions("agent")
    public ExecuteResult close(AgentBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        agentService.close(entity);
        return new ExecuteResult(StatusCode.OK);
    }
}
