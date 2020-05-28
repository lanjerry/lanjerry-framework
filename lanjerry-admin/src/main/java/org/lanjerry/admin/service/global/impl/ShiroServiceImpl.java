package org.lanjerry.admin.service.global.impl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.lanjerry.admin.mapper.sys.SysPermissionMapper;
import org.lanjerry.admin.mapper.sys.SysRoleMapper;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

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
    private SysRoleService roleService;

    @Autowired
    private SysPermissionService permissionService;

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
        result = ((SysRoleMapper) roleService.getBaseMapper()).getPermissionTagsByUserId(id);
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
        result = ((SysPermissionMapper) permissionService.getBaseMapper()).getPermissionsByUserId(SysPermissionTypeEnum.BUTTON.getValue(), id);

        // 把数据存储到redis中，过期时间为15天
        try {
            RedisUtil.setFromString(AdminConsts.REDIS_SYS_USER_PERMISSION.concat(String.valueOf(id)), JSONUtil.toJsonStr(result), AdminConsts.REDIS_SYS_USER_PERMISSION_ROUTER_EXPIRE_TIME);
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
        result.put("/util/**/**", "anon");// 工具

        // 其他全部需要鉴权
        result.put("/**", "authcToken"); // 默认进行用户鉴权
        return result;
    }
}