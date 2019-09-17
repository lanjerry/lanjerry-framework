package org.lanjerry.admin.service.sys.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.lanjerry.admin.config.shiro.JwtToken;
import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveOrUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysUserMapper;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.UserStatusEnum;
import org.lanjerry.common.core.exception.ApiException;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.lanjerry.common.core.util.Md5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysRoleService roleService;

    private final SysUserRoleService userRoleService;

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
    }
}