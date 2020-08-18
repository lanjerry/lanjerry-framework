package org.lanjerry.admin.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.lanjerry.admin.config.websocket.SessionPool;
import org.lanjerry.admin.dto.sys.SysNotificationPageDTO;
import org.lanjerry.admin.dto.sys.SysNotificationSaveDTO;
import org.lanjerry.admin.mapper.sys.SysNotificationMapper;
import org.lanjerry.admin.service.sys.SysNotificationService;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.vo.sys.SysNotificationPageVO;
import org.lanjerry.common.core.entity.sys.SysNotification;
import org.lanjerry.common.core.entity.sys.SysUser;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 系统消息服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
@Service
public class SysNotificationServiceImpl extends ServiceImpl<SysNotificationMapper, SysNotification> implements SysNotificationService {

    @Autowired
    private SysUserService userService;

    @Override
    public IPage<SysNotificationPageVO> pageNotifications(SysNotificationPageDTO dto) {
        IPage<SysNotification> page = this.lambdaQuery().orderByDesc(SysNotification::getId)
                .ge(StrUtil.isNotBlank(dto.getCreatedTimeStart()), SysNotification::getCreatedTime, dto.getCreatedTimeStart() + AdminConsts.START_TIME)
                .le(StrUtil.isNotBlank(dto.getCreatedTimeEnd()), SysNotification::getCreatedTime, dto.getCreatedTimeEnd() + AdminConsts.END_TIME)
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        IPage<SysNotificationPageVO> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())) {
            List<SysNotificationPageVO> records = new ArrayList<>();
            List<Integer> userIds = page.getRecords().stream().map(SysNotification::getUserId).collect(Collectors.toList());
            List<SysUser> users = userService.lambdaQuery().in(SysUser::getId, userIds).list();
            page.getRecords().forEach(r -> {
                SysNotificationPageVO record = BeanCopyUtil.beanCopy(r, SysNotificationPageVO.class);
                // 设置账号
                Optional<SysUser> user = users.stream().filter(o -> o.getId().equals(r.getUserId())).findFirst();
                user.ifPresent(c -> record.setUserAccount(c.getAccount()));
                records.add(record);
            });
            result.setRecords(records);
        }
        return result;
    }

    @Override
    public void saveNotification(SysNotificationSaveDTO dto) {
        SysNotification notification = BeanCopyUtil.beanCopy(dto, SysNotification.class);
        this.save(notification);
        SessionPool.sendMessage(dto.getUserId(), dto.getContent());
    }
}