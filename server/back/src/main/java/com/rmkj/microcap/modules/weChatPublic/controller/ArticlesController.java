package com.rmkj.microcap.modules.weChatPublic.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublicArticle;
import com.rmkj.microcap.modules.weChatPublic.service.PicService;
import com.rmkj.microcap.modules.weChatPublic.service.WeChatPublicArticlesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by renwp on 2017/3/14.
 */
@Controller
@RequestMapping("/weChatPublic/articles")
public class ArticlesController extends BaseController {

    @Autowired
    private WeChatPublicArticlesService articlesService;

    @Autowired
    private PicService picService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String toPage(){
        return "/weChatPublic/articles/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public GridPager list(WeChatPublicArticle weChatPublicArticle){
        weChatPublicArticle.setStart(evalStart(weChatPublicArticle.getPage(), weChatPublicArticle.getRows()));
        List<WeChatPublicArticle> list = articlesService.list(weChatPublicArticle);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(list);
        return g;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Model model){
        WeChatPublicArticle it = articlesService.findById(id);
        if(it == null){
            it = new WeChatPublicArticle();
        }
        model.addAttribute("it", it);
        model.addAttribute("type", ProjectConstants.PIC_PATH_TYPE.WECHAT_PUBLIC_ARTICLE_PIC_URL);
        return "/weChatPublic/articles/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult save(@Valid WeChatPublicArticle weChatPublicArticle, String type, @RequestParam(value = "file", required = false) MultipartFile file, Errors errors){
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        if(file != null){
            String picurl = picService.upload(type, file);
            if(StringUtils.isNotBlank(picurl)){
                weChatPublicArticle.setPicurl(picurl);
            }
        }
        articlesService.save(weChatPublicArticle);
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult del(String id){
        articlesService.del(id);
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/look/{id}", method = RequestMethod.GET)
    public String article(@PathVariable String id, Model model){
        WeChatPublicArticle byId = articlesService.findById(id);
        if(byId == null){
            byId = new WeChatPublicArticle();
        }
        model.addAttribute("it", byId);
        return "/weChatPublic/articles/show";
    }

}
