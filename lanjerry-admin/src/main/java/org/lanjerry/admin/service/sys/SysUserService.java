package org.lanjerry.admin.service.sys;

import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveOrUpdateDTO;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.enums.UserStatusEnum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页获取系统用户列表
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param dto 系统用户列表查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.sys.SysUserPageVO>
     */
    IPage<SysUserPageVO> pageUsers(SysUserPageDTO dto);

    /**
     * 新增系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param dto 系统用户新增参数
     */
    void saveUser(SysUserSaveOrUpdateDTO dto);

    /**
     * 更新系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id id
     * @param dto 系统用户更新参数
     */
    void updateUser(int id, SysUserSaveOrUpdateDTO dto);

    /**
     * 删除系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id id
     */
    void removeUser(int id);

    /**
     * 锁定或者解锁系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id id
     * @param statusEnum 系统用户状态枚举
     */
    void statusChange(int id, UserStatusEnum statusEnum);

    /**
     * 重置系统用户密码
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id id
     */
    void resetPassword(int id);

    /**
     * 系统用户登录
     *
     * @author lanjerry
     * @since 2019/9/16 17:36
     * @param dto 系统用户登录参数
     * @return java.lang.String
     */
    String login(SysUserLoginDTO dto);

    /**
     * 获取用户信息
     *
     * @author lanjerry
     * @since 2019/11/22 17:42
     * @return org.lanjerry.admin.vo.sys.SysUserInfoVO
     */
    SysUserInfoVO info();
}
