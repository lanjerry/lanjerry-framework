package org.lanjerry.admin.util;

import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>
 * 缓存操作工具类
 * </p>
 *
 * @author lanjerry
 * @since 2020-06-29
 */
public class CacheUtil {

    /**
     * 清除用户权限
     *
     * @author lanjerry
     * @since 2020/6/29 16:37
     * @param userId 用户id，"*"表示全部
     */
    public static void clearUserCache(String userId){
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_ROLE.concat(userId)))));
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat(userId)))));
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_ROUTER.concat(userId)))));
    }
}
