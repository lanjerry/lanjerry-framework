package org.lanjerry.admin.dto.sys;

import javax.validation.constraints.NotBlank;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限更新DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionUpdateDTO extends BaseEntity {

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称", example = "首页", required = true, position = 10)
    private String name;

    /**
     * 视图地址
     */
    @ApiModelProperty(value = "视图地址", example = "index", position = 20)
    private String viewAddress;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "/index", position = 30)
    private String permission;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", example = "icon-home", position = 40)
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", position = 50)
    private Integer sort;
}
