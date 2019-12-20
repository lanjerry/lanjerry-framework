package org.lanjerry.admin.config.swagger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

/**
 * <p>
 * 拓展枚举在swagger上的显示
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Component
public class CodedEnumPropertyPlugin implements ModelPropertyBuilderPlugin {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Override
    public void apply(ModelPropertyContext context) {
        if (!enableSwagger) {
            return;
        }

        Optional<ApiModelProperty> annotation = Optional.absent();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }
        final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
        //过滤得到目标类型
        if (annotation.isPresent() && Enum.class.isAssignableFrom(rawPrimaryType)) {
            //获取CodedEnum的code值
            Enum[] values = (Enum[]) rawPrimaryType.getEnumConstants();
            final List<String> displayValues = Arrays.stream(values).map(codedEnum -> codedEnum.name()).collect(Collectors.toList());
            final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
            //固定设置为int类型
            final ResolvedType resolvedType = context.getResolver().resolve(String.class);
            context.getBuilder().allowableValues(allowableListValues).type(resolvedType);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
