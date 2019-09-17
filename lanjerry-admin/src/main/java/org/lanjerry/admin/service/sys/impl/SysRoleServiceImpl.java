package org.lanjerry.admin.service.sys.impl;

import java.util.List;

import org.lanjerry.admin.dto.sys.SysRolePageDTO;
import org.lanjerry.admin.dto.sys.SysRoleSaveOrUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysRoleMapper;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRolePermissionService;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.vo.sys.SysRolePageVO;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRole;
import org.lanjerry.common.core.entity.sys.SysRolePermission;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
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
 * 系统角色表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysPermissionService permissionService;

    private final SysRolePermissionService rolePermissionService;

    @Override
    public IPage<SysRolePageVO> pageRoles(SysRolePageDTO dto) {
        IPage<SysRole> page = this.lambdaQuery().orderByDesc(SysRole::getId)
                .eq(dto.getId() != null, SysRole::getId, dto.getId())
                .like(StrUtil.isNotBlank(dto.getName()), SysRole::getName, dto.getName())
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        IPage<SysRolePageVO> result = BeanCopyUtil.pageCopy(page, SysRole.class, SysRolePageVO.class);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleSaveOrUpdateDTO dto) {
        ApiAssert.isTrue(this.count(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, dto.getName())) == 0, String.format("名称：%s已存在", dto.getName()));
        SysRole role = BeanCopyUtil.beanCopy(dto, SysRole.class);
        this.save(role);

        // 新增角色权限
        this.updateRolePermission(role.getId(), dto.getPermissionIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(int id, SysRoleSaveOrUpdateDTO dto) {
        SysRole oriRole = this.getById(id);
        ApiAssert.notNull(oriRole, String.format("id：%s不存在", id));
        SysRole role = BeanCopyUtil.beanCopy(dto, SysRole.class);
        role.setId(id);
        this.updateById(role);

        // 修改角色权限
        this.updateRolePermission(id, dto.getPermissionIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRole(int id) {
        SysRole oriRole = this.getById(id);
        ApiAssert.notNull(oriRole, String.format("id：%s不存在", id));
        this.removeById(id);

        // 删除角色权限
        this.updateRolePermission(id, null);
    }

    /**
     * 更新角色权限
     *
     * @author lanjerry
     * @since 2019/9/5 16:54
     * @param roleId 角色id
     * @param permissionIds 权限id列表
     */
    private void updateRolePermission(int roleId, List<Integer> permissionIds) {
        // 删除角色权限
        this.rolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, roleId));

        // 新增角色权限
        if (CollectionUtil.isNotEmpty(permissionIds)) {
            permissionService.list(Wrappers.<SysPermission>lambdaQuery().in(SysPermission::getId, permissionIds)).forEach(permission -> {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permission.getId());
                this.rolePermissionService.save(rolePermission);
            });
        }
    }
}
