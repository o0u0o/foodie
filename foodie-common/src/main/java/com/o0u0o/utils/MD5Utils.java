package com.o0u0o.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * MD5加密工具
 * @author o0u0o
 * @date 2020/10/26 6:50 下午
 */
public class MD5Utils {

    /**
     *
     * @Title: MD5Utils.java
     * @Package com.o0u0o.utils
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("o0u0o");
            System.out.println(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
