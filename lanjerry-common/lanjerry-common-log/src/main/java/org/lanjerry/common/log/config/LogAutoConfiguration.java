package org.lanjerry.common.log.config;

import org.lanjerry.common.log.aspect.SysLogAspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * 日志自动配置类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-17
 */
public class LogAutoConfiguration {

    @Bean
    public SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
        return new SysLogAspect(publisher);
    }
}
