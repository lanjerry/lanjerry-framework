package org.lanjerry.common.core.util;


import java.util.Collection;

import org.lanjerry.common.core.exception.ApiException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * API 业务断言
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public class ApiAssert {

    /**
     * 大于O
     */
    public static void gtZero(Integer num, String message) {
        if (num == null || num <= 0) {
            error(message);
        }
    }

    /**
     * 大于等于O
     */
    public static void geZero(Integer num, String message) {
        if (num == null || num < 0) {
            error(message);
        }
    }

    /**
     * num1大于num2
     */
    public static void gt(Integer num1, Integer num2, String message) {
        if (num1 <= num2) {
            error(message);
        }
    }

    /**
     * num1大于等于num2
     */
    public static void ge(Integer num1, Integer num2, String message) {
        if (num1 < num2) {
            error(message);
        }
    }

    /**
     * obj1等于obj2
     */
    public static void eq(Object obj1, Object obj2, String message) {
        if (!obj1.equals(obj2)) {
            error(message);
        }
    }

    /**
     * 为真
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            error(message);
        }
    }

    /**
     * 为假
     */
    public static void isFalse(boolean condition, String message) {
        if (condition) {
            error(message);
        }
    }

    /**
     * 字符串不为空
     */
    public static void notBlank(String str, String message) {
        if (StrUtil.isBlank(str)) {
            error(message);
        }
    }

    /**
     * 集合不为空
     */
    public static void notBlank(Collection<?> collection, String message) {
        if (CollUtil.isEmpty(collection)) {
            error(message);
        }
    }

    /**
     * 字符串为空
     */
    public static void isBlank(String str, String message) {
        if (StrUtil.isNotBlank(str)) {
            error(message);
        }
    }

    /**
     * 集合为空
     */
    public static void isBlank(Collection<?> collection, String message) {
        if (CollUtil.isNotEmpty(collection)) {
            error(message);
        }
    }

    /**
     * 对象不为null
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            error(message);
        }
    }

    /**
     * 对象为null
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            error(message);
        }
    }

    /**
     * 失败结果
     *
     * @param message 提示信息
     */
    public static void error(String message) {
        throw ApiException.argError(message);
    }
}