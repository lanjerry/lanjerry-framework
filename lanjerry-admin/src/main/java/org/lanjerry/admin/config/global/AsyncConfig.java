package org.lanjerry.admin.config.global;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <p>
 * 异步线程池配置类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-04
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 线程池size
     */
    private int corePoolSize = 10;

    /**
     * 线程池最大数量
     */
    private int maxPoolSize = 20;

    /**
     * 队列最大长度
     */
    private int queueCapacity = 10;

    /**
     * 线程池名称
     */
    private String threadNamePrefix = "lanjerryExecutor-";

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
