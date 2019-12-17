package org.lanjerry.admin.service.sys.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserResetPasswordDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveDTO;
import org.lanjerry.admin.dto.sys.SysUserUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysUserMapper;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.sys.SysPermissionFindVO;
import org.lanjerry.admin.vo.sys.SysUserBaseVO;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.admin.vo.sys.SysUserRouterMetaVO;
import org.lanjerry.admin.vo.sys.SysUserRouterVO;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.constant.CommonConsts;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.PermissionTypeEnum;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;
import org.lanjerry.common.core.exception.ApiException;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.lanjerry.common.core.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ShiroService shiroService;

    @Override
    public IPage<SysUserPageVO> pageUsers(SysUserPageDTO dto) {
        IPage<SysUser> page = this.lambdaQuery().orderByDesc(SysUser::getId)
                .eq(dto.getId() != null, SysUser::getId, dto.getId())
                .like(StrUtil.isNotBlank(dto.getAccount()), SysUser::getAccount, dto.getAccount())
                .like(StrUtil.isNotBlank(dto.getName()), SysUser::getName, dto.getName())
                .eq(dto.getStatus() != null, SysUser::getStatus, dto.getStatus())
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        IPage<SysUserPageVO> result = BeanCopyUtil.pageCopy(page, SysUser.class, SysUserPageVO.class);
        result.getRecords().forEach(r -> {
            r.setRoles(shiroService.getRolesById(r.getId()));
        });
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserSaveDTO dto) {
        ApiAssert.isTrue(this.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, dto.getAccount())) == 0, String.format("账号：%s已存在", dto.getAccount()));
        SysUser user = BeanCopyUtil.beanCopy(dto, SysUser.class);
        this.save(user);

        // 设置密码
        user.setPassword(Md5Util.encode(dto.getPassword(), String.valueOf(user.getId())));
        this.updateById(user);

        // 新增用户角色
        this.updateUserRole(user.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(int id, SysUserUpdateDTO dto) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        SysUser user = BeanCopyUtil.beanCopy(dto, SysUser.class);
        user.setId(id);
        this.updateById(user);

        // 修改用户角色
        this.updateUserRole(id, dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Integer[] ids) {
        for (Integer id : ids) {
            ApiAssert.isTrue(id != 1, "id为1的账号不允许删除");
            SysUser oriUser = this.getById(id);
            ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
            this.removeById(id);

            // 删除用户角色
            this.updateUserRole(id, null);
        }
    }

    @Override
    public void changeStatus(int id, SysUserStatusEnum statusEnum) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        SysUser user = new SysUser();
        user.setStatus(statusEnum);
        user.setId(id);
        this.updateById(user);
    }

    @Override
    public void resetPassword(SysUserResetPasswordDTO dto) {
        SysUser oriUser = this.getById(dto.getId());
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", dto.getId()));
        SysUser user = new SysUser();
        user.setPassword(Md5Util.encode(dto.getPassword(), String.valueOf(dto.getId())));
        user.setId(dto.getId());
        this.updateById(user);
    }

    @Override
    public String login(SysUserLoginDTO dto) {
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
    public SysUserInfoVO getInfoById(int id) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        SysUserInfoVO result = BeanCopyUtil.beanCopy(oriUser, SysUserInfoVO.class);
        // 设置角色id集
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        result.setRoleIds(new HashSet(userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList())));
        return result;
    }

    @Override
    public SysUserBaseVO info() {
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        SysUser oriUser = this.getById(token.getId());
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", token.getId()));
        SysUserBaseVO result = BeanCopyUtil.beanCopy(oriUser, SysUserBaseVO.class);
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
    public List<SysUserRouterVO> router() {
        List<SysUserRouterVO> result = new ArrayList<>();
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        Set<String> userPermissions = new HashSet<>();
        if (!CommonConsts.DEFAULT_ADMIN_ACCOUNT.equals(token.getAccount())) {
            userPermissions = shiroService.getPermissionsById(token.getId());
        }
        List<SysPermission> permissions = permissionService.lambdaQuery()
                .orderByAsc(SysPermission::getSort)
                .eq(SysPermission::getType, PermissionTypeEnum.MENU)
                .eq(SysPermission::getHiddenFlag, false)
                .in(CollectionUtil.isNotEmpty(userPermissions), SysPermission::getPermission, userPermissions)
                .list();
        List<SysPermissionFindVO> treePermissions = permissionService.listPermissions(permissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
        result = this.buildRouters(treePermissions);
        return result;
    }

    private List<SysUserRouterVO> buildRouters(List<SysPermissionFindVO> treePermissions) {
        List<SysUserRouterVO> result = new ArrayList<>();
        treePermissions.forEach(p -> {
            SysUserRouterVO router = new SysUserRouterVO();
            router.setName(StrUtil.upperFirst(p.getPath()));
            router.setPath(AdminConsts.SYS_PERMISSION_PARENT_ID.equals(p.getParentId()) && !p.getFrameFlag() ? "/".concat(p.getPath()) : p.getPath());
            router.setComponent(StrUtil.isNotBlank(p.getComponent()) ? p.getComponent() : "Layout");
            router.setMeta(SysUserRouterMetaVO.builder().title(p.getName()).icon(p.getIcon()).build());
            if (PermissionTypeEnum.MENU.equals(p.getType()) && CollectionUtil.isNotEmpty(p.getChildrens())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(this.buildRouters(p.getChildrens()));
            }
            result.add(router);
        });
        return result;
    }

    /**
     * 更新用户角色
     *
     * @author lanjerry
     * @since 2019/9/5 16:52
     * @param userId 用户id
     * @param roleIds 角色id列表
     */
    private void updateUserRole(int userId, List<Integer> roleIds) {
        // 删除用户角色
        this.userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));

        // 新增用户角色
        if (CollectionUtil.isNotEmpty(roleIds)) {
            roleService.list(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, roleIds)).forEach(role -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(role.getId());
                this.userRoleService.save(userRole);
            });
        }

        // 清空redis中的系统角色和权限数据
        RedisUtil.remove(AdminConsts.REDIS_SYS_USER_ROLE.concat(String.valueOf(userId)));
        RedisUtil.remove(AdminConsts.REDIS_SYS_USER_PERMISSION.concat(String.valueOf(userId)));
    }
}