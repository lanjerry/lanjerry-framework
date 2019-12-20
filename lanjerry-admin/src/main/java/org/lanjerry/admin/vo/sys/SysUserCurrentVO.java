package org.lanjerry.admin.vo.sys;

import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户信息VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserCurrentVO extends BaseEntity {

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号", example = "admin", position = 10)
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", example = "管理员", position = 20)
    private String name;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", example = "", position = 30)
    private String avatar;

    /**
     * 权限集
     */
    @ApiModelProperty(value = "权限集", example = "[\"*:*:*\"]", position = 40)
    private Set<String> permissions;

    /**
     * 角色集
     */
    @ApiModelProperty(value = "角色集", example = "[\"admin\"]", position = 50)
    private Set<String> roles;
}
