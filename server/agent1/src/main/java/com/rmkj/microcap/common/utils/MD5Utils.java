package com.rmkj.microcap.common.utils;


import org.apache.commons.codec.digest.DigestUtils;


/**
 * Created by yan on 2016/1/15.
 * MD5 加密工具类
 */
public class MD5Utils {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }
}