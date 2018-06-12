package com.rmkj.microcap.modules.index.controller.wap;

import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.modules.index.entity.Broadcast;
import com.rmkj.microcap.modules.index.service.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by renwp on 2016/10/18.
 */
@Controller
@RequestMapping("${wap}/index")
public class IndexController {
    @Autowired
    private BroadcastService broadcastService;

    @RequestMapping(value = "/broadcastDetail/{id}", method = RequestMethod.GET)
    @LoginAnnot
    public String broadcastDetail(@PathVariable String id, Model model){
        Broadcast broadcast = broadcastService.findById(id);
        model.addAttribute("model", broadcast);
        return "/wap/index/broadcast";
    }

    @RequestMapping(value = "/center")
    @LoginAnnot
    public String index(Model model){
        String userId = AuthContext.getUserId();
        List<Broadcast> list =  broadcastService.findList();
        model.addAttribute("data",list);
        return "/wap/center/center";
    }

    @RequestMapping(value = "/center/content/{id}", method = RequestMethod.GET)
    @LoginAnnot
    public String content(@PathVariable String id, Model model){
        Broadcast broadcast = broadcastService.findById(id);
        model.addAttribute("data", broadcast);
        return "/wap/center/content";
    }

    @RequestMapping(value = "/broadcast/list", method = RequestMethod.GET)
    @LoginAnnot
    public String toBroadcast(Model model){
        return "/wap/index/index";
    }
}
