package org.lanjerry.admin.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 * Swagger2配置
 * </p>
 *
 * @author lanjerry
 * @since 2020-05-26
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public SwaggerConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket api() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        tokenPar.name("Authorization").description("身份认证Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(tokenPar.build());
        String groupName = "1.0版本";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(groupName))
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("<table><tbody>" +
                        "<tr><td colspan='2'>状态码</td></tr>" +
                        "<tr><td>200</td><td>成功</td></tr>" +
                        "<tr><td>201</td><td>操作失败</td></tr>" +
                        "<tr><td>202</td><td>系统异常</td></tr>" +
                        "<tr><td>203</td><td>用户未登录或身份异常</td></tr>" +
                        "<tr><td>400</td><td>参数错误</td></tr>" +
                        "<tr><td>401</td><td>权限不足</td></tr>" +
                        "<tr><td colspan='2'>常用api</td></tr>" +
                        "<tr><td>账户登录</td><td>/sys/user/login</td></tr>" +
                        "<tr><td>账户注销登录</td><td>/sys/user/logout</td></tr>" +
                        "<tr><td>上传</td><td>/util/upload</td></tr>" +
                        "</tbody></table>")
                .contact(new Contact("lanjerry", "https://gitee.com/lanjerry/lanjerry-framework", "38046851@qq.com"))
                .version("1.0")
                .build();
    }
}
