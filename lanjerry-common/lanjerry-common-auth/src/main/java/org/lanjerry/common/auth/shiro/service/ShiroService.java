package org.lanjerry.common.auth.shiro.service;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * shiro 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-10
 */
public interface ShiroService {

    /**
     * 根据帐号获取登录信息
     *
     * @author lanjerry
     * @since 2019/9/16 17:38
     * @param account 帐号
     * @return java.lang.Object
     */
    Object getLoginByAccount(String account);

    /**
     * 根据id获取角色信息
     *
     * @author lanjerry
     * @since 2019/9/16 17:38
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getRoles();

    /**
     * 根据id获取权限信息
     *
     * @author lanjerry
     * @since 2019/9/16 17:38
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getPermissions();

    /**
     * 获取过滤链
     *
     * @author lanjerry
     * @since 2019/11/21 16:13
     * @param
     * @return
     */
    Map<String, String> filterChainDefinitions();
}
