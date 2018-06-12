package com.rmkj.microcap.common.bean;


import com.rmkj.microcap.common.bean.annotation.CodeAnnot;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangbowen on 2015/6/24.
 */
public enum StatusCode {

    @CodeAnnot("正确处理")OK(0),
    @CodeAnnot("服务器正在维护") SERVER_ERROR(1),
    @CodeAnnot("没有举报")NO_REPORT(1),
    @CodeAnnot("错误处理")FAILED(-1),
    @CodeAnnot("退款不存在")ORDER_BACK_NOT_FOUND(4),
    @CodeAnnot("订单状态不正确")ORDER_STATUS_ERROR(5),
    @CodeAnnot("用户下面存在商品")USER_GOODS(2),
    @CodeAnnot("手机号已存在")PHONE_EXIST(7),
    @CodeAnnot("该门诊下存在主任医生")CLINIC_DOCTOR(4),
    @CodeAnnot("该商品已被别的用户代理")USER_GOODS_AGENT(3),
    @CodeAnnot("图片上传到oss上失败")OSS_IMG(-3),
    @CodeAnnot("用户注册微信生成二维码失败")REG_QRCODE_WEI_XIN_CREATE(20),
    @CodeAnnot("提现金额不足，请检查现有资金") OVER_MONEY_GET_CASH(9),
    @CodeAnnot("用户已存在")USER_EXIST(8),
    @CodeAnnot("参数非法")VALIDATION_FAILED(-2),
    @CodeAnnot("已经做了审核，请刷新") HAS_PAST(10),
    @CodeAnnot("手机号已存在，请重新输入")MOBILE_FAILED(-4);

    private static final Map<String, String> hMap = new HashMap<String, String>();

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

    public String GetDescription() {
        return hMap.get(this.toString());
    }
    public String getDescription() {
        return hMap.get(this.toString());
    }
}
