package com.rmkj.microcap.modules.money.controller.wap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.rmkj.microcap.common.base.BaseController;
import com.rmkj.microcap.common.bean.annotation.LoginAnnot;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.money.entity.CashCoupon;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.money.service.CashCouponService;
import com.rmkj.microcap.modules.money.service.MoneyService;
import com.rmkj.microcap.modules.user.entity.User;
import com.rmkj.microcap.modules.user.service.UserService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by renwp on 2016/10/24.
 */
@Controller
@RequestMapping("${wap}/money")
public class MoneyController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private CashCouponService cashCouponService;

    @Autowired
    private MoneyService moneyService;



    @RequestMapping("/out")
    @LoginAnnot
    public String moneyOut(Model model){
        User user = (User) userService.get().getBody();
        model.addAttribute("user", user);
        return userService.validateBankCard(model);
    }

    @RequestMapping(value = "/cashCoupon")
    @LoginAnnot
    public String lookCashCoupon(Model model){
        List<CashCoupon> cashCouponList = cashCouponService.findUserCashCouponByUserId();
        model.addAttribute("cashCouponList",cashCouponList);
        return "/wap/cashCoupon";
    }

    @RequestMapping(value = "/qrcode")
    @LoginAnnot
    public String qrcodePage(String url, Model model) throws UnsupportedEncodingException {
        model.addAttribute("url", URLEncoder.encode(url, "utf-8"));
        return "wap/pay/qrcode";
    }

    /**
     * 二维码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/qrcodeImg")
    @LoginAnnot
    public void getRQCode(HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream writer = null;
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "No-cache");
        String content = request.getParameter("url");// 内容
        int width = 300; // 图像宽度
        int height = 300; // 图像高度
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;// 生成矩阵
        FileOutputStream fos = null;
        File file = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            String filePath = UserService.class.getResource("/").getPath();
            filePath = filePath.substring(0, filePath.length()-1);
            String fileName = Utils.uuid().concat(".jpg");
            file = new File(filePath, fileName);
            fos = new FileOutputStream(file);
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", fos);

            writer = response.getOutputStream();
            Thumbnails.of(file).size(width, height)
                    .watermark(Positions.CENTER, Thumbnails.of(new File(filePath, "logo.jpg")).scale(0.2).asBufferedImage(), 1f)
                    .outputQuality(1f).imageType(1).outputFormat("jpg").toOutputStream(writer);
            writer.flush();
            writer.close();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(file != null){
                file.delete();
            }
        }

    }

    /**
     * 跳转充值记录页面
     * @return
     */
    @RequestMapping(value = "/payRecord")
    @LoginAnnot
    public String payRecord(MoneyRecord moneyRecord, Model model){
        model.addAttribute("list", moneyService.findUserPayMoneyRecord(moneyRecord));
        if("1".equals(moneyRecord.getType())){
            model.addAttribute("type",1);
        }else if("0".equals(moneyRecord.getType())){
            model.addAttribute("type",0);
        }else{
            model.addAttribute("type",null);
        }
        return "wap/pay/payRecord";
    }
}
