package org.lanjerry.admin.service.global.impl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRolePermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysRolePermission;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * shiro服务类实现
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-10
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public Object getLoginByAccount(String account) {
        if (StrUtil.isBlank(account)) {
            return null;
        }
        SysUser user = userService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, account).last("limit 1"));
        return user;
    }

    @Override
    public Set<String> getRolesById(int id) {
        Set<String> result = new HashSet<>();
        if (id == 0) {
            return result;
        }
        // 从redis中获取数据
        try {
            String jsonRole = RedisUtil.get(AdminConsts.REDIS_SYS_USER_ROLE.concat(String.valueOf(id)));
            if (StrUtil.isNotBlank(jsonRole)) {
                result = new HashSet<>(JSONUtil.toList(JSONUtil.parseArray(jsonRole), String.class));
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 从数据库中获取数据
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        if (CollUtil.isNotEmpty(userRoles)) {
            // 获取角色
            List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
            List<SysRole> roles = roleService.lambdaQuery().select(SysRole::getPermissionTag).in(SysRole::getId, roleIds).list();
            result = new HashSet(roles.stream().map(SysRole::getPermissionTag).distinct().collect(Collectors.toList()));
        }

        // 把数据存储到redis中，过期时间为15天
        try {
            RedisUtil.setFromString(AdminConsts.REDIS_SYS_USER_ROLE.concat(String.valueOf(id)), JSONUtil.toJsonStr(result), AdminConsts.REDIS_SYS_USER_ROLE_EXPIRE_TIME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Set<String> getPermissionsById(int id) {
        Set<String> result = new HashSet<>();
        if (id == 0) {
            return result;
        }
        // 从redis中获取数据
        try {
            String jsonPermission = RedisUtil.get(AdminConsts.REDIS_SYS_USER_PERMISSION.concat(String.valueOf(id)));
            if (StrUtil.isNotBlank(jsonPermission)) {
                result = new HashSet<>(JSONUtil.toList(JSONUtil.parseArray(jsonPermission), String.class));
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 从数据库中获取数据
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        if (CollUtil.isNotEmpty(userRoles)) {
            // 获取角色权限
            List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
            List<SysRolePermission> rolePermissions = rolePermissionService.lambdaQuery().select(SysRolePermission::getPermissionId).in(SysRolePermission::getRoleId, roleIds).list();
            if (CollUtil.isNotEmpty(rolePermissions)) {
                // 获取权限标识
                List<Integer> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).distinct().collect(Collectors.toList());
                List<SysPermission> permissions = permissionService.lambdaQuery().select(SysPermission::getPermission).eq(SysPermission::getType, SysPermissionTypeEnum.BUTTON).in(SysPermission::getId, permissionIds).list();
                result = new HashSet(permissions.stream().map(SysPermission::getPermission).distinct().collect(Collectors.toList()));
            }
        }

        // 把数据存储到redis中，过期时间为15天
        try {
            RedisUtil.setFromString(AdminConsts.REDIS_SYS_USER_PERMISSION.concat(String.valueOf(id)), JSONUtil.toJsonStr(result), AdminConsts.REDIS_SYS_USER_PERMISSION_EXPIRE_TIME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, String> filterChainDefinitions() {
        Map<String, String> result = new LinkedHashMap<>();
        // swagger的拦截
        result.put("/swagger-resources/**", "anon");
        result.put("/v2/api-docs", "anon");
        result.put("/v2/api-docs-ext", "anon");
        result.put("/doc.html", "anon");
        result.put("/webjars/**", "anon");

        // 不需要验证的api
        result.put("/sys/user/login", "anon");
        result.put("/test/**/**", "anon");// 测试
        result.put("/util/**/**", "anon");// 工具

        // 其他全部需要鉴权
        result.put("/**", "authcToken"); // 默认进行用户鉴权
        return result;
    }
}