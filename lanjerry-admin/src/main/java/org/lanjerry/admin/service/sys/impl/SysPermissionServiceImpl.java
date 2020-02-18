package org.lanjerry.admin.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.lanjerry.admin.dto.sys.SysPermissionSaveOrUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysPermissionMapper;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.service.sys.SysRolePermissionService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.sys.SysPermissionInfoVO;
import org.lanjerry.admin.vo.sys.SysPermissionListVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.entity.sys.SysRolePermission;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.core.enums.sys.SysPermissionTypeEnum;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统权限表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Override
    public List<SysPermissionListVO> listPermissions() {
        List<SysPermission> listPermissions = this.lambdaQuery().orderByAsc(SysPermission::getType).orderByAsc(SysPermission::getSort).list();
        return this.listPermissions(listPermissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
    }

    @Override
    public void savePermission(SysPermissionSaveOrUpdateDTO dto) {
        if (SysPermissionTypeEnum.MENU.equals(dto.getType())) {
            ApiAssert.isTrue(this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getName, dto.getName())) == 0, String.format("名称：%s已存在", dto.getName()));
        }
        SysPermission permission = BeanCopyUtil.beanCopy(dto, SysPermission.class);
        this.save(permission);
    }

    @Override
    public void updatePermission(int id, SysPermissionSaveOrUpdateDTO dto) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("权限编号：%s不存在", id));
        if (SysPermissionTypeEnum.MENU.equals(oriPermission.getType())) {
            ApiAssert.isTrue(this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getName, dto.getName()).ne(SysPermission::getId, id)) == 0, String.format("名称：%s已存在", dto.getName()));
        }
        SysPermission permission = BeanCopyUtil.beanCopy(dto, SysPermission.class);
        permission.setId(id);
        this.updateById(permission);

        // 清空redis中的所有系统权限数据
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat("*")))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removePermission(int id) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("权限编号：%s不存在", id));
        ApiAssert.isTrue(this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getParentId, id)) == 0, String.format("权限：%s存在子菜单，不允许删除", oriPermission.getName()));
        ApiAssert.isTrue(rolePermissionService.count(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getPermissionId, id)) == 0, String.format("权限：%s存在已分配，不允许删除", oriPermission.getName()));

        this.removeById(id);

        // 清空redis中的所有系统权限数据
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat("*")))));
    }

    @Override
    public List<SysPermissionListVO> listPermissions(List<SysPermission> permissions, Integer parentId) {
        List<SysPermissionListVO> result = new ArrayList<>();
        permissions.forEach(permission -> {
            if (parentId.equals(permission.getParentId())) {
                SysPermissionListVO permissionVO = BeanCopyUtil.beanCopy(permission, SysPermissionListVO.class);
                permissionVO.setChildren(this.listPermissions(permissions, permission.getId()));
                result.add(permissionVO);
            }
        });
        return result;
    }

    @Override
    public void changePermissionStatus(int id, SysPermissionStatusEnum statusEnum) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("权限编号：%s不存在", id));
        SysPermission permission = new SysPermission();
        permission.setStatus(statusEnum);
        permission.setId(id);
        this.updateById(permission);

        // 清空redis中的所有系统权限数据
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat("*")))));
    }

    @Override
    public SysPermissionInfoVO getPermission(int id) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("权限编号：%s不存在", id));
        return BeanCopyUtil.beanCopy(oriPermission, SysPermissionInfoVO.class);
    }

    @Override
    public List<SysPermissionTreeVO> treePermissions() {
        List<SysPermission> permissions = this.lambdaQuery()
                .orderByAsc(SysPermission::getSort)
                .list();
        return this.treePermissions(permissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
    }

    /**
     * 递归获取树形结构系统权限列表
     *
     * @author lanjerry
     * @since 2019/12/18 14:35
     * @param permissions 系统权限列表
     * @param parentId 父类id
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionTreeVO>
     */
    private List<SysPermissionTreeVO> treePermissions(List<SysPermission> permissions, Integer parentId) {
        List<SysPermissionTreeVO> result = new ArrayList<>();
        permissions.forEach(permission -> {
            if (parentId.equals(permission.getParentId())) {
                SysPermissionTreeVO permissionVO = new SysPermissionTreeVO();
                permissionVO.setId(permission.getId());
                permissionVO.setLabel(permission.getName());
                permissionVO.setChildren(this.treePermissions(permissions, permission.getId()));
                result.add(permissionVO);
            }
        });
        return result;
    }
}
