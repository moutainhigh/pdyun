package com.rmkj.microcap.common.bean;


import com.rmkj.microcap.common.bean.annotation.CodeAnnot;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangbowen on 2015/6/24.
 */
public enum StatusCode {
    @CodeAnnot() OK(0),
    @CodeAnnot("服务器正在维护") SERVER_ERROR(1),
    @CodeAnnot("余额不足")MONEY_NOT_ENOUGH(2),
    @CodeAnnot("参数异常")VALIDATION_FAILED(3),
    @CodeAnnot("密码不正确")PASSWORD_ERROR(4),
    @CodeAnnot("账号不存在")ACCOUNT_NO_EXIST(5),
    @CodeAnnot("验证码不正确")VALIDATE_CODE_ERROR(6),
    @CodeAnnot("手机号已存在")MOBILE_EXIST(7),
    @CodeAnnot("您的账户已被冻结，详情咨询客服")USER_CLOSE(8),
    @CodeAnnot("验证码发送失败")SEND_SMS_ERROR(9),

    @CodeAnnot("用户不存在")USER_NOT_EXIST(10),
    @CodeAnnot("交易密码不正确")TRADE_PASSWORD_INVALID(11),
    @CodeAnnot("交易失败") MAKE_TRADE_ERROR(12),

    @CodeAnnot("微信预支付失败") WX_PRE_PAY_ERROR(13),
    @CodeAnnot("邀请码不正确") AGENT_INVITE_CODE_ERROR(14),
    @CodeAnnot("手机号不存在") MOBILE_NOT_EXIST(15),
    @CodeAnnot("银行卡已绑定") BANK_CARD_IS_BOUND(16),
    @CodeAnnot("优惠券不可用") COUPON_INVALID(17),
    @CodeAnnot("请填写邀请码") AGENT_VALID_CODE_REQUIRE(18),
    @CodeAnnot("目前持仓数量达到上限") HOLD_POSITIONS(19),
    @CodeAnnot("目前持仓金额超出规定额度") HOLD_MONEY(20),
    @CodeAnnot("每日提现金额达到上限") CASH_MONEY_RATION_MAX(21),
    @CodeAnnot("每日提现次数达到上限") CASH_MONEY_COUNT_MAX(22),
    @CodeAnnot("错误的请求") BAD_REQUEST_ADDRESS(23),
    @CodeAnnot("不存在的二维码") FILE_NOT_FOUND(24),
    @CodeAnnot("不支持此银行") YIZHIFU_NOT_FIND_BANK_LIANHANGHAO(25),
    @CodeAnnot("不支持此省份") YIZHIFU_PROVINCE_UNVALID(26),
    @CodeAnnot("不支持此城市") YIZHIFU_CITY_UNVALID(27),
    @CodeAnnot("请先绑定银行卡") NOT_BANKCARD(28),
    @CodeAnnot("超出提现申请时间") BEYOND_WITHDRAW_TIME(29),
    @CodeAnnot("最低提现100元") WITHDRAW_MONEY_MIN(30),
    @CodeAnnot("邀请手机号不正确") AGENT_INVITE_MOBILE_ERROR(31),
    @CodeAnnot("参数为空") PARAM_EMPTY_ERROR(32),
    @CodeAnnot("休市停盘") MARKET_CLOSE(99),
    @CodeAnnot("代金券余额不足")COUPON_MONEY_NOT_ENOUGH(33),
    @CodeAnnot("用户已没有认购机会") USER_SUB_NO_CHANCE(34),
    @CodeAnnot("商品可认购数量不足") USER_SUBGOODS_NOT_ENOUGH(35),
    @CodeAnnot("认购金额错误") USER_SUBGOODS_MONEY_ERROR(36),
    @CodeAnnot("认购服务费错误") USER_SUBGOODS_SERVICEFEE_ERROR(37),
    @CodeAnnot("认购用户资金变更失败") USER_SUB_MONEY_ERROR(38),
    @CodeAnnot("更改认购商品剩余数量失败") CHANGE_SUB_LEFTNUM_ERROR(39),
    @CodeAnnot("更改认购商品状态失败") CHANGE_SUB_GOODS_STATUS_ERROR(40),
    @CodeAnnot("更改用户认购标识错误") CHANGE_USER_SUBFLAG_ERROR(41),

    @CodeAnnot("商品不存在") SUBGOODS_NOT_EXIST(45),
    @CodeAnnot("商品数量不足") TRADE_HOLDNUM_NOT_ENOUGH(46),
    @CodeAnnot("商品上架失败") ENTRY_ORDERS_FAIL(47),
    @CodeAnnot("金额错误") USER_BUY_MONEY_ERROR(48),
    @CodeAnnot("手续费错误") USER_BUY_FEE_ERROR(49),
    @CodeAnnot("购买用户资金变更失败") USER_CHANGE_BUY_MONEY_ERROR(50),
    @CodeAnnot("平仓用户资金变更失败") USER_CHANGE_SELL_MONEY_ERROR(51),
    @CodeAnnot("积分变更失败") USER_CHANGE_INTEGRAL_ERROR(52),
    @CodeAnnot("添加积分明细失败") ADD_INTEGRAL_DETAIL_ERROR(53),
    @CodeAnnot("返佣失败") RETURN_MONEY_ERROR(54),
    @CodeAnnot("服务费返佣普通客户失败") RETURN_SERVICE_AGENT_ERROR(55),
    @CodeAnnot("手续费返佣普通客户失败") RETURN_FEE_AGENT_ERROR(56),
    @CodeAnnot("服务费返佣高级客户失败") RETURN_SERVICE_UNITS_ERROR(57),
    @CodeAnnot("手续费返佣高级客户失败") RETURN_FEE_UNITS_ERROR(58),
    @CodeAnnot("服务费返佣vip客户失败") RETURN_SERVICE_CENTER_ERROR(59),
    @CodeAnnot("手续费返佣vip客户失败") RETURN_FEE_CENTER_ERROR(60),
    @CodeAnnot("该用户已绑定银行卡") BIND_BANK_CARD_ERROR(61),
    @CodeAnnot("修改银行卡信息错误") UPDATE_BANK_CARD_ERROR(62),
    @CodeAnnot("修改密码失败")UPDATE_PASSWORD_ERROR(63),
    @CodeAnnot("原密码不正确")OLD_PASSWORD_ERROR(64),
    @CodeAnnot("提货数量不能为空")TAKE_NUM_NOT_NULL_ERROR(65),
    @CodeAnnot("请选择提货的发货地址")TAKE_GOODS_SEND_ADDRESS_NOT_NULL_ERROR(66),
    @CodeAnnot("订单流水号不能为空")TAKE_GOODS_SERIAL_NO_NOT_NULL_ERROR(67),
    @CodeAnnot("提货数量错误")TAKE_NUM_ERROR(68),
    @CodeAnnot("订单流水号不存在")SERIAL_NO_NOT_EXISTS(69),
    @CodeAnnot("撤销挂单修改原订单失败")CANCLE_HANG_TRADE_OLD_ERROR(70),
    @CodeAnnot("撤销挂单失败")CANCLE_HANG_TRADE_ERROR(71),
    @CodeAnnot("购买数量错误")BUY_NUM_ERROR(72),
    @CodeAnnot("商品为空")GOODSID_NOT_NULL_ERROR(73),
    @CodeAnnot("请选择要购买商品的价格")GOODS_BUY_PRICE_ERROR(74),
    @CodeAnnot("分离挂单信息错误")UPDATE_HANG_TRADE_ERROR(75),
    @CodeAnnot("挂单价格不在范围内")ENTRY_ORDER_NONE(76),
    @CodeAnnot("订单来源流水号为空")OLD_SERIAL_NO_NULL_ERROR(77),
    @CodeAnnot("不在挂单时间范围") NOT_HANG_TIME_ERROR(78),
    @CodeAnnot("认购资金返现失败") SUB_RETURN_USER_MONEY_ERROR(79),
    @CodeAnnot("统计平台服务费错误") PLAT_SERVICE_FEE_ERROR(80),
    @CodeAnnot("统计平台手续费错误") PLAT_FEE_ERROR(81),
    @CodeAnnot("挂单金额限制小数点后两位") MONEY_TWO_FLOAT(82),


    @CodeAnnot("最低充值100元")MONEY_RECORD_MIN_ERROR(100);

    private static final Map<String, String> hMap = new HashMap<>();

    static {
        Field[] fields = StatusCode.class.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CodeAnnot.class)) {
                hMap.put(field.getName(), field.getAnnotation(CodeAnnot.class).value());
            }
        }
    }

    private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return hMap.get(this.toString());
    }
}
