package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.PermissionTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限新增DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionSaveDTO extends BaseEntity {

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @ApiModelProperty(value = "权限名称", example = "首页", required = true, position = 10)
    private String name;

    /**
     * 父类id，当值等于0时，代表的是一级的菜单
     */
    @NotNull(message = "父类id不能为空")
    @ApiModelProperty(value = "父类id，当值等于0时，代表的是一级的菜单", example = "0", required = true, position = 20)
    private Integer parentId;

    /**
     * 类型 1.菜单 2.功能
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型 1.菜单（MENU） 2.功能（AUTH）", example = "MENU", required = true, position = 30)
    private PermissionTypeEnum type;

    /**
     * 视图地址
     */
    @ApiModelProperty(value = "视图地址", example = "index", position = 40)
    private String viewAddress;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "/index", position = 50)
    private String permission;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", example = "icon-home", position = 60)
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", position = 70)
    private Integer sort;
}
