package com.rmkj.microcap.common.constants;

import java.io.File;

/**
 * Created by zhangbowen on 2015/12/22.
 * 业务常量
 */
public interface Constants {
    int MINUTE = 60;
    int HOUR = MINUTE * 60;

    String PROJECT_RUN_DIR = Constants.class.getClassLoader().getResource("").getPath().substring(0, Constants.class.getClassLoader().getResource("").getPath().length() - "\\WEB-INF\\classes".length());

    String BANNER_DIR = "static"+File.separator+"images"+File.separator+"banners";

    interface WeiXin {
        String TOKEN = "weixin_access_token";
        String TICKET = "weixin_jsapi_ticket";
    }

    //资金记录Type类型 0：充值 1：提现
    interface MONEY_RECORD_TYPE{
        String RECHARGE = "0";
        String WITHDRAW = "1";
    }

    /**
     * 代理后台角色
     */
    interface ML3_AGENT_ROLE{
        String ROLE_UNITS = "5";
        String ROLE_CENTER = "1";
        String ROLE_AGENT = "4";
    }

    /**
     * 提现状态
     */
    interface WITHDRAW_STATUS{
        String SUCCESS = "1";
        String WAIT = "0";
        String FAILD = "2";
    }

    interface AGENT_STATUS {
        String NORMAL = "0";
        String DISABLE = "1";
    }

}
