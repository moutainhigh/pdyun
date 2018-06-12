package com.rmkj.microcap.modules.weChatPublic.controller;

import com.rmkj.microcap.modules.weChatPublic.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by renwp on 2017/3/14.
 */
@Controller
@RequestMapping("/weChatPublic/msgsend")
public class MessageSendController {

    @Autowired
    private MessageSendService messageSendService;

    /**
     * 推送图文消息
     * @return
     */
    @RequestMapping("/toAll")
    @ResponseBody
    public String toAll(String wechatPublicId, String type, String content){
        return messageSendService.toAll(wechatPublicId, type, content);
    }

}
