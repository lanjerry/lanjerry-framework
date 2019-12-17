package org.lanjerry.admin.vo.sys;

import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;

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
public class SysUserBaseVO extends BaseEntity {

    /**
     * 帐号
     */
    private String account;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 权限集
     */
    private Set<String> permissions;

    /**
     * 角色集
     */
    private Set<String> roles;
}
