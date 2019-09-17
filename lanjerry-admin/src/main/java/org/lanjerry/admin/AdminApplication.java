package org.lanjerry.admin;

import org.mybatis.spring.annotation.MapperScan;
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
@SpringBootApplication
@MapperScan("org.lanjerry.admin.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
