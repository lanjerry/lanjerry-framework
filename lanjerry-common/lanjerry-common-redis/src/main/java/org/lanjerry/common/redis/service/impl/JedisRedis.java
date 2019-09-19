package org.lanjerry.common.redis.service.impl;

import org.lanjerry.common.redis.service.NbRedis;

/**
 * <p>
 * jedis实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-18
 */
public class JedisRedis implements NbRedis {

    @Override
    public boolean setFromString(String key, String value) {
        return false;
    }
}
