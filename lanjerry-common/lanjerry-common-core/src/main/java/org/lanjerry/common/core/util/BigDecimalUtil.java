package org.lanjerry.common.core.util;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 *
 * @author lanjerry
 * @date 2018/8/25 16:21
 */
public class BigDecimalUtil {

    /**
     * <p>
     * 提供精确的加法运算。
     * </p>
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    /**
     * <p>
     * 提供精确的减法运算。
     * </p>
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }

    /**
     * <p>
     * 提供精确的乘法运算。
     * </p>
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return mul(v1, v2, 2);
    }

    /**
     * <p>
     * 提供精确的乘法运算。
     * </p>
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
        if (null == v1) {
            v1 = BigDecimal.ONE;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * <p>
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * </p>
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, 10);
    }

    /**
     * <p>
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * </p>
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (null == v1) {
            v1 = BigDecimal.ONE;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * <p>
     * 提供精确的小数位四舍五入处理。
     * </p>
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (null == v || v.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return v.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * <p>
     * 比较 v1 是否等于 v2
     * </p>
     *
     * @param v1 值 1
     * @param v2 值 2
     * @return
     */
    public static boolean equalThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            return false;
        }
        return v1.compareTo(v2) == 0;
    }

    /**
     * <p>
     * 比较 v1 是否小于 v2
     * </p>
     *
     * @param v1 值 1
     * @param v2 值 2
     * @return
     */
    public static boolean lessThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            return false;
        }
        return v1.compareTo(v2) == -1;
    }

    /**
     * <p>
     * 比较 v1 是否小于等于 v2
     * </p>
     *
     * @param v1 值 1
     * @param v2 值 2
     * @return
     */
    public static boolean lessEqThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            return false;
        }
        return v1.compareTo(v2) <= 0;
    }

    /**
     * <p>
     * 比较 v1 是否大于 v2
     * </p>
     *
     * @param v1 值 1
     * @param v2 值 2
     * @return
     */
    public static boolean greaterThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            return false;
        }
        return v1.compareTo(v2) == 1;
    }

    /**
     * <p>
     * 比较 v1 是否大于等于 v2
     * </p>
     *
     * @param v1 值 1
     * @param v2 值 2
     * @return
     */
    public static boolean greaterEqThan(BigDecimal v1, BigDecimal v2) {
        if (null == v1 || null == v2) {
            return false;
        }
        return v1.compareTo(v2) >= 0;
    }
}
