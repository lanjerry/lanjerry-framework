package org.lanjerry.admin.vo.sys;

import java.time.LocalDateTime;
import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限列表查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionListVO extends BaseEntity {

    /**
     * 权限编号
     */
    @ApiModelProperty(value = "权限编号", example = "1", position = 10)
    private Integer id;

    /**
     * 父类id，当值等于0时，代表的是一级的菜单
     */
    @ApiModelProperty(value = "父类id，当值等于0时，代表的是一级的菜单", example = "0", position = 20)
    private Integer parentId;

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
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", example = "user", position = 50)
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", example = "sys/user/index", position = 60)
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "sys:user:page", position = 70)
    private String permission;

    /**
     * 状态 1.启用 2.停用
     */
    @ApiModelProperty(value = "状态 1.启用 2.停用", position = 80)
    private SysPermissionStatusEnum status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", position = 90)
    private Integer sort;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "管理员", position = 100)
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22 15:57:53", position = 110)
    private LocalDateTime createdTime;

    /**
     * 是否外链 0.否 1.是
     */
    @ApiModelProperty(value = "是否外链 0.否 1.是", example = "false", position = 120)
    private Boolean frameFlag;

    /**
     * 类型 1.菜单 2.按钮
     */
    @ApiModelProperty(value = "类型 1.菜单 2.按钮", position = 130)
    private SysPermissionTypeEnum type;

    /**
     * 子权限列表
     */
    @ApiModelProperty(value = "子权限列表", position = 140)
    private List<SysPermissionListVO> children;
}
