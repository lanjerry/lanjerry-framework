package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;

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
public class SysPermissionSaveOrUpdateDTO extends BaseEntity {

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
     * 类型 1.菜单 2.按钮
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型 1.菜单 2.按钮", example = "1", required = true, position = 30)
    private SysPermissionTypeEnum type;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", example = "user", position = 40)
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", example = "sys/user", position = 50)
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "sys:user", position = 60)
    private String permission;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", example = "icon-home", position = 70)
    private String icon;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序", example = "1", required = true, position = 80)
    private Integer sort;

    /**
     * 状态 1.启用 2.停用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "1", required = true, position = 90)
    private SysPermissionStatusEnum status;

    /**
     * 是否外链 0.否 1.是
     */
    @NotNull(message = "是否外链不能为空")
    @ApiModelProperty(value = "是否外链 0.否 1.是", example = "true", required = true, position = 100)
    private Boolean frameFlag;
}