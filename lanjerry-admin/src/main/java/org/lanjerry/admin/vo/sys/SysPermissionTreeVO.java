package org.lanjerry.admin.vo.sys;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限树形结构列表查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionTreeVO extends BaseEntity {

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
     * 菜单列表
     */
    @ApiModelProperty(value = "菜单列表", position = 40)
    private List<SysPermissionTreeVO> menus;

    /**
     * 按钮列表
     */
    @ApiModelProperty(value = "按钮列表", position = 50)
    private List<SysPermissionTreeVO> auths;
}
