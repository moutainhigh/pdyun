package com.rmkj.microcap.common.constants;

import java.math.BigDecimal;

/**
 * Created by zhangbowen on 2015/12/22.
 * 业务常量
 */
public interface Constants {
    int MINUTE = 60;
    int HOUR = MINUTE * 60;

    // 更新合约时间间隔
    long REFRESH_CONTRACTS = 30000;

    interface LoginType{
        String DEFAULT = "";
        String AGENT = "agent";
    }

    interface User{
        // 经纪人
        String TYPE = "1";
    };

    interface Agent{
        //经纪人审核状态  0  审核中 1 审核通过 2 审核未通过
        int AGENT_REVIEW_STATUS_ING = 0;
        int AGENT_REVIEW_STATUS_SUCCESS = 1;
        int AGENT_REVIEW_STATUS_FAILURE = 2;
    }

    interface Market {
        // default 只获取当前价格
        // all 获取当前价格及 昨收 今开 最高 最低
        enum GetType {DEFAULT,ALL}
    }

    interface Trade {
        String BUY_UP = "0";
        String BUY_DOWN = "1";
        //0 资金 1 代金券
        String TYPE_MONEY = "0";
        String TYPE_COUPON_MONEY = "1";
        String TYPE_MONEY_AND_COUPON = "2";

        String MODEL_DEFAULT = "0";
        String MODEL_TIME = "1";
        String MODEL_POINT = "2";

        String BALANCE_DEFAULT = "0";
        String BALANCE_ING = "1";
        String BALANCE_OVER = "2";

        String RESULT_WIN = "1";
        String RESULT_LOSE = "-1";
        String RESULT_DRAW = "0";

        enum SellType {
            HAND("0"),
            STOP("1"),
            TIME_STOP("2"),
            ERROR("3");

            private String val;
            SellType(String val){
                this.val = val;
            }
            public String val(){
                return val;
            }
        }
    }

    interface AgentMoney{
        //类型 0 交易返佣 1 提现
        String AGENT_MONEY_CHANGE_TYPE_IN = "0";
        String AGENT_MONEY_CHANGE_TYPE_OUT = "1";

        //返佣比例
        Double RATIO = 0.01;
    }
    interface Money {
        // 类型 0 充值 1 提现 2 认购  3 买入 4 平仓 5 认购返现
        String MONEY_CHANGE_TYPE_MONEY_IN = "0";
        String MONEY_CHANGE_TYPE_MONEY_OUT = "1";
        String MONEY_CHANGE_TYPE_SUB_MAKE = "2";
        String MONEY_CHANGE_TYPE_TRADE_MAKE = "3";
        String MONEY_CHANGE_TYPE_SUB_SELL = "4";
        String MONEY_CHANGE_TYPE_TRADE_SELL = "4";
        String MONEY_CHANGE_SUB_RETURN_USER = "5";

        // 类型：0 充值 1 提现
        String MONEY_RECORD_TYPE_IN = "0";
        String MONEY_RECORD_TYPE_OUT = "1";

        String MONEY_RECORD_STATUS_DEFAULT = "0";
        String MONEY_RECORD_STATUS_SUCCESS = "1";
        String MONEY_RECORD_STATUS_FAIL = "2";
    }

    interface CashCoupon{
        //赠送代金券的额度
        String GIVE_MONEY = "20.00";
        //代金券的使用条件
        String MIN_MONEY = "100.00";
        //代金券使用期限
        int interval = 30;
        //赠送代金券的数量
        int counts = 5;
        //代金券状态：0 正常 1 已使用 2 已过期
        String NORMAL_STATUS = "0";
        String USE_STATUS = "1";
    }

    interface Http {
        String AUTHORIZATION = "_auth";
        String AUTHORIZATION_AGENT = "_auth_agent";
    }

    interface IM {
        String TOKEN = "im_access_token";
//        //发送消息
//        String PUSH_MESSAGE = BASE_URL + "/{org_name}/{app_name}/messages";
//        //更新群组信息
//        String UPDATE_GROUP_INFO = BASE_URL + "/{org_name}/{app_name}/chatgroups/{group_id}";
    }

    interface WeiXin {
        String TOKEN = "weixin_access_token";
        String TICKET = "weixin_jsapi_ticket";
    }

    /**
     * 终端类型
     */
    interface Terminal {
        String TERMINAL_ANDROID = "1";
        String TERMINAL_IOS = "2";
        String TERMINAL_WAP = "3";
        String TERMINAL_NAME = "terminal";
    }

    /**
     * 验证码类型
     * reg 注册 mtpwd 修改交易密码 mmoble 修改手机号 moneyout 提现
     */
    interface ValidateCode {
        String REG = "reg";//注册
        String M_TRADE_PWD = "mtpwd";
        String M_MOBILE = "mmoble";
        String MONEY_OUT = "moneyout";
    }

    interface USER_TYPE {
        String COMMON = "1"; // 普通用户
    }

    // 0 正常 1 停用
    interface USER_STATUE {
        String VALID = "0"; // 正常
        String INVALID = "1"; // 停用
    }

    interface Pay {
        String PAY_TYPE_WEI_XIN = "1";
    }

    /**
     * 微信 参数常量
     */
    interface WX {
        String TRADE_TYPE_JSAPI = "JSAPI";
    }

    public interface UserMessage {
        String TITLE_TRADE = "交易";
        String USE_CASH_COUPON = "使用代金券";
    }

    public interface Coupon {
        String STATUS_DEFAULT = "0";
        String STATUS_USED = "1";
        String STATUS_EXPIRE = "2";
    }

    /**
     * 交易模型：0  微盘模型 1 微交易时间模型 2 微交易点位模型 3 全部模式
     */
    interface ContractModel{
        String MARKET = "0";
        String TRADE = "1";
        String POINT = "2";
        String ALL = "3";
    }

    //会员单位保证金浮动记录表类型
    interface UNITS_MONEY_CHANGE_TYPE{
        //浮动保证金金额
        Integer FLOAT_MONEY = 1;
        //追加保证金金额
        Integer ADD_BOND_MONEY = 2;
    }

    interface Sub{//是否能认购 ： ‘1’ - 能  ''2''- 否'
        String SUB_OK = "1";
        String SUB_NO = "2";
    }

    interface SubGoodsStatus{//商品状态： 1-认购  2-购销  3-下架
        String SUB = "1";
        String TRADE = "2";
        String CANCEL = "3";
    }

    interface SubMake{//trade状态：1 持仓  2挂单 3 平仓（交割） 4 下架
        String HOLD = "1";
        String HANG = "2";
        String SELL = "3";
        String DELETE = "4";
    }

    interface holdFlag{//持仓类型:   1-认购   2-买入
        String RG = "1";
        String MR = "2";
    }

    interface SubFeesType{//1-服务费   2-手续费
        String SERVICE = "1";
        String FEE = "2";
    }

    /**
     * 用户注册类型: 1.品道注册 2.商城注册
     */
    interface RegType {
        String PINDAO_REG = "1";
        String SHOP_REG = "2";
    }

    /**
     * 状态： 1-提货   2-冻结   3-驳回    4-通过
     */
    interface TakeGoodsType{
        String TAKE = "1";
        String FROZEN = "2";
        String REFUSED = "3";
        String PASS = "4";
    }

    /**
     * 参数设置表KEY
     */
    interface PARAMETER_SET_KEY {
        String OPEN_TIME = "open_time";
        String WEEK_DAY_SET = "week_day_set";
    }

    /**
     * 收藏状态
     */
    interface GOODS_STORE{
        String STORE = "1";
        String CANCEL = "2";
    }
}
