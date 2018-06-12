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

    //会员单位二维码
    @Config("agent_qrcode")
    public static String AGENT_QRCODE;
    @Config("agent_qrcode_type")
    public static String AGENT_QRCODE_TYPE;
}
