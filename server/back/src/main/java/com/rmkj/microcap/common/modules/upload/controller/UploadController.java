package com.rmkj.microcap.common.modules.upload.controller;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.modules.upload.bean.UploadFile;
import com.rmkj.microcap.common.modules.upload.service.UploadService;
import com.rmkj.microcap.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangbowen on 2015/6/24.图片
 */
@Controller
@RequestMapping(value = "/upload", method = RequestMethod.POST)
public class UploadController extends BaseController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传
     *
     * @return Object
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "")
    public UploadFile upload(MultipartHttpServletRequest request, boolean useDefaultName) throws IOException {
        return this.uploadService.uploadFile(request, useDefaultName);
    }

    /**
     * 图片上传
     *
     * @return Object
     */
    @RequestMapping(value = "/ckeditor")
    public void ckeditorUpload(MultipartHttpServletRequest request, PrintWriter printWriter, HttpServletRequest servletRequest) {
        // 图片上传
        try {
            UploadFile uploadFileBean = this.uploadService.uploadFile(request,false);
            String CKEditorFuncNum = servletRequest.getParameter("CKEditorFuncNum");
            // 服务地址根路径
            String rootUrl = Utils.rootUrl();

            printWriter.println("<script type=\"text/javascript\">");
            printWriter.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + rootUrl.concat("/".concat(uploadFileBean.getUrl())) + "','')");
            printWriter.println("</script>");
        } catch (Exception e) {
            printWriter.print("<font color=\"red\"size=\"2\">上传失败</font>");
        } finally {
            printWriter.flush();
            printWriter.close();
        }

    }

    /**
     * 图片上传删除
     *
     * @return Object
     */
    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete() {
        return true;
    }
}
