package com.rmkj.microcap.modules.weiXinMenu.controller;

import com.alibaba.fastjson.JSONObject;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.modules.weixin.http.interceptor.WeiXinInterceptor;
import com.rmkj.microcap.modules.weiXinMenu.entity.MediaBean;
import com.rmkj.microcap.modules.weiXinMenu.entity.WeixinMenuBean;
import com.rmkj.microcap.modules.weiXinMenu.service.WeixinMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
* Created by zhangbowen on 2016-6-7.
*/
@Controller
@RequestMapping("/weiXinMenu")
public class WeixinMenuController extends BaseController {
    @Autowired
    private WeixinMenuService weixinMenuService1;

    /**
    * 列表页面
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPage() {
        return "/weiXinMenu/weiXinMenu_list";
    }
    /**
    * 新增页面
    * @return
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(String appId, Model model)
    {
        WeiXinInterceptor.setAppId(appId);
        List<WeixinMenuBean> weiXinMenuBean1s = weixinMenuService1.query();
        for(int i = 0;i<weiXinMenuBean1s.size();i++){
            if(weiXinMenuBean1s.get(i).getParentName() != null){
                weiXinMenuBean1s.remove(i);
                i--;
            }
        }
        request.setAttribute("list",weiXinMenuBean1s);
        model.addAttribute("appId", appId);
        return "/weiXinMenu/weiXinMenu_add";
    }

    /**
    * 列表数据
    * @param entity
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GridPager listData(WeixinMenuBean entity, String appId) {
        WeiXinInterceptor.setAppId(appId);
        List<WeixinMenuBean> entityList = weixinMenuService1.query();
        GridPager g = new GridPager();
        g.setTotal(entityList.size());
        g.setRows(entityList);
        return g;
    }

    /**
     * 编辑页面
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(Model model, String appId) throws UnsupportedEncodingException {
        WeiXinInterceptor.setAppId(appId);

        String name = request.getParameter("name");
        if(StringUtils.isNotBlank(name)){
            name=java.net.URLDecoder.decode(name, "UTF-8");
        }
        model.addAttribute("name",name);
        String url = request.getParameter("url");
        if(StringUtils.isNotBlank(url)){
            url=java.net.URLDecoder.decode(url, "UTF-8");
        }
        model.addAttribute("url",url);
        String parentName = request.getParameter("parentName");
        if(StringUtils.isNotBlank(parentName)){
            parentName=java.net.URLDecoder.decode(parentName, "UTF-8");
        }
        model.addAttribute("parentName",parentName);
        String key = request.getParameter("key");
        if(StringUtils.isNotBlank(key)){
            key=java.net.URLDecoder.decode(key, "UTF-8");
        }
        model.addAttribute("key",key);
        String mediaId = request.getParameter("mediaId");
        if(StringUtils.isNotBlank(mediaId)){
            mediaId=java.net.URLDecoder.decode(mediaId, "UTF-8");
        }
        model.addAttribute("mediaId",mediaId);
        String content = request.getParameter("content");
        if(StringUtils.isNotBlank(content)){
            content=java.net.URLDecoder.decode(content, "UTF-8");
        }
        model.addAttribute("content",content);
        String type = request.getParameter("type");
        if(StringUtils.isNotBlank(type)){
            type=java.net.URLDecoder.decode(type, "UTF-8");
        }
        model.addAttribute("type",type);

        model.addAttribute("appId", appId);
        return "/weiXinMenu/weiXinMenu_edit";
    }

    /**
     * 保存
     * @param
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ExecuteResult save(@Valid WeixinMenuBean entity, String appId, Errors errors) {
        WeiXinInterceptor.setAppId(appId);

        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", entity.getName());

        if(StringUtils.isNotBlank(entity.getContent())){
            jsonObject.put("content", entity.getContent());
        }
        if(StringUtils.isNotBlank(entity.getMediaId())){
            jsonObject.put("media_id", entity.getMediaId());
        }
        if(StringUtils.isNotBlank(entity.getUrl())){
            jsonObject.put("url", entity.getUrl());
        }

        if(StringUtils.isBlank(entity.getType())){
            jsonObject.put("type", "view");
        }else{
            jsonObject.put("type", entity.getType());
        }

        if(!weixinMenuService1.create(jsonObject, "add", entity.getParentName())){
            return new ExecuteResult(StatusCode.FAILED);
        }
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
    public ExecuteResult update(@Valid WeixinMenuBean entity, String appId, Errors errors) throws Exception {
        WeiXinInterceptor.setAppId(appId);

        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("id");
        String newName = entity.getName();
        jsonObject.put("name", name);
        // 修改名字
        jsonObject.put("new_name", newName);

        if(StringUtils.isNotBlank(entity.getContent())){
            jsonObject.put("content", entity.getContent());
        }
        if(StringUtils.isNotBlank(entity.getMediaId())){
            jsonObject.put("media_id", entity.getMediaId());
        }
        if(StringUtils.isNotBlank(entity.getUrl())){
            jsonObject.put("url", entity.getUrl());
        }

        if(StringUtils.isBlank(entity.getType())){
            jsonObject.put("type", "view");
        }else{
            jsonObject.put("type", entity.getType());
        }

        if(!weixinMenuService1.create(jsonObject, "update", entity.getParentName())){
            return new ExecuteResult(StatusCode.FAILED);
        }
        return new ExecuteResult(StatusCode.OK);
    }
    /**
    * 删除
    * @param
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ExecuteResult delete(String name1, String parentName, String appId) throws Exception{
        WeiXinInterceptor.setAppId(appId);

        if(StringUtils.isNotBlank(name1)){
            name1=java.net.URLDecoder.decode(name1, "UTF-8");
        }
        if(StringUtils.isNotBlank(parentName)){
            parentName=java.net.URLDecoder.decode(parentName, "UTF-8");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name1);
        weixinMenuService1.create(jsonObject,"delete", parentName);

        return new ExecuteResult(StatusCode.OK);
    }

    /**
     *
     */
    @ResponseBody
    @RequestMapping(value = "/queryMedia", method = RequestMethod.GET)
    public List<MediaBean> queryMedia(String type, String appId){
        WeiXinInterceptor.setAppId(appId);
        return weixinMenuService1.queryMedias(type, 0, 100).getRows();
    }
}
