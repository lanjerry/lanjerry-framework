package org.lanjerry.admin.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Swagger2配置
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("身份认证Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.lanjerry.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台接口文档")
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
                .version("1.0")
                .build();
    }
}
