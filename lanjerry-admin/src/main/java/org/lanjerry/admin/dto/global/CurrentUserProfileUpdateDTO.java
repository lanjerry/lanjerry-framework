package org.lanjerry.admin.dto.global;

import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 当前登录用户基本资料更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("当前登录用户基本资料更新参数")
public class CurrentUserProfileUpdateDTO extends BaseEntity {

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 10)
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "38046851@qq.com", position = 20)
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13623012336", position = 30)
    private String phone;

    /**
     * 性别 1.男 2.女 3.未知
     */
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别 1.男 2.女 3.未知", example = "MALE", required = true, position = 40)
    private SysUserSexEnum sex;
}
