package org.lanjerry.admin.service.sys;

import java.util.List;

import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserResetPasswordDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveDTO;
import org.lanjerry.admin.dto.sys.SysUserUpdateDTO;
import org.lanjerry.admin.vo.sys.SysUserCurrentVO;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.admin.vo.sys.SysUserRouterVO;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;

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
     * 分页查询系统用户列表
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param dto 系统用户列表查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.sys.SysUserPageVO>
     */
    IPage<SysUserPageVO> pageUsers(SysUserPageDTO dto);

    /**
     * 根据用户编号查询用户信息
     *
     * @author lanjerry
     * @since 2019/12/17 10:59
     * @param id 用户编号
     * @return org.lanjerry.admin.vo.sys.SysUserInfoVO
     */
    SysUserInfoVO getUser(int id);

    /**
     * 新增系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param dto 系统用户新增参数
     */
    void saveUser(SysUserSaveDTO dto);

    /**
     * 更新系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id 用户编号
     * @param dto 系统用户更新参数
     */
    void updateUser(int id, SysUserUpdateDTO dto);

    /**
     * 删除系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param ids 用户编号集
     */
    void removeUsers(Integer[] ids);

    /**
     * 锁定或者解锁系统用户
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param id 用户编号
     * @param statusEnum 系统用户状态枚举
     */
    void changeUserStatus(int id, SysUserStatusEnum statusEnum);

    /**
     * 重置系统用户密码
     *
     * @author lanjerry
     * @since 2019/9/5 11:53
     * @param dto 系统用户重置密码参数
     */
    void resetUserPwd(SysUserResetPasswordDTO dto);

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
     * 查询当前登录用户信息
     *
     * @author lanjerry
     * @since 2019/11/22 17:42
     * @return org.lanjerry.admin.vo.sys.SysUserInfoVO
     */
    SysUserCurrentVO getCurrentUserinfo();

    /**
     * 查询当前登录用户的路由信息
     *
     * @author lanjerry
     * @since 2019/12/10 16:37
     * @return org.lanjerry.admin.vo.sys.SysUserRouterVO
     */
    List<SysUserRouterVO> router();
}
