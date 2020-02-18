package org.lanjerry.admin.config.global;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * <p>
 * 代码生成的配置
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-16
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class ToolGenConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 默认生成包路径
     */
    private String packageName;

    /**
     * 自动去除表前缀
     */
    private boolean autoRemovePre;
}
