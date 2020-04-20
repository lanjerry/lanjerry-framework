package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统权限信息")
public class SysPermissionInfoVO extends BaseEntity {

    /**
     * 父类id，当值等于0时，代表的是一级的菜单
     */
    @ApiModelProperty(value = "父类id，当值等于0时，代表的是一级的菜单", example = "0", position = 10)
    private Integer parentId;

    /**
     * 类型 1.菜单 2.按钮
     */
    @ApiModelProperty(value = "类型 1.菜单 2.按钮", example = "{\"value\": 1,\"text\": \"菜单\",\"name\": \"MENU\"}", position = 20)
    private SysPermissionTypeEnum type;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", example = "icon-home", position = 30)
    private String icon;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称", example = "首页", position = 40)
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", position = 50)
    private Integer sort;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", example = "user", position = 60)
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", example = "sys/user/index", position = 70)
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "sys:user:page", position = 80)
    private String permission;

    /**
     * 状态 1.启用 2.停用
     */
    @ApiModelProperty(value = "状态 1.启用 2.停用", example = "{\"value\": 1,\"text\": \"启用\",\"name\": \"ENABLE\"}", position = 90)
    private SysPermissionStatusEnum status;

    /**
     * 是否外链 0.否 1.是
     */
    @ApiModelProperty(value = "是否外链 0.否 1.是", example = "false", position = 100)
    private Boolean frameFlag;
}
