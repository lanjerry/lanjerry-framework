package org.lanjerry.admin.config.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * <p>
 * Mybatis-Plus配置
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}