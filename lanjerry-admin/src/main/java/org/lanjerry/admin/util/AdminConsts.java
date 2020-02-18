package org.lanjerry.admin.util;

/**
 * <p>
 * admin常量池
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface AdminConsts {

    //============================REDIS=============================
    /**
     * redis中系统用户角色数据
     */
    String REDIS_SYS_USER_ROLE = "sys_user_role:";

    /**
     * redis中系统用户角色数据过期时间15天（单位：秒）
     */
    long REDIS_SYS_USER_ROLE_EXPIRE_TIME = 60 * 60 * 24 * 15;

    /**
     * redis中系统用户权限数据
     */
    String REDIS_SYS_USER_PERMISSION = "sys_user_permission:";

    /**
     * redis中系统用户路由数据
     */
    String REDIS_SYS_USER_ROUTER = "sys_user_router:";

    /**
     * redis中系统用户权限数据过期时间15天（单位：秒）
     */
    long REDIS_SYS_USER_PERMISSION_ROUTER_EXPIRE_TIME = 60 * 60 * 24 * 15;

    /**
     * redis中验证码key
     */
    String CAPTCHA_CODE_KEY = "admin_captcha_code:";

    /**
     * redis中验证码有效期3分钟（单位：秒）
     */
    long CAPTCHA_EXPIRATION = 60 * 3;

    //============================默认=============================
    /**
     * 系统用户默认密码
     */
    String SYS_USER_DEFAULT_PASSWORD = "123456";

    /**
     * 系统权限一级菜单
     */
    Integer SYS_PERMISSION_PARENT_ID = 0;

    //============================代码生成=============================
    /**
     * 字符串类型
     */
    String GEN_TYPE_STRING = "String";

    /**
     * 整型
     */
    String GEN_TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
    String GEN_TYPE_LONG = "Long";

    /**
     * 定点型
     */
    String GEN_TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 单精度浮点型
     */
    String GEN_TYPE_FLOAT = "Float";

    /**
     * 双精度浮点型
     */
    String GEN_TYPE_DOUBLE = "Double";

    /**
     * 布尔类型
     */
    String GEN_TYPE_BOOLEAN = "Boolean";

    /**
     * 时间类型
     */
    String GEN_TYPE_DATE = "LocalDateTime";

    /**
     * 数据库字符串类型
     */
    String[] GEN_COLUMNTYPE_STR = { "char", "varchar", "narchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext" };

    /**
     * 数据库时间类型
     */
    String[] GEN_COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };

    /**
     * 数据库数字类型
     */
    String[] GEN_COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "double", "decimal" };

    //============================OTHER=============================
    /**
     * 日期查询的默认起始时分秒
     */
    String START_TIME = " 00:00:00";

    /**
     * 日期查询的默认时间止
     */
    String END_TIME = " 23:59:59";
}
