package org.lanjerry.admin.service.sys.impl;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserResetPasswordDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveDTO;
import org.lanjerry.admin.dto.sys.SysUserUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysUserMapper;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.service.sys.SysUserRoleService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.entity.sys.SysUserRole;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;
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

import cn.hutool.core.collection.CollUtil;
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
            // 设置角色名称
            List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, r.getId()).list();
            if (CollUtil.isNotEmpty(userRoles)) {
                // 获取角色
                List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList());
                List<SysRole> roles = roleService.lambdaQuery().select(SysRole::getName).in(SysRole::getId, roleIds).list();
                r.setRoles(new HashSet(roles.stream().map(SysRole::getName).distinct().collect(Collectors.toList())));
            }
        });
        return result;
    }

    @Override
    public SysUserInfoVO getUser(int id) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", id));
        SysUserInfoVO result = BeanCopyUtil.beanCopy(oriUser, SysUserInfoVO.class);
        // 设置角色id集
        List<SysUserRole> userRoles = userRoleService.lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id).list();
        result.setRoleIds(new HashSet(userRoles.stream().map(SysUserRole::getRoleId).distinct().collect(Collectors.toList())));
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
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", id));
        SysUser user = BeanCopyUtil.beanCopy(dto, SysUser.class);
        user.setId(id);
        this.updateById(user);

        // 修改用户角色
        this.updateUserRole(id, dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsers(Integer[] ids) {
        for (Integer id : ids) {
            ApiAssert.isTrue(id != 1, "管理员的账号不允许删除");
            SysUser oriUser = this.getById(id);
            ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", id));
            this.removeById(id);

            // 删除用户角色
            this.updateUserRole(id, null);
        }
    }

    @Override
    public void changeUserStatus(int id, SysUserStatusEnum statusEnum) {
        SysUser oriUser = this.getById(id);
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", id));
        SysUser user = new SysUser();
        user.setStatus(statusEnum);
        user.setId(id);
        this.updateById(user);
    }

    @Override
    public void resetUserPwd(SysUserResetPasswordDTO dto) {
        SysUser oriUser = this.getById(dto.getId());
        ApiAssert.notNull(oriUser, String.format("用户编号：%s不存在", dto.getId()));
        SysUser user = new SysUser();
        user.setPassword(Md5Util.encode(dto.getPassword(), String.valueOf(dto.getId())));
        user.setId(dto.getId());
        this.updateById(user);
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