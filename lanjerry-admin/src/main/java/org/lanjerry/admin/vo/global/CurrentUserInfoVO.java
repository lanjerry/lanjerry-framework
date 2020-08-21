package org.lanjerry.admin.vo.global;

import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 当前登录用户信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("当前登录用户信息")
public class CurrentUserInfoVO extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id", example = "1", position = 0)
    private Integer id;

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
    @ApiModelProperty(value = "头像地址", example = "image/touxiang.png", position = 30)
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
