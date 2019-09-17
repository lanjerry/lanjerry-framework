package org.lanjerry.admin.service.sys;

import org.lanjerry.admin.dto.sys.SysRolePageDTO;
import org.lanjerry.admin.dto.sys.SysRoleSaveOrUpdateDTO;
import org.lanjerry.admin.vo.sys.SysRolePageVO;
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
     * @param id id
     * @param dto 系统角色更新参数
     */
    void updateRole(int id, SysRoleSaveOrUpdateDTO dto);

    /**
     * 删除系统角色
     *
     * @author lanjerry
     * @since 2019/9/5 11:55
     * @param id id
     */
    void removeRole(int id);
}
