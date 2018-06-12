package com.rmkj.microcap.common.constants;


import com.rmkj.microcap.common.bean.annotation.Config;

/**
 * Created by zhangbowen on 2016/5/11.
 * 配置文件常量,spring启动时注入值
 */
public class ProjectConstants {


    @Config("project_name")
    public static String PROJECT_NAME;

    //项目是否为debug模式
    @Config("project_debug")
    public static boolean PRO_DEBUG;
    //短信是否为debug模式
    @Config("sms_debug")
    public static boolean SMS_DEBUG;

    @Config("wei_xin_app_id")
    public static String WE_XIN_APP_ID;
    @Config("wei_xin_secret")
    public static String WE_XIN_SECRET;

    //威鹏支付
    //微信扫码下单异步通知地址
    @Config("weipeng_qrcode_pay_asyncresult_url")
    public static String WEIPENG_QRCODE_PAY_ASYNCRESULT_URL;
    //代付出金异步通知地址
    @Config("weipeng_dai_pay_asyncresult_url")
    public static String WEIPENG_DAI_PAY_ASYNCRESULT_URL;
    //代付收益出金异步通知地址
    @Config("weipeng_dai_pay_asyncresult_returnmoney_url")
    public static String WEIPENG_DAI_PAY_ASYNCRESULT_RETURNMONEY_URL;
    //商户号
    @Config("weipeng_pay_merchant_no")
    public static String WEIPENG_PAY_MERCHANT_NO;
    //秘钥
    @Config("weipeng_pay_secret")
    public static String WEIPENG_PAY_SECRET;
    //端口号
    @Config("weipeng_pay_port")
    public static String WEIPENG_PAY_PORT;

    @Config("qrcode_path")
    public static String QRCODE_PATH;

    @Config("pic_path")
    public static String PIC_PATH;


    public static interface PIC_PATH_TYPE {
        String WECHAT_PUBLIC_ARTICLE_PIC_URL = "article_pic_url";
        String CKEDITOR = "ckeditor";
    }

    public interface WEI_XIN_MESSAGE_CUSTOM_SEND_TYPE {
        String TU_WEN = "tuwen";
    }

    public interface WEI_XIN_MESSAGE_CUSTOM_TYPE {
        String GUAN_ZHU = "0";
        String TI_XIAN = "1";
        String FAN_YONG = "2";
        String YOU_HUI_QUAN = "3";
    }

    @Config("wei_fu_tong_url")
    public static String WEI_FU_TONG_URL;
    @Config("wei_fu_tong_no")
    public static String WEI_FU_TONG_NO;
    @Config("wei_fu_tong_name")
    public static String WEI_FU_TONG_NAME;
    @Config("wei_fu_tong_key")
    public static String WEI_FU_TONG_KEY;
    @Config("wei_fu_tong_private_key_cert")
    public static String WEI_FU_TONG_PRIVATE_KEY_CERT;
    @Config("wei_fu_tong_public_key_cert")
    public static String WEI_FU_TONG_PUBLIC_KEY_CERT;
    @Config("wei_fu_tong_business_code")
    public static String WEI_FU_TONG_BUSINESS_CODE;

    @Config("money_out_ytb_base_url")
    public static String MONEY_OUT_YTB_BASE_URL;
    @Config("money_out_ytb_cardno")
    public static String MONEY_OUT_YTB_CARDNO;
    @Config("money_out_ytb_secret")
    public static String MONEY_OUT_YTB_SECRET;

    @Config("guofu_key")
    public static String GUOFU_KEY;
    @Config("guofu_mech_no")
    public static String GUOFU_MECH_NO;
    @Config("guofu_vr_no")
    public static String GUOFU_VR_NO;
    @Config("guofu_url")
    public static String GUOFU_URL;
    @Config("guofu_query_url")
    public static String GUOFU_QUERY_URL;

    //皇冠代付
    @Config("crown_url")
    public static String CROWN_URL;
    @Config("crown_mer_id")
    public static String CROWN_MER_ID;
    @Config("crown_key")
    public static String CROWN_KEY;
    @Config("crown_notify_url")
    public static String CROWN_NOTIFY_URL;
    @Config("crown_notify_return_money_url")
    public static String CROWN_NOTIFY_RETURN_MONEY_URL;
}
