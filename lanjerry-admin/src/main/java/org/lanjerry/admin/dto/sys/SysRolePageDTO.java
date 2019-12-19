package org.lanjerry.admin.dto.sys;

import org.lanjerry.common.core.bean.SplitPage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色分页查询DTO
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageDTO extends SplitPage {

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号", example = "1", position = 10)
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", example = "销售", position = 20)
    private String name;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", example = "admin", position = 30)
    private String permissionTag;

    /**
     * 创建时间始
     */
    @ApiModelProperty(value = "创建时间始", example = "2018-11-23", position = 40)
    private String createdTimeStart;

    /**
     * 创建时间止
     */
    @ApiModelProperty(value = "创建时间止", example = "2018-11-23", position = 50)
    private String createdTimeEnd;
}
