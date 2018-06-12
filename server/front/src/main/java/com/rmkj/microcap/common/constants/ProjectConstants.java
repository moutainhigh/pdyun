package com.rmkj.microcap.common.constants;

import com.rmkj.microcap.common.bean.annotation.Config;

/**
 * Created by zhangbowen on 2016/5/11.
 * 配置文件常量,spring启动时注入值
 */
public class ProjectConstants {
    @Config("v")
    public static String V;
    @Config("wap")
    public static String WAP;
    @Config("wap_agent")
    public static String WAP_AGENT;
    @Config("api")
    public static String API;

    @Config("project_name")
    public static String PROJECT_NAME;

    //项目是否为debug模式
    @Config("project_debug")
    public static boolean PRO_DEBUG;
    //短信是否为debug模式
    @Config("sms_debug")
    public static boolean SMS_DEBUG;
    //行情是否为debug模式
    @Config("market_debug")
    public static boolean MARKET_DEBUG;
    //会员二级域名是否为debug模式
    @Config("two_level_domain_debug")
    public static boolean TWO_LEVEL_DOMAIN_DEBUG;
    //会员二级域名结尾
    @Config("two_level_domain")
    public static String TWO_LEVEL_DOMAIN;
    @Config("wei_xin_login")
    public static boolean WEI_XIN_LOGIN;
    //预警系统开关
    @Config("warning_debug")
    public static boolean WARNING_DEBUG;

    // 是否支持三级营销系统
    @Config("three_sale_sys")
    public static boolean THREE_SALE_SYS;

    @Config("wei_xin_app_id")
    public static String WEI_XIN_APP_ID;
    @Config("wei_xin_secret")
    public static String WEI_XIN_SECRET;

    //回调用
    @Config("wei_xin_token")
    public static String WEI_XIN_TOKEN;

    @Config("wei_xin_page_access_url")
    public static String WEI_XIN_PAGE_ACCESS_URL;

    @Config("wei_xin_redirect_uri")
    public static String WEI_XIN_REDIRECT_URI;

    @Config("wei_xin_redirect_uri_agent")
    public static String WEI_XIN_REDIRECT_URI_AGENT;


    @Config("wei_xin_login_url")
    public static String WEI_XIN_LOGIN_URL;

    @Config("wei_xin_page_access_check_url")
    public static String WEI_XIN_PAGE_ACCESS_CHECK_URL;


    //微信公众号支付
    @Config("wei_xin_mch_id")
    public static String WEI_XIN_MCH_ID;
    @Config("wei_xin_pay_callback_url")
    public static String WEI_XIN_PAY_CALLBACK_URL;
    @Config("wei_xin_key")
    public static String WEI_XIN_KEY;

    @Config("validate_code_msg")
    public static String VALIDATE_CODE_MSG;

    @Config("wei_fu_tong_url")
    public static String WEI_FU_TONG_PAY_URL;
    @Config("wei_fu_tong_pay_mch_id")
    public static String WEI_FU_TONG_PAY_MCH_ID;
    @Config("wei_fu_tong_pay_front_callback_url")
    public static String WEI_FU_TONG_PAY_FRONT_CALLBACK_URL;
    @Config("wei_fu_tong_pay_notify_url")
    public static String WEI_FU_TONG_PAY_NOTIFY_URL;
    @Config("wei_fu_tong_pay_key")
    public static String WEI_FU_TONG_PAY_KEY;

    //会员代理公众号二维码
    @Config("agent_qrcode")
    public static String AGENT_QRCODE;
    @Config("agent_qrcode_type")
    public static String AGENT_QRCODE_TYPE;

    //首信易支付
    @Config("key")
    public static String MD5KEY;
    @Config("v_mid")
    public static String V_MID;

    @Config("zhineng_cloud_uid")
    public static String ZHINENG_CLOUD_UID;
    @Config("zhineng_cloud_token")
    public static String ZHINENG_CLOUD_TOKEN;
    @Config("zhineng_cloud_notify_url")
    public static String ZHINENG_CLOUD_NOTIFY_URL;
    @Config("zhineng_cloud_return_url")
    public static String ZHINENG_CLOUD_RETURN_URL;

    //融雅支付
    @Config("rongya_pay_url")
    public static String RONGYA_PAY_URL;
    @Config("rongya_pay_notify_url")
    public static String RONGYA_PAY_NOTIFY_URL;
    @Config("rongya_pay_memberid")
    public static String RONGYA_PAY_MEMBERID;
    @Config("rongya_pay_key")
    public static String RONGYA_PAY_KEY;

    @Config("ronghe_pay_url")
    public static String RONGHE_PAY_URL;
    @Config("ronghe_pay_mer_no")
    public static String RONGHR_PAY_MER_NO;
    @Config("ronghe_pay_session")
    public static String RONGHE_PAY_SESSION;
    @Config("ronghe_pay_key")
    public static String RONGHE_PAY_KEY;
    @Config("ronghe_pay_notify_url")
    public static String RONGHE_PAY_NOTIFY_URL;
    @Config("ronghe_pay_return_url")
    public static String RONGHE_PAY_RETURN_URL;

    //环迅网关支付链接
    @Config("huanxun_pay_url")
    public static String HUANXUN_PAY_URL;
}
