package com.rmkj.microcap.modules.article.controller;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.article.entity.ArticleBean;
import com.rmkj.microcap.modules.article.service.ArticleService;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by Administrator on 2016-10-17.
*/
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("article")
    public String listPage() {
        return "/article/article_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequiresPermissions("article:add")
    public String addPage() {
         return "/article/article_add";
    }
    /**
    * 编辑页面
    * @param id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @RequiresPermissions("article:edit")
    public String editPage(String id,Map<String,Object> model) {
        ArticleBean articleBean =  articleService.get(id);
        model.put("model",articleBean );
//        model.put("_type", JSON.toJSONString(articleBean.getType()));
        return "/article/article_edit";
    }
    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("article")
    public GridPager listData(ArticleBean entity) {
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<ArticleBean> entityList = articleService.queryList(entity);
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
    @RequiresPermissions("article:add")
    public ExecuteResult save(@Valid ArticleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }

        entity.setCreateTime(new Date());
        articleService.insert(entity);
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
    @RequiresPermissions("article:edit")
    public ExecuteResult update(@Valid ArticleBean entity, Errors errors) {
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        System.out.println("标题"+entity.getTitle());
        System.out.println("内容"+entity.getContent());
        articleService.update(entity);
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("article:delete")
    public ExecuteResult delete(String id) {
        articleService.delete(id);
        return new ExecuteResult(StatusCode.OK);
    }
}
