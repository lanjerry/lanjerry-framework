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

    /**
     * 系统用户默认密码
     */
    String SYS_USER_DEFAULT_PASSWORD = "123456";

    /**
     * 系统权限一级菜单
     */
    Integer SYS_PERMISSION_PARENT_ID = 0;

    /**
     * redis中系统用户角色数据
     */
    String REDIS_SYS_USER_ROLE = "sys_user_role:";

    /**
     * redis中系统用户角色数据过期时间15天
     */
    long REDIS_SYS_USER_ROLE_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15;

    /**
     * redis中系统用户权限数据
     */
    String REDIS_SYS_USER_PERMISSION = "sys_user_permission:";

    /**
     * redis中系统用户权限数据过期时间15天
     */
    long REDIS_SYS_USER_PERMISSION_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15;
}
