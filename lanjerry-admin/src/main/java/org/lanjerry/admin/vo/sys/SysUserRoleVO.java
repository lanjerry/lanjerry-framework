package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户角色列表VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleVO extends BaseEntity {

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号", example = "1", position = 10)
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", example = "管理员", position = 20)
    private String name;
}
