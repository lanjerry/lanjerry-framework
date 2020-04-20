package org.lanjerry.admin.dto.sys;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色新增或者更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统角色新增或者更新参数")
public class SysRoleSaveOrUpdateDTO extends BaseEntity {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", example = "销售", required = true, position = 10)
    private String name;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    @ApiModelProperty(value = "权限标识", example = "admin", required = true, position = 20)
    private String permissionTag;

    /**
     * 权限列表
     */
    @NotNull(message = "权限不能为空")
    @Size(min = 1, message = "请至少选择一个权限")
    @ApiModelProperty(value = "权限列表", example = "[1,2,3]", required = true, position = 30)
    private List<Integer> permissionIds;
}
