package org.lanjerry.admin.dto.sys;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户更新参数")
public class SysUserUpdateDTO extends BaseEntity {

    /**
     * 帐号
     */
    @NotBlank(message = "帐号不能为空")
    @ApiModelProperty(value = "帐号", example = "admin", required = true, position = 10)
    private String account;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称", example = "管理员", required = true, position = 20)
    private String name;

    /**
     * 性别 1.男 2.女 3.未知
     */
    @ApiModelProperty(value = "性别 1.男 2.女 3.未知", example = "MALE", required = true, position = 30)
    private SysUserSexEnum sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "38046851@qq.com", position = 40)
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13623012336", position = 50)
    private String phone;

    /**
     * 状态 1.启用 2.停用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "ENABLE", required = true, position = 60)
    private SysUserStatusEnum status;

    /**
     * 角色列表
     */
    @NotNull(message = "角色不能为空")
    @Size(min = 1, message = "请至少选择一个角色")
    @ApiModelProperty(value = "角色列表", example = "[1,2,3]", required = true, position = 70)
    private List<Integer> roleIds;
}
