package org.lanjerry.admin.listen;

import org.apache.shiro.SecurityUtils;
import org.lanjerry.admin.config.shiro.JwtToken;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.common.core.entity.sys.SysLog;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.lanjerry.common.log.annotation.EnableLog;
import org.lanjerry.common.log.bean.SysLogSaveDTO;
import org.lanjerry.common.log.event.SysLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 异步监听系统日志事件
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-04
 */
@Log4j2
@EnableLog
@Component
@AllArgsConstructor
public class SysLogListener {

    private final SysLogService logService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLogSaveDTO dto = (SysLogSaveDTO) event.getSource();
        SysLog sysLog = BeanCopyUtil.beanCopy(dto, SysLog.class);
        JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        sysLog.setUserId(token != null ? token.getId() : 0);
        logService.save(sysLog);
        log.info("远程操作日志记录成功：{}", dto);
    }
}
