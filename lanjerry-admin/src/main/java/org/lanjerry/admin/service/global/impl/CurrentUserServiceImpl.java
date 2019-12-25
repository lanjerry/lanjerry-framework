package org.lanjerry.admin.service.global.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.lanjerry.admin.dto.global.CurrentUserLoginDTO;
import org.lanjerry.admin.dto.global.CurrentUserPasswordUpdateDTO;
import org.lanjerry.admin.dto.global.CurrentUserProfileUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysUserMapper;
import org.lanjerry.admin.service.global.CurrentUserService;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.global.CurrentUserInfoVO;
import org.lanjerry.admin.vo.global.CurrentUserProfileVO;
import org.lanjerry.admin.vo.global.CurrentUserRouterVO;
import org.lanjerry.admin.vo.sys.SysPermissionListVO;
import org.lanjerry.admin.vo.sys.SysUserRouterMetaVO;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.constant.CommonConsts;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.global.ApiResultCodeEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;
import org.lanjerry.common.core.exception.ApiException;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.lanjerry.common.core.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 当前用户 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-25
 */
@Service
public class CurrentUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements CurrentUserService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ShiroService shiroService;

    @Override
    public String login(CurrentUserLoginDTO dto) {
        String captchaCode = RedisUtil.get(AdminConsts.CAPTCHA_CODE_KEY.concat(dto.getCaptchaKey()), true);
        ApiAssert.isTrue(dto.getCaptchaCode().trim().toLowerCase().equals(captchaCode), "验证码错误或者已失效");
        Subject subject = SecurityUtils.getSubject();
        JwtToken token = JwtToken.builder().account(dto.getAccount()).password(dto.getPassword()).build();
        try {
            subject.login(token);
        } catch (DisabledAccountException e) {
            throw ApiException.argError(e.getMessage());
        } catch (Exception e) {
            throw ApiException.systemError(e.getMessage());
        }
        return ((JwtToken) SecurityUtils.getSubject().getPrincipal()).getToken();
    }

    @Override
    public CurrentUserInfoVO getCurrentUserInfo() {
        // 验证
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        ApiAssert.notNull(token.getId(), ApiResultCodeEnum.NOT_SING_IN.text);
        SysUser oriUser = this.getById(token.getId());
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", token.getId()));
        CurrentUserInfoVO result = BeanCopyUtil.beanCopy(oriUser, CurrentUserInfoVO.class);
        // 设置角色和权限
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        if (CommonConsts.DEFAULT_ADMIN_ACCOUNT.equals(token.getAccount())) {
            roles.add(CommonConsts.DEFAULT_ADMIN_ROLE);
            permissions.add(CommonConsts.DEFAULT_ADMIN_PERMISSION);
        } else {
            roles = shiroService.getRolesById(token.getId());
            permissions = shiroService.getPermissionsById(token.getId());
        }
        result.setRoles(roles);
        result.setPermissions(permissions);
        return result;
    }

    @Override
    public List<CurrentUserRouterVO> router() {
        // 验证
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        ApiAssert.notNull(token.getId(), ApiResultCodeEnum.NOT_SING_IN.text);
        List<CurrentUserRouterVO> result;
        Set<String> userPermissions = new HashSet<>();
        if (!CommonConsts.DEFAULT_ADMIN_ACCOUNT.equals(token.getAccount())) {
            userPermissions = shiroService.getPermissionsById(token.getId());
        }
        List<SysPermission> permissions = permissionService.lambdaQuery()
                .orderByAsc(SysPermission::getSort)
                .eq(SysPermission::getType, SysPermissionTypeEnum.MENU)
                .eq(SysPermission::getStatus, SysPermissionStatusEnum.ENABLE)
                .in(CollectionUtil.isNotEmpty(userPermissions), SysPermission::getPermission, userPermissions)
                .list();
        List<SysPermissionListVO> treePermissions = permissionService.listPermissions(permissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
        result = this.buildRouters(treePermissions);
        return result;
    }

    @Override
    public CurrentUserProfileVO getCurrentUserProfile() {
        // 验证
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        ApiAssert.notNull(token.getId(), ApiResultCodeEnum.NOT_SING_IN.text);
        SysUser oriUser = this.getById(token.getId());
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", token.getId()));
        CurrentUserProfileVO result = BeanCopyUtil.beanCopy(oriUser, CurrentUserProfileVO.class);
        // 设置角色名称
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, token.getId()).list();
        if (CollUtil.isNotEmpty(userRoles)) {
            // 获取角色
            List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
            List<SysRole> roles = roleService.lambdaQuery().select(SysRole::getName).in(SysRole::getId, roleIds).list();
            result.setRoles(new HashSet(roles.stream().map(SysRole::getName).distinct().collect(Collectors.toList())));
        }
        return result;
    }

    @Override
    public void updateCurrentUserProfile(CurrentUserProfileUpdateDTO dto) {
        // 验证
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        ApiAssert.notNull(token.getId(), ApiResultCodeEnum.NOT_SING_IN.text);
        SysUser oriUser = this.getById(token.getId());
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", token.getId()));
        // 更新基本资料
        SysUser user = BeanCopyUtil.beanCopy(dto, SysUser.class);
        user.setId(token.getId());
        this.updateById(user);
    }

    @Override
    public void updateCurrentUserPassword(CurrentUserPasswordUpdateDTO dto) {
        // 验证
        ApiAssert.isTrue(dto.getNewPassword().equals(dto.getConfirmPassword()), "新密码和确认密码输出不一致");
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        ApiAssert.notNull(token.getId(), ApiResultCodeEnum.NOT_SING_IN.text);
        SysUser oriUser = this.getById(token.getId());
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", token.getId()));
        ApiAssert.isTrue(oriUser.getPassword().equals(Md5Util.encode(dto.getOriPassword(), oriUser.getId().toString())), "旧密码错误");
        // 更新密码
        SysUser user = new SysUser();
        user.setPassword(Md5Util.encode(dto.getNewPassword(), String.valueOf(oriUser.getId())));
        user.setId(oriUser.getId());
        this.updateById(user);
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    private List<CurrentUserRouterVO> buildRouters(List<SysPermissionListVO> treePermissions) {
        List<CurrentUserRouterVO> result = new ArrayList<>();
        treePermissions.forEach(p -> {
            CurrentUserRouterVO router = new CurrentUserRouterVO();
            router.setName(StrUtil.upperFirst(p.getPath()));
            router.setPath(AdminConsts.SYS_PERMISSION_PARENT_ID.equals(p.getParentId()) && !p.getFrameFlag() ? "/".concat(p.getPath()) : p.getPath());
            router.setComponent(StrUtil.isNotBlank(p.getComponent()) ? p.getComponent() : "Layout");
            router.setMeta(SysUserRouterMetaVO.builder().title(p.getName()).icon(p.getIcon()).build());
            if (SysPermissionTypeEnum.MENU.equals(p.getType()) && CollectionUtil.isNotEmpty(p.getChildren())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(this.buildRouters(p.getChildren()));
            }
            result.add(router);
        });
        return result;
    }
}