package org.lanjerry.admin.dto.sys;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;

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
public class SysUserSaveOrUpdateDTO extends BaseEntity {

    /**
     * 帐号
     */
    @NotBlank(message = "帐号不能为空")
    @ApiModelProperty(value = "帐号", example = "admin", required = true, position = 10)
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 20)
    private String name;

    /**
     * 角色列表
     */
    @NotNull(message = "角色不能为空")
    @Size(min = 1, message = "请至少选择一个角色")
    @ApiModelProperty(value = "角色列表", example = "[1,2,3]", required = true, position = 30)
    private List<Integer> roleIds;
}
