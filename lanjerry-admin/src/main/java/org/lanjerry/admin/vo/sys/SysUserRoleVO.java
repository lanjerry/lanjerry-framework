package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;
}
