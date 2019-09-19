package org.lanjerry.common.redis.service.impl;

import org.lanjerry.common.redis.service.NbRedis;

import io.lettuce.core.RedisClient;

/**
 * <p>
 * lettuce实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-18
 */
public class LettuceRedis implements NbRedis {

    private final RedisClient redisClient;

    public LettuceRedis(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public boolean setFromString(String key, String value) {
        redisClient.connect().sync().set(key,value);
        return true;
    }

}
