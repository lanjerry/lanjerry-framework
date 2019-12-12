package org.lanjerry.admin.vo.sys;

import java.time.LocalDateTime;
import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.PermissionTypeEnum;

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
public class SysPermissionFindVO extends BaseEntity {

    /**
     * 权限id
     */
    @ApiModelProperty(value = "id", example = "1", position = 10)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "首页", required = true, position = 20)
    private String name;

    /**
     * 父类id，当值等于0时，代表的是一级的菜单
     */
    @ApiModelProperty(value = "父类id，当值等于0时，代表的是一级的菜单", example = "0", position = 30)
    private Integer parentId;

    /**
     * 类型 1.菜单 2.功能
     */
    @ApiModelProperty(value = "类型 1.菜单（MENU） 2.功能（AUTH）",example = "MENU", position = 40)
    private PermissionTypeEnum type;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址",example = "user", position = 50)
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径",example = "sys/user/index", position = 60)
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "sys:user:page", position = 70)
    private String permission;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", example = "icon-home", position = 80)
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", position = 90)
    private Integer sort;

    /**
     * 是否隐藏 0.否 1.是
     */
    @ApiModelProperty(value = "是否隐藏 0.否 1.是", example = "false", position = 100)
    private Boolean hiddenFlag;

    /**
     * 是否外链 0.否 1.是
     */
    @ApiModelProperty(value = "是否外链 0.否 1.是", example = "false", position = 110)
    private Boolean frameFlag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "管理员", position = 120)
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22T15:57:53", position = 130)
    private LocalDateTime createdTime;

    /**
     * 子权限列表
     */
    @ApiModelProperty(value = "子权限列表", position = 140)
    private List<SysPermissionFindVO> childrens;
}
