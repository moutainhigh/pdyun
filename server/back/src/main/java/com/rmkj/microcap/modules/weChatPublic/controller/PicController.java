package com.rmkj.microcap.modules.weChatPublic.controller;

import com.rmkj.microcap.common.bean.ExecuteResult;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.weChatPublic.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by renwp on 2017/3/14.
 */
@Controller
@RequestMapping("/pic")
public class PicController {

    @Autowired
    private PicService picService;

    @RequestMapping(value = "/${type}/upload", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult upload(@PathVariable String type, @RequestParam("file") MultipartFile file){
        return new ExecuteResult(StatusCode.OK, picService.upload(type, file));
    }

    @RequestMapping(value = "/{type}/{id}/show", method = RequestMethod.GET)
    public void show(@PathVariable String type, @PathVariable String id, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        FileInputStream fis = null;
        try {
            response.setContentType("image/jpg");
            outputStream = response.getOutputStream();
            File it = new File(ProjectConstants.PIC_PATH.concat(File.separator).concat(type), id.concat(".jpg"));
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
