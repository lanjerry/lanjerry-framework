package org.lanjerry.admin.config.global;

import org.lanjerry.admin.util.FileUploadUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 资源配置
 * </p>
 *
 * @author lanjerry
 * @since 2020-09-11
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本地文件上传路径
        registry.addResourceHandler(FileUploadUtil.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + FileUploadUtil.UPLOAD_PATH + "/");
    }
}
