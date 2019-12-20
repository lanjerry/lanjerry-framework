package org.lanjerry.admin.service.sys;

import java.util.List;

import org.lanjerry.admin.dto.sys.SysRolePageDTO;
import org.lanjerry.admin.dto.sys.SysRoleSaveOrUpdateDTO;
import org.lanjerry.admin.vo.sys.SysRoleInfoVO;
import org.lanjerry.admin.vo.sys.SysRolePageVO;
import org.lanjerry.admin.vo.sys.SysUserRoleVO;
import org.lanjerry.common.core.entity.sys.SysRole;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页获取系统角色列表
     *
     * @author lanjerry
     * @since 2019/9/5 11:55
     * @param dto 系统角色列表查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.sys.SysRolePageVO>
     */
    IPage<SysRolePageVO> pageRoles(SysRolePageDTO dto);

    /**
     * 根据角色编号查询系统角色信息
     *
     * @author lanjerry
     * @since 2019/12/19 9:34
     * @param id 角色编号
     * @return org.lanjerry.admin.vo.sys.SysRoleInfoVO
     */
    SysRoleInfoVO getInfoById(int id);

    /**
     * 新增系统角色
     *
     * @author lanjerry
     * @since 2019/9/5 11:55
     * @param dto 系统角色新增参数
     */
    void saveRole(SysRoleSaveOrUpdateDTO dto);

    /**
     * 更新系统角色
     *
     * @author lanjerry
     * @since 2019/9/5 11:55
     * @param id 角色编号
     * @param dto 系统角色更新参数
     */
    void updateRole(int id, SysRoleSaveOrUpdateDTO dto);

    /**
     * 删除系统角色
     *
     * @author lanjerry
     * @since 2019/9/5 11:55
     * @param ids 角色编号集
     */
    void removeRoles(Integer[] ids);

    /**
     * 根据角色编号查询系统角色的权限编号集
     *
     * @author lanjerry
     * @since 2019/12/19 9:36
     * @param id 角色编号
     * @return java.util.List<java.lang.Integer>
     */
    List<Integer> getPermissionIds(int id);

    /**
     * 获取用户角色列表
     * 
     * @author lanjerry
     * @since 2019/12/18 11:46
     * @return java.util.List<org.lanjerry.admin.vo.sys.SysUserRoleVO>
     */
    List<SysUserRoleVO> listRoles();
}
