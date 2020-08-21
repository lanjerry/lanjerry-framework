package org.lanjerry.admin.service.sys;

import org.lanjerry.admin.dto.sys.SysNotificationPageDTO;
import org.lanjerry.admin.dto.sys.SysNotificationSaveDTO;
import org.lanjerry.admin.vo.sys.SysNotificationPageVO;
import org.lanjerry.common.core.entity.sys.SysNotification;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统消息服务类
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
public interface SysNotificationService extends IService<SysNotification> {

    /**
     * 分页查询系统消息列表
     *
     * @author lanjerry
     * @since 2020-08-18 11:28:58
     * @param dto 系统消息查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.sys.SysNotificationPageVO>
     */
    IPage<SysNotificationPageVO> pageNotifications(SysNotificationPageDTO dto);

    /**
     * 新增系统消息
     *
     * @author lanjerry
     * @since 2020-08-18 11:28:58
     * @param dto 系统消息新增参数
     */
    void saveNotification(SysNotificationSaveDTO dto);

    /**
     * 查询用户消息通知个数
     *
     * @author lanjerry
     * @since 2020/8/21 17:12
     * @param userId 用户id
     */
    int getNotificationCount(Integer userId);

    /**
     * 已读系统消息
     *
     * @author lanjerry
     * @since 2020/8/21 17:05
     * @param ids 编号数组
     */
    void readNotifications(Integer[] ids);
}
