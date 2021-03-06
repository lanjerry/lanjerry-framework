package org.lanjerry.admin.vo.sys;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色查询信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统角色查询信息")
public class SysRolePageVO extends BaseEntity {

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
    @ApiModelProperty(value = "权限标识", example = "sale", position = 30)
    private String permissionTag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "管理员", position = 40)
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22 15:57:53", position = 50)
    private LocalDateTime createdTime;
}
