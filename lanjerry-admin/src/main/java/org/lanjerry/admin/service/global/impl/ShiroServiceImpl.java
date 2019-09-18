package org.lanjerry.admin.service.global.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRolePermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysRolePermission;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.PermissionTypeEnum;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

/**
 * <p>
 * shiro服务类实现
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-10
 */
@Service
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {

    private final SysUserService userService;

    private final SysUserRoleService userRoleService;

    private final SysRoleService roleService;

    private final SysRolePermissionService rolePermissionService;

    private final SysPermissionService permissionService;

    @Override
    public Object getLoginByAccount(String account) {
        if (StrUtil.isBlank(account)) {
            return null;
        }
        return userService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, account).last("limit 1"));
    }

    @Override
    public Set<String> getRolesById(int id) {
        Set<String> result = new HashSet<>();
        if (id == 0) {
            return result;
        }
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        if (CollUtil.isEmpty(userRoles)) {
            return result;
        }
        // 获取角色
        List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
        List<SysRole> roles = roleService.lambdaQuery().select(SysRole::getName).in(SysRole::getId, roleIds).list();
        result = new HashSet(roles.stream().map(SysRole::getName).distinct().collect(Collectors.toList()));
        return result;
    }

    @Override
    public Set<String> getPermissionsById(int id) {
        Set<String> result = new HashSet<>();
        if (id == 0) {
            return result;
        }
        // 获取用户角色
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        if (CollUtil.isEmpty(userRoles)) {
            return result;
        }
        // 获取角色权限
        List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
        List<SysRolePermission> rolePermissions = rolePermissionService.lambdaQuery().select(SysRolePermission::getPermissionId).in(SysRolePermission::getRoleId, roleIds).list();
        if (CollUtil.isEmpty(rolePermissions)) {
            return result;
        }
        // 获取权限标识
        List<Integer> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).distinct().collect(Collectors.toList());
        List<SysPermission> permissions = permissionService.lambdaQuery().select(SysPermission::getPermission).eq(SysPermission::getType, PermissionTypeEnum.AUTH).in(SysPermission::getId, permissionIds).list();
        result = new HashSet(permissions.stream().map(SysPermission::getPermission).distinct().collect(Collectors.toList()));
        return result;
    }
}