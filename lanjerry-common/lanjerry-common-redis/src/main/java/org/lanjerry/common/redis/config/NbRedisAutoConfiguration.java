package org.lanjerry.common.redis.config;

import org.lanjerry.common.redis.service.NbRedis;
import org.lanjerry.common.redis.service.impl.JedisRedis;
import org.lanjerry.common.redis.service.impl.LettuceRedis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

/**
 * <p>
 * redis配置自动注入
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-18
 */
@Configuration
@EnableConfigurationProperties(NbRedisProperties.class)
public class NbRedisAutoConfiguration {

    @Configuration
    @ConditionalOnClass(RedisClient.class)
    @ConditionalOnMissingBean(value = NbRedis.class)
    static class Lettuce {

        private NbRedisProperties nbRedisProperties;

        Lettuce(NbRedisProperties nbRedisProperties) {
            this.nbRedisProperties = nbRedisProperties;
        }

        @Bean
        public RedisClient redisClient(){
            RedisURI redisURI = RedisURI.create(nbRedisProperties.getHost(), nbRedisProperties.getPort());
            redisURI.setPassword(nbRedisProperties.getPassword());
            return RedisClient.create(redisURI);
        }

        @Bean
        public NbRedis nbRedis() {
            return new LettuceRedis(redisClient());
        }
    }

    @Configuration
    @ConditionalOnClass(redis.clients.jedis.Jedis.class)
    @ConditionalOnMissingBean(value = NbRedis.class)
    static class Jedis {

        @Bean
        @ConditionalOnMissingBean
        public NbRedis jedisRedis() {
            return new JedisRedis();
        }
    }

}
