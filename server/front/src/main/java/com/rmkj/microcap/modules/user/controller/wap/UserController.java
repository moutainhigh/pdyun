package com.rmkj.microcap.modules.user.controller.wap;

import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.PageEntity;
import com.rmkj.microcap.common.bean.PagerBean;
import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.modules.index.service.RootService;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.user.entity.BankCard;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.entity.UserMessage;
import com.rmkj.microcap.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by renwp on 2016/10/20.
 */
@Controller
@RequestMapping("${wap}/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RootService rootService;

    @RequestMapping(value = "")
    @LoginAnnot
    public String index(Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        rootService.countNewMessage(model);
        return "wap/homepage";
    }

    @RequestMapping(value = "/tradeList")
    @LoginAnnot
    public String tradeList(PageEntity pageEntity, Model model){
        pageEntity.setStart(0);
        pageEntity.setRows(4);
        PagerBean<Trade> pb = userService.tradeList(pageEntity);
        model.addAttribute("pb", pb);
        return "wap/tradeList";
    }

    @RequestMapping(value = "/moneyList")
    @LoginAnnot
    public String moneyList(PageEntity pageEntity, Model model){
        pageEntity.setStart(0);
        pageEntity.setRows(5);
        PagerBean<MoneyRecord> pb = userService.moneyRecordList(pageEntity);
        model.addAttribute("pb", pb);
        return "wap/moneyList";
    }

    @RequestMapping(value = "/moneyOutDetail")
    @LoginAnnot
    public String moneyOutDetail(MoneyRecord moneyRecord, Model model){
        moneyRecord = userService.moneyOutInfo(moneyRecord);
        model.addAttribute("moneyRecord", moneyRecord);
        return "wap/moneyOutDetail";
    }

    @RequestMapping(value = "/modifyMobile")
    @LoginAnnot
    public String modifyMobile(Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        return "wap/modifyMobile";
    }

    @RequestMapping(value = "/modifyTradePwd")
    @LoginAnnot
    public String modifyTradePwd(Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        return "wap/user/modifyTradePwd";
    }

    @RequestMapping(value = "/userMessageList")
    @LoginAnnot
    public String userMessageList(Model model){
        UserMessage userMessage = new UserMessage();
        model.addAttribute("pb", userService.messageList(userMessage));
        return "wap/user/userMessageList";
    }

    @RequestMapping(value = "/userMessageDetail")
    @LoginAnnot
    public String userMessageDetail(UserMessage userMessage,Model model){
        userService.readMessage(userMessage.getId());
        model.addAttribute("userDetail", userService.selectUserMsgDetail(userMessage));
        return "wap/user/userMessageDetail";
    }

    @RequestMapping(value = "/bankCard")
    @LoginAnnot
    public String bankCard(Model model){
        model.addAttribute("user", userService.get().getBody());
        BankCard bankCard = userService.findBankCard();
        model.addAttribute("bankCard", bankCard);
        userService.findCheckUtils(model);
        return "wap/user/Withdrawals";
    }

    @RequestMapping(value = "/shareImgOld")
    @LoginAnnot
    public void shareImg(HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream writer = null;
        try {
            response.setContentType("image/jpg");
            writer = response.getOutputStream();
            userService.shareImg(writer);
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    /**
     * TODO 生成带图片的微信二维码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/shareImg")
    @LoginAnnot
    public void getRQCode(HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream write = null;
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "No-cache");
        //BufferedImage bufferedImage = userService.generateRQCodeImage(write);
        BufferedImage bufferedImage = userService.generateRQCode(write);
        try {
            write = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", write);
            write.flush();
            write.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    //提现时忘记密码
    @RequestMapping(value = "/firstPartResetTradePassWordWhenMoneyOut")
    @LoginAnnot
    public String firstPartResetTradePassWordWhenMoneyOut(Model model){
        User user = (User) userService.get().getBody();
        String mobile = user.getMobile();
        model.addAttribute("mobile",mobile);
        return "wap/firstPartResetTradePassWord";
    }

}
