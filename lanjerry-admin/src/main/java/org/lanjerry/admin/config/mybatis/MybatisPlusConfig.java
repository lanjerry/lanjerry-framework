package org.lanjerry.admin.config.mybatis;

import org.lanjerry.admin.plugin.DemoPlugin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
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
@MapperScan("org.lanjerry.admin.mapper")
public class MybatisPlusConfig {

    /**
     * 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 演示模式开关
     */
    @Value("${demo.enable}")
    private boolean enableDemo;

    /**
     * 演示模式插件配置
     */
    @Bean
    public ConfigurationCustomizer customizer() {
        return config -> {
            if (enableDemo) {
                config.addInterceptor(new DemoPlugin());
            }
        };
    }
}