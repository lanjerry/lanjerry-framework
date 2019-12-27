package org.lanjerry.admin.mapper.sys;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.lanjerry.common.core.entity.sys.SysRole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户编号查询角色的权限标识集
     *
     * @author lanjerry
     * @since 2019/12/26 18:00
     * @param userId 用户编号
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getPermissionTagsByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户编号查询角色名称集
     *
     * @author lanjerry
     * @since 2019/12/27 9:58
     * @param userId 用户编号
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getNameByUserId(@Param("userId") Integer userId);
}