package com.rmkj.microcap.common.modules.jpush.constants;

/**
 * Created by zhangbowen on 2015/10/8.
 */
public class JPushMsgConstants {
    //发送群体
    public static final String TARGET_TYPE_PERSONAL = "1";//发送给单人
    public static final String TARGET_TYPE_ALL = "2";//发送给全体
    //发送类别
    public static final int SEND_TYPE_ALIAS = 1;//根据alias发送
    public static final int SEND_TYPE_REGIDS = 2;//根据与极光绑定注册id发送
    public static final int SEND_TYPE_ALL = 3;//发送全部
    //消息类别
    public static final int MSG_TYPE_NOTIFICATION = 1;//发送通知
    public static final int MSG_TYPE_MESSAGE = 2;//发送消息
};
