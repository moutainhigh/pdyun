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

    //代理角色表id
    interface ML3_AGENT_ROLE{
        //市场管理部id
        String OCID = "1";
        //代理商
        String AGENTID = "4";
        //会员单位
        String MEMBER_UNITS = "5";
    }

    //会员单位保证金浮动记录表类型
    interface UNITS_MONEY_CHANGE_TYPE{
        //浮动保证金金额
        Integer FLOAT_MONEY = 1;
        //追加保证金金额
        Integer ADD_BOND_MONEY = 2;
    }

    /**
     * 出金状态
     */
    interface WITHDRAW_STATUS{
        String SUCCESS = "1";
        String FAILD = "2";
        String PROCESSED = "0";

    }

    /**
     * 商品认购状态 1-认购  2-购销  3-下架
     */
    interface SUB_GOODS_STATUS {
        String SUB = "1";
        String PUR = "2";
        String SOLD_OUT = "3";
    }

    /**
     * 持仓类型:   1-认购   2-买入  3-持仓划转
     */
    interface TRADE_HOLD_FLAG {
        String TRADE_SUB = "1";
        String TRADE_BUY = "2";
        String TRANS_BUY = "3";
    }

}
