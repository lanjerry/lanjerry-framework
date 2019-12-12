package org.lanjerry.admin.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.lanjerry.admin.dto.sys.SysPermissionSaveDTO;
import org.lanjerry.admin.dto.sys.SysPermissionUpdateDTO;
import org.lanjerry.admin.mapper.sys.SysPermissionMapper;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.RedisUtil;
import org.lanjerry.admin.vo.sys.SysPermissionFindVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.enums.PermissionTypeEnum;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

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

    @Override
    public List<SysPermissionFindVO> listPermissions() {
        List<SysPermission> listPermissions = this.lambdaQuery().orderByAsc(SysPermission::getType).orderByAsc(SysPermission::getSort).list();
        return this.listPermissions(listPermissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
    }

    @Override
    public List<SysPermissionTreeVO> treePermissions() {
        List<SysPermission> listPermissions = this.lambdaQuery().list();
        return this.treePermissions(listPermissions, AdminConsts.SYS_PERMISSION_PARENT_ID);
    }

    @Override
    public void savePermission(SysPermissionSaveDTO dto) {
        if (PermissionTypeEnum.MENU.equals(dto.getType())) {
            ApiAssert.isTrue(this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getName, dto.getName())) == 0, String.format("名称：%s已存在", dto.getName()));
        }
        SysPermission permission = BeanCopyUtil.beanCopy(dto, SysPermission.class);
        this.save(permission);
    }

    @Override
    public void updatePermission(int id, SysPermissionUpdateDTO dto) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("id：%s不存在", id));
        if (PermissionTypeEnum.MENU.equals(oriPermission.getType())) {
            ApiAssert.isTrue(this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getName, dto.getName()).ne(SysPermission::getId, id)) == 0, String.format("名称：%s已存在", dto.getName()));
        }
        SysPermission permission = BeanCopyUtil.beanCopy(dto, SysPermission.class);
        permission.setId(id);
        this.updateById(permission);

        // 清空redis中的所有系统权限数据
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat("*")))));
    }

    @Override
    public void removePermission(int id) {
        SysPermission oriPermission = this.getById(id);
        ApiAssert.notNull(oriPermission, String.format("id：%s不存在", id));
        this.removeById(id);

        // 删除子权限
        this.remove(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getParentId, id));

        // 清空redis中的所有系统权限数据
        RedisUtil.remove(new ArrayList<>(Objects.requireNonNull(RedisUtil.keys(AdminConsts.REDIS_SYS_USER_PERMISSION.concat("*")))));
    }

    /**
     * 递归获取系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 16:52
     * @param permissions 系统权限列表
     * @param parentId 父类id
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionFindVO>
     */
    @Override
    public List<SysPermissionFindVO> listPermissions(List<SysPermission> permissions, Integer parentId) {
        List<SysPermissionFindVO> result = new ArrayList<>();
        permissions.forEach(permission -> {
            if (parentId.equals(permission.getParentId())) {
                SysPermissionFindVO permissionVO = BeanCopyUtil.beanCopy(permission, SysPermissionFindVO.class);
                permissionVO.setChildrens(this.listPermissions(permissions, permission.getId()));
                result.add(permissionVO);
            }
        });
        return result;
    }

    /**
     * 递归获取树形结构系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 16:55
     * @param permissions 系统权限列表
     * @param parentId 父类id
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionTreeVO>
     */
    private List<SysPermissionTreeVO> treePermissions(List<SysPermission> permissions, Integer parentId) {
        List<SysPermissionTreeVO> result = new ArrayList<>();
        permissions.forEach(permission -> {
            if (parentId.equals(permission.getParentId())) {
                SysPermissionTreeVO permissionVO = BeanCopyUtil.beanCopy(permission, SysPermissionTreeVO.class);
                if (AdminConsts.SYS_PERMISSION_PARENT_ID.equals(permissionVO.getParentId())) {
                    permissionVO.setMenus(this.treePermissions(permissions, permission.getId()));
                    permissionVO.setAuths(null);
                } else {
                    permissionVO.setAuths(this.treePermissions(permissions, permission.getId()));
                    permissionVO.setMenus(null);
                }
                result.add(permissionVO);
            }
        });
        return result;
    }
}
