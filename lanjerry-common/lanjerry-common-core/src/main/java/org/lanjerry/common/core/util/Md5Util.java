package org.lanjerry.common.core.util;

import java.security.MessageDigest;

import org.lanjerry.common.core.constant.CommonConsts;

/**
 * <p>
 * MD5加密工具类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public class Md5Util {

    /**
     * 密码加密
     *
     * @param password 明文密码
     * @param salt     加密盐
     * @return 密文
     */
    public static String encode(String password, String salt) {
        password = password + salt;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 密码加密使用固定加密盐
     *
     * @param password 明文密码
     * @return 密文
     */
    public String encode(String password) {
        return encode(password, CommonConsts.DEFAULT_SALT);
    }
}
