package org.lanjerry.admin.dto.global;

import javax.validation.constraints.NotBlank;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 当前用户登录DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentUserLoginDTO extends BaseEntity {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", example = "admin", required = true, position = 10)
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456", required = true, position = 20)
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", example = "F7KTP", required = true, position = 30)
    private String captchaCode;

    /**
     * 验证码key
     */
    @NotBlank(message = "验证码key不能为空")
    @ApiModelProperty(value = "验证码key", example = "382545197952684034", required = true, position = 40)
    private String captchaKey;
}
