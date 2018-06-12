package com.rmkj.microcap.common.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 标识公众号，用户点击菜单进行访问的请求
 * 获取openid
 * 未登录就进行登录
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WeiXinLogin {
    String value() default "";
}
