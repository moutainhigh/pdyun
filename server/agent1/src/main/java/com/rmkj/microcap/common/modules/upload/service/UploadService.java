package com.rmkj.microcap.common.modules.upload.service;

import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.modules.upload.bean.UploadFile;
import com.rmkj.microcap.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


/**
 * 图片上传
 *
 * @author
 * @since 2015.6.9
 */
@Service
public class UploadService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 文件上传
     *
     * @return
     */
    public UploadFile uploadFile(MultipartHttpServletRequest request, boolean useDefaultName) throws IOException {
        Iterator<String> itr = request.getFileNames();
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            String fileName = mpf.getOriginalFilename();
            if(!useDefaultName){
                fileName = Utils.uuid() + fileName.substring(fileName.indexOf("."));
            }
//            PutObjectRequest putObjectRequest = new PutObjectRequest(ProjectConstants.OSS_BUCKET_NAME, fileName, mpf.getInputStream());
//            return ossService.uploadFile(putObjectRequest);
            // TODO 图片上传 可优化
            File rootFile = new File(Constants.PROJECT_RUN_DIR.concat(Constants.BANNER_DIR));
            if(!rootFile.exists()){
                rootFile.mkdirs();
            }
            File file = new File(rootFile, fileName);

            try(FileOutputStream fos = new FileOutputStream(file);
                InputStream inputStream = mpf.getInputStream();){
                byte[] bb = new byte[1024];
                int read = -1;
                while ((read = inputStream.read(bb)) != -1){
                    fos.write(bb, 0, read);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            UploadFile uploadFile = new UploadFile();
            uploadFile.setUrl(Constants.BANNER_DIR.replace(File.separator, "/").concat("/").concat(fileName));
            return uploadFile;
        }
        return null;
    }

}
