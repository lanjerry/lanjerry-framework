package org.lanjerry.admin.dto.sys;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户新增或者更新DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserSaveDTO extends BaseEntity {

    /**
     * 帐号
     */
    @NotBlank(message = "帐号不能为空")
    @ApiModelProperty(value = "帐号", example = "admin", required = true, position = 10)
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456", required = true, position = 20)
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 30)
    private String name;

    /**
     * 性别 1.男 2.女 3.未知
     */
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别 1.男 2.女 3.未知", example = "1", required = true, position = 40)
    private SysUserSexEnum sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "38046851@qq.com", position = 50)
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13623012336", position = 60)
    private String phone;

    /**
     * 状态 1.启用 2.停用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "1", required = true, position = 70)
    private SysUserStatusEnum status;

    /**
     * 角色列表
     */
    @NotNull(message = "角色不能为空")
    @Size(min = 1, message = "请至少选择一个角色")
    @ApiModelProperty(value = "角色列表", example = "[1,2,3]", required = true, position = 80)
    private List<Integer> roleIds;
}
