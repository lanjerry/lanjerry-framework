package org.lanjerry.admin.dto.global;

import javax.validation.constraints.NotBlank;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 当前登录用户密码更新DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentUserPasswordUpdateDTO extends BaseEntity {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码", example = "123456", required = true, position = 10)
    private String oriPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", example = "123456", required = true, position = 20)
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码", example = "123456", required = true, position = 30)
    private String confirmPassword;
}
