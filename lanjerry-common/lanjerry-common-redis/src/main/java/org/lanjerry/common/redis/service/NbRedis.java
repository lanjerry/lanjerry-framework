package org.lanjerry.common.redis.service;

/**
 * <p>
 * redis接口类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-18
 */
public interface NbRedis {

    boolean setFromString(String key, String value);
}
