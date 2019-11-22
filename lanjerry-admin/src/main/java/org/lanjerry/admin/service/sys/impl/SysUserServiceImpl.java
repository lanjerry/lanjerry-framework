package org.lanjerry.admin.service.sys.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveOrUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysUserMapper;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
import org.lanjerry.common.auth.shiro.service.ShiroService;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.UserStatusEnum;
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
    private ShiroService shiroService;

    @Override
    public IPage<SysUserPageVO> pageUsers(SysUserPageDTO dto) {
        IPage<SysUser> page = this.lambdaQuery().orderByDesc(SysUser::getId)
                .eq(dto.getId() != null, SysUser::getId, dto.getId())
                .like(StrUtil.isNotBlank(dto.getAccount()), SysUser::getAccount, dto.getAccount())
                .like(StrUtil.isNotBlank(dto.getName()), SysUser::getName, dto.getName())
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        IPage<SysUserPageVO> result = BeanCopyUtil.pageCopy(page, SysUser.class, SysUserPageVO.class);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserSaveOrUpdateDTO dto) {
        ApiAssert.isTrue(this.count(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getAccount, dto.getAccount())) == 0, String.format("账号：%s已存在", dto.getAccount()));
        SysUser user = BeanCopyUtil.beanCopy(dto, SysUser.class);
        this.save(user);

        // 设置密码
        user.setPassword(Md5Util.encode(AdminConsts.SYS_USER_DEFAULT_PASSWORD, String.valueOf(user.getId())));
        this.updateById(user);

        // 新增用户角色
        this.updateUserRole(user.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(int id, SysUserSaveOrUpdateDTO dto) {
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
    public void removeUser(int id) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        this.removeById(id);

        // 删除用户角色
        this.updateUserRole(id, null);
    }

    @Override
    public void statusChange(int id, UserStatusEnum statusEnum) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        SysUser user = new SysUser();
        user.setStatus(statusEnum);
        user.setId(id);
        this.updateById(user);
    }

    @Override
    public void resetPassword(int id) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("id：%s不存在", id));
        SysUser user = new SysUser();
        user.setPassword(Md5Util.encode(AdminConsts.SYS_USER_DEFAULT_PASSWORD, String.valueOf(id)));
        user.setId(id);
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
    public SysUserInfoVO info() {
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        SysUserInfoVO result = new SysUserInfoVO();
        result.setAccount(token.getAccount());
        result.setName(token.getName());
        result.setRoles(shiroService.getRolesById(token.getId()));
        result.setPermissions(shiroService.getPermissionsById(token.getId()));
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