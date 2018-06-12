package com.rmkj.microcap.modules.weChatPublic.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.GridPager;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.modules.weChatPublic.entity.WeChatPublic;
import com.rmkj.microcap.modules.weChatPublic.service.WeChatPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by renwp on 2017/2/11.
 */
@Controller
@RequestMapping("/weChatPublic")
public class WeChatPublicController extends BaseController {

    @Autowired
    private WeChatPublicService weChatPublicService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(){
        return "/weChatPublic/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public GridPager list(WeChatPublic entity){
        entity.setStart(evalStart(entity.getPage(), entity.getRows()));
        List<WeChatPublic> list = weChatPublicService.list(entity);
        GridPager g = new GridPager();
        g.setTotal(MybatisPagerInterceptor.getTotal());
        g.setRows(list);
        return g;
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public List<WeChatPublic> all(WeChatPublic entity){
        entity.setRows(10000);
        entity.setStart(0);
        return weChatPublicService.list(entity);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(String id, Model model){
        WeChatPublic it = weChatPublicService.findById(id);
        if(it == null){
            it = new WeChatPublic();
            it.setStatus("0");
        }
        model.addAttribute("it", it);
        return "/weChatPublic/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult save(@Valid WeChatPublic weChatPublic, Errors errors){
        if (errors.hasErrors()) {
            return parseErrors(errors);
        }
        weChatPublicService.save(weChatPublic);
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/uploadQrcode", method = RequestMethod.GET)
    public String uploadQrcodePage(String id, Model model){
        model.addAttribute("id", id);
        return "/weChatPublic/upload";
    }

    @RequestMapping(value = "/uploadQrcode", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult uploadQrcode(@RequestParam("file") MultipartFile file, String id){
        FileOutputStream fos = null;
        try {
            byte[] bytes = file.getBytes();
            File it = new File(ProjectConstants.QRCODE_PATH, id.concat(".jpg"));
            fos = new FileOutputStream(it);
            fos.write(bytes);
            fos.close();
            fos = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ExecuteResult(StatusCode.OK);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public String del(String id){
        weChatPublicService.del(id);
        return "操作成功！";
    }

    @RequestMapping(value = "/showQrcode", method = RequestMethod.GET)
    public void showQrcode(String id, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        FileInputStream fis = null;
        try {
            response.setContentType("image/jpg");
            outputStream = response.getOutputStream();
            File it = new File(ProjectConstants.QRCODE_PATH, id.concat(".jpg"));
            if(it.exists()){
                fis = new FileInputStream(it);
                byte[] bb = new byte[1024];
                int read = -1;
                while((read = fis.read(bb)) != -1){
                    outputStream.write(bb, 0, read);
                }

                fis.close();
                fis = null;
            }
            outputStream.flush();
            outputStream.close();

            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
