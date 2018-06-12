package com.rmkj.microcap.modules.weChatPublic.service;

import com.rmkj.microcap.common.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by renwp on 2017/3/14.
 */
@Service
public class PicService {

    public String upload(String type, MultipartFile file){
        FileOutputStream fos = null;
        String fileName = Utils.uuid();
        try {
            byte[] bytes = file.getBytes();
            File it = new File(Utils.picPath(type));
            if(!it.exists()){
                it.mkdirs();
            }
            it = new File(Utils.picPath(type), fileName.concat(".jpg"));
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
        return Utils.rootUrl().concat("/").concat(Utils.picUrl(type, fileName));
    }
}
