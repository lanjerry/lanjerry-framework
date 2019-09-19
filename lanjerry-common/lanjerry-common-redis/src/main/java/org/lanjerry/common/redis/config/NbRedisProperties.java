package org.lanjerry.common.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * <p>
 * redis配置属性类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-18
 */
@Data
@ConfigurationProperties(prefix = "lanjerry.redis")
public class NbRedisProperties {

    /**
     * 数据库
     */
    private int database = 0;

    /**
     * 连接地址
     */
    private String host = "localhost";

    /**
     * 连接端口
     */
    private int port;

    /**
     * 连接密码
     */
    private String password;
}