package org.lanjerry.common.core.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 公共常量池
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
public interface CommonConsts {

    /**
     * 本地IP数组
     */
    List<String> LOCAL_HOST_IPs = new ArrayList<String>() {
        {
            add("0:0:0:0:0:0:0:1");
            add("localhost");
        }
    };

    /**
     * 本地IP
     */
    String LOCAL_HOST_IP = "127.0.0.1";

    /**
     * JWT过期时间一周
     */
    long JWT_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 默认加密盐
     */
    String DEFAULT_SALT = "lanjerry";

    /**
     * 默认管理员角色
     */
    String DEFAULT_ADMIN_ROLE = "admin";

    /**
     * 默认管理员权限
     */
    String DEFAULT_ADMIN_PERMISSION = "*:*:*";
}
