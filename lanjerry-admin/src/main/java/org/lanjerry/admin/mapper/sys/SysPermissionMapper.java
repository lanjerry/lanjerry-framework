package org.lanjerry.admin.mapper.sys;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.lanjerry.common.core.entity.sys.SysPermission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统权限表 Mapper 接口
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据用户编号查询权限标识集
     *
     * @author lanjerry
     * @since 2019/12/26 18:03
     * @param type 权限类型
     * @param userId 用户编号
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getPermissionsByUserId(@Param("type") Integer type, @Param("userId") Integer userId);

    /**
     * 根据角色编号查询权限编号集
     *
     * @author lanjerry
     * @since 2019/12/27 10:13
     * @param type 权限类型
     * @param roleId 角色编号
     * @return java.util.List<java.lang.Integer>
     */
    List<Integer> getIdsByRoleId(@Param("type") Integer type, @Param("roleId") Integer roleId);
}
