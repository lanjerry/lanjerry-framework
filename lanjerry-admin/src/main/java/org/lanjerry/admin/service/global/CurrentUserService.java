package org.lanjerry.admin.service.global;

import java.util.List;

import org.lanjerry.admin.dto.global.CurrentUserLoginDTO;
import org.lanjerry.admin.dto.global.CurrentUserProfileUpdateDTO;
import org.lanjerry.admin.dto.global.CurrentUserPasswordUpdateDTO;
import org.lanjerry.admin.vo.global.CurrentUserInfoVO;
import org.lanjerry.admin.vo.global.CurrentUserProfileVO;
import org.lanjerry.admin.vo.global.CurrentUserRouterVO;

/**
 * <p>
 * 当前用户 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-25
 */
public interface CurrentUserService {

    /**
     * 当前用户登录
     *
     * @author lanjerry
     * @since 2019/9/16 17:36
     * @param dto 当前用户登录参数
     * @return java.lang.String
     */
    String login(CurrentUserLoginDTO dto);

    /**
     * 查询当前登录用户信息
     *
     * @author lanjerry
     * @since 2019/11/22 17:42
     * @return org.lanjerry.admin.vo.sys.SysUserInfoVO
     */
    CurrentUserInfoVO getCurrentUserInfo();

    /**
     * 查询当前登录用户的路由信息
     *
     * @author lanjerry
     * @since 2019/12/10 16:37
     * @return org.lanjerry.admin.vo.global.CurrentUserRouterVO
     */
    List<CurrentUserRouterVO> router();

    /**
     * 查询当前登录用户基本资料
     *
     * @author lanjerry
     * @since 2019/12/24 17:55
     * @return org.lanjerry.admin.vo.global.CurrentUserProfileVO
     */
    CurrentUserProfileVO getCurrentUserProfile();

    /**
     * 更新当前登录用户基本资料
     *
     * @author lanjerry
     * @since 2019/12/24 17:55
     * @param dto 当前登录用户基本资料更新参数
     */
    void updateCurrentUserProfile(CurrentUserProfileUpdateDTO dto);

    /**
     * 更新当前登录用户密码
     *
     * @author lanjerry
     * @since 2019/12/24 17:56
     * @param dto 当前登录用户密码更新参数
     */
    void updateCurrentUserPassword(CurrentUserPasswordUpdateDTO dto);

    /**
     * 退出登录
     *
     * @author lanjerry
     * @since 2019/12/25 14:32
     */
    void logout();
}
