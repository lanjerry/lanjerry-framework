package org.lanjerry.admin.service.sys;

import java.util.List;

import org.lanjerry.admin.dto.sys.SysPermissionSaveDTO;
import org.lanjerry.admin.dto.sys.SysPermissionUpdateDTO;
import org.lanjerry.admin.vo.sys.SysPermissionFindVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.entity.sys.SysPermission;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 获取系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 15:15
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionFindVO>
     */
    List<SysPermissionFindVO> listPermissions();

    /**
     * 新增系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param dto 系统权限新增参数
     */
    void savePermission(SysPermissionSaveDTO dto);

    /**
     * 更新系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param id 权限编号
     * @param dto 系统权限更新参数
     */
    void updatePermission(int id, SysPermissionUpdateDTO dto);

    /**
     * 删除系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param id 权限编号
     */
    void removePermission(int id);

    /**
     * 递归处理权限列表，重组成树形结构
     * 
     * @author lanjerry
     * @since 2019/12/18 13:57
     * @param permissions 权限列表
     * @param parentId 父级id
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionFindVO>
     */
    List<SysPermissionFindVO> listPermissions(List<SysPermission> permissions, Integer parentId);

    /**
     * 获取树形结构系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionTreeVO>
     */
    List<SysPermissionTreeVO> treePermissions();
}
