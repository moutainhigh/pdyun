package com.rmkj.microcap.common.utils;

import com.rmkj.microcap.common.constants.ProjectConstants;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Random;
import java.util.UUID;

/**
 * Created by zhangbowen on 2015/12/4.
 */
public class Utils {
    public static final int MINUTE = 60;
    public static final int HOUR = MINUTE * 60;

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据手机号生成用户名
     *
     * @param phone
     * @return
     */
    public static String generateNameByPhone(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 生成短信验证码
     *
     * @return
     */
    public static String generateSmsCode() {
        Random random = new Random();
        String verificationCode = "";
        for (int i = 0; i < 4; i++) {
            verificationCode += random.nextInt(10);
        }
        return verificationCode;
    }

    public static String rootUrl() {
        HttpServletRequest request = ContextUtils.getRequest();
        return request.getScheme().concat("://").concat(request.getServerName()).concat(request.getServerPort() == 80 ? "" : ":".concat(request.getServerPort()+"")).concat(request.getContextPath());
    }

    public static String picUrl(String type, String fileName){
        return "pic/".concat(type).concat("/").concat(fileName).concat("/show");
    }

    public static String picPath(String type) {
        return ProjectConstants.PIC_PATH.concat(File.separator).concat(type);
    }

    /**
     * 邀请码生成工具
     */
    public static class InvitationCodeUtils {

        /**
         * 自定义进制(0,1没有加入,容易与o,l混淆)
         */
        private static final char[] r = new char[]{'Q', 'w', 'E', '8', 'a', 'S', '2', 'd', 'Z', 'x', '9', 'c', '7', 'p', 'O', '5', 'i', 'K', '3', 'm', 'j', 'U', 'f', 'r', '4', 'V', 'y', 'L', 't', 'N', '6', 'b', 'g', 'H'};
        /**
         * 自动补全组(不能与自定义进制有重复)
         */
        private static final char[] b = new char[]{'q', 'W', 'e', 'A', 's', 'D', 'z', 'X', 'C', 'P', 'o', 'I', 'k', 'M', 'J', 'u', 'F', 'R', 'v', 'Y', 'T', 'n', 'B', 'G', 'h'};
        /**
         * 进制长度
         */
        private static final int l = r.length;
        /**
         * 序列最小长度
         */
        private static final int s = 6;

        /**
         * 根据id或手机号生成六位随机码
         *
         * @param num
         * @return 随机码
         */
        public static String generateCode(long num) {
            char[] buf = new char[32];
            int charPos = 32;

            while ((num / l) > 0) {
                buf[--charPos] = r[(int) (num % l)];
                num /= l;
            }
            buf[--charPos] = r[(int) (num % l)];
            String str = new String(buf, charPos, (32 - charPos));
            //不够长度的自动随机补全
            if (str.length() < s) {
                StringBuffer sb = new StringBuffer();
                Random rnd = new Random();
                for (int i = 0; i < s - str.length(); i++) {
                    sb.append(b[rnd.nextInt(b.length - 1)]);
                }
                str += sb.toString();
            }
            return str;
        }
    }
    //生成8位邀请码
    public static class InvitationCodeUtils1 {

        /**
         * 自定义进制(0,1没有加入,容易与o,l混淆)
         */
        private static final char[] r = new char[]{'Q', 'w', 'E', '8', 'a', 'S', '2', 'd', 'Z', 'x', '9', 'c', '7', 'p', 'O', '5', 'i', 'K', '3', 'm', 'j', 'U', 'f', 'r', '4', 'V', 'y', 'L', 't', 'N', '6', 'b', 'g', 'H'};
        /**
         * 自动补全组(不能与自定义进制有重复)
         */
        private static final char[] b = new char[]{'q', 'W', 'e', 'A', 's', 'D', 'z', 'X', 'C', 'P', 'o', 'I', 'k', 'M', 'J', 'u', 'F', 'R', 'v', 'Y', 'T', 'n', 'B', 'G', 'h'};
        /**
         * 进制长度
         */
        private static final int l = r.length;
        /**
         * 序列最小长度
         */
        private static final int s = 8;

        /**
         * 根据id或手机号生成六位随机码
         *
         * @param num
         * @return 随机码
         */
        public static String generateCode(long num) {
            char[] buf = new char[32];
            int charPos = 32;

            while ((num / l) > 0) {
                buf[--charPos] = r[(int) (num % l)];
                num /= l;
            }
            buf[--charPos] = r[(int) (num % l)];
            String str = new String(buf, charPos, (32 - charPos));
            //不够长度的自动随机补全
            if (str.length() < s) {
                StringBuffer sb = new StringBuffer();
                Random rnd = new Random();
                for (int i = 0; i < s - str.length(); i++) {
                    sb.append(b[rnd.nextInt(b.length - 1)]);
                }
                str += sb.toString();
            }
            return str;
        }
    }
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha(String str) {
        return DigestUtils.sha1Hex(str);
    }

    /**
     * 格式化字符串：
     * 你好，{0}，我是你孩子的老师{1}，请你{2}来参加家长会
     * {0} 奥巴马
     * {1} 川普
     * {2} 2016-12-12早8点
     * 你好，奥巴马，我是你孩子的老师川普，请你2016-12-12早8点来参加家长会
     * @param resource
     * @param str
     * @return
     */
    public static String formatStr(String resource, String ... str){
        for (int i = 0; i < str.length; i++){
            resource = resource.replace("{"+i+"}", "%"+(i+1)+"$s");
        }
        return String.format(resource, str);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }
}
