package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户登录DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserLoginDTO extends BaseEntity {

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
}
