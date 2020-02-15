package org.lanjerry.admin.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.lanjerry.common.core.entity.sys.SysUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 更新用户登录信息
     *
     * @author lanjerry
     * @since 2020/2/16 1:05
     * @param userId 用户编号
     * @param ipAddress IP地址
     */
    void updateLoginInfo(@Param("userId") Integer userId, @Param("ipAddress") String ipAddress);
}