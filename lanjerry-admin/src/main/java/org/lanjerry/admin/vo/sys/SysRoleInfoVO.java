package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色信息VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleInfoVO extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permissionTag;
}
