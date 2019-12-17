package org.lanjerry.admin.vo.sys;

import java.util.Set;

import org.lanjerry.common.core.bean.BaseEntity;
import org.lanjerry.common.core.enums.sys.SysUserSexEnum;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

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
public class SysUserInfoVO extends BaseEntity {

    /**
     * 帐号
     */
    private String account;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别 1.男 2.女 3.未知
     */
    private SysUserSexEnum sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态 1.正常 2.禁用
     */
    private SysUserStatusEnum status;

    /**
     * 角色id集
     */
    private Set<Integer> roleIds;
}
