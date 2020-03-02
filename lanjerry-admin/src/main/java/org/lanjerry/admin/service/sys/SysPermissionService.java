package org.lanjerry.admin.service.sys;

import java.util.List;

import org.lanjerry.admin.dto.sys.SysPermissionSaveOrUpdateDTO;
import org.lanjerry.admin.vo.sys.SysPermissionInfoVO;
import org.lanjerry.admin.vo.sys.SysPermissionListVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.entity.sys.SysPermission;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;

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
     * 查询系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 15:15
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionListVO>
     */
    List<SysPermissionListVO> listPermissions();

    /**
     * 根据权限编号查询系统权限信息
     *
     * @author lanjerry
     * @since 2019/12/19 17:44
     * @param id 权限编号
     * @return org.lanjerry.admin.vo.sys.SysPermissionInfoVO
     */
    SysPermissionInfoVO getPermission(int id);

    /**
     * 新增系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param dto 系统权限新增参数
     */
    void savePermission(SysPermissionSaveOrUpdateDTO dto);

    /**
     * 更新系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param id 权限编号
     * @param dto 系统权限更新参数
     */
    void updatePermission(int id, SysPermissionSaveOrUpdateDTO dto);

    /**
     * 删除系统权限
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @param id 权限编号
     */
    void removePermission(int id);

    /**
     * 启用或者停用系统权限
     *
     * @author lanjerry
     * @since 2019/12/24 16:01
     * @param id 权限编号
     * @param statusEnum statusEnum 系统权限状态枚举
     */
    void changePermissionStatus(int id, SysPermissionStatusEnum statusEnum);

    /**
     * 查询树形结构系统权限列表
     *
     * @author lanjerry
     * @since 2019/9/5 14:56
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionTreeVO>
     */
    List<SysPermissionTreeVO> treePermissions();

    /**
     * 递归处理权限列表，重组成树形结构
     *
     * @author lanjerry
     * @since 2019/12/18 13:57
     * @param permissions 权限列表
     * @param parentId 父级id
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysPermissionListVO>
     */
    List<SysPermissionListVO> listPermissions(List<SysPermission> permissions, Integer parentId);
}
