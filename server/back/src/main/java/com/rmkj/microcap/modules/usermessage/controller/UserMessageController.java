package com.rmkj.microcap.modules.usermessage.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import com.rmkj.microcap.modules.usermessage.service.UserMessageService;
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
* Created by Administrator on 2016-11-4.
*/
@Controller
@RequestMapping("/usermessage")
public class UserMessageController extends BaseController {
    @Autowired
    private UserMessageService userMessageService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("usermessage")
    public String listPage() {
        return "/usermessage/usermessage_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("usermessage:add")
    public String addPage() {
         return "/usermessage/usermessage_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("usermessage:edit")
    public String editPage(String id,Map<String,Object> model) {
        model.put("model", userMessageService.get(id));
        return "/usermessage/usermessage_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("usermessage")
    public GridPager listData(UserMessageBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<UserMessageBean> entityList = userMessageService.queryList(entity);
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
    @RequiresPermissions("usermessage:add")
    public ExecuteResult save(@Valid UserMessageBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userMessageService.insert(entity);
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
    @RequiresPermissions("usermessage:edit")
    public ExecuteResult update(@Valid UserMessageBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        userMessageService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("usermessage:delete")
    public ExecuteResult delete(String id) {
        userMessageService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
