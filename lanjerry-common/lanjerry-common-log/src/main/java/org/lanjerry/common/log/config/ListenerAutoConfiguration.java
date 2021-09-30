package org.lanjerry.common.log.config;

import org.lanjerry.common.log.listener.LogListener;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 日志监听自动装配
 * </p>
 *
 * @author linjierong
 * @since 2021-09-30
 */
public class ListenerAutoConfiguration {

    @Bean
    public LogListener logListener() {
        return new LogListener();
    }
}
