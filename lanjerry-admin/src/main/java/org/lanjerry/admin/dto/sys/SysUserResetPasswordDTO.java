package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户重置密码DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserResetPasswordDTO extends BaseEntity {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    @ApiModelProperty(value = "用户编号", example = "1", required = true, position = 10)
    private Integer id;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456", required = true, position = 20)
    private String password;
}
