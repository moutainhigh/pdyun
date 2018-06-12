package com.rmkj.microcap.modules.Ml3Agent.controller;

import com.rmkj.microcap.common.modules.sys.utils.UserUtils;
import com.rmkj.microcap.modules.Ml3Agent.entity.Ml3AgentBean;
import com.rmkj.microcap.modules.Ml3Agent.service.CoderService;
import com.rmkj.microcap.modules.Ml3Agent.service.Ml3AgentService;
import com.rmkj.microcap.modules.Ml3Agent.service.ZxingDecoderService;
import com.rmkj.microcap.modules.Ml3MemberUnits.entity.Ml3MemberUnitsBean;
import com.rmkj.microcap.modules.Ml3MemberUnits.service.Ml3MemberUnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by Administrator on 2016/12/28.
 */
@Controller
@RequestMapping("/ZxingCode")
public class ZxingEncoderAction {
    @Autowired
    private CoderService coderService;
    @Autowired
    private ZxingDecoderService zxingDecoderService;
    @Autowired
    private Ml3AgentService ml3AgentService;
    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;
    @RequestMapping(value="/zxingdecode", method= RequestMethod.GET)
    public ModelAndView zxingdecode(@RequestParam("realImgPath") String realImgPath, HttpSession session) {
        String uploadPath = "/images" ;
        String realUploadPath = session.getServletContext().getRealPath(uploadPath) ;
        String imgPath = realUploadPath+"/"+realImgPath ;
        String result = zxingDecoderService.zxingdecode(imgPath) ;

        ModelAndView ret = new ModelAndView() ;
        ret.addObject("result", result) ;
        ret.setViewName("zxingdecode");

        return ret ;
    }

    @RequestMapping(value="/zxingcoder", method=RequestMethod.GET)
    public ModelAndView watermark(HttpSession session) throws Exception {
        System.out.println("====================");
//        String uploadPath = "/images" ;
//        String realUploadPath = session.getServletContext().getRealPath(uploadPath) ;
//        String imageName = "12345"+".png" ;
//        Ml3AgentBean ml3AgentBean = ml3AgentService.get(UserUtils.getUser().getId());
//        Ml3MemberUnitsBean ml3MemberUnitsBean = ml3MemberUnitsService.get(ml3AgentBean.getUnitsId());
//        String twoLevelDomain = ml3MemberUnitsBean.getTwoLevelDomain();//二级域名
//        String userid = UserUtils.getUser().getId();
//        String agentInviteCode = ml3AgentBean.getAgentInviteCode();
//        //生成内容
//        String contents = "http://"+twoLevelDomain+".rongmei.com/front/wap/reg?agentInviteCode="+userid+"&userId="+agentInviteCode;
//        int width = 300;
//        int height = 300;
//        String zxingImage = coderService.encode(contents, width, height, uploadPath, realUploadPath, imageName);
//
        ModelAndView ret = new ModelAndView() ;
//        ret.addObject("imageUrl", zxingImage) ;
//        ret.addObject("imageName", imageName) ;
//        ret.setViewName("zxingcoder");

        return ret ;
    }
}
