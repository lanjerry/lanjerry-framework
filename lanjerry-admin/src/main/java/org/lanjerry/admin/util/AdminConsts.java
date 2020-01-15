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

    //============================OTHER=============================
    /**
     * 日期查询的默认起始时分秒
     */
    String START_TIME = " 00:00:00";

    /**
     * 日期查询的默认时间止
     */
    String END_TIME = " 59:59:59";
}
