package org.lanjerry.admin;

import org.lanjerry.common.auth.shiro.annotation.EnableShiro;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Springboot启动类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@EnableShiro
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
