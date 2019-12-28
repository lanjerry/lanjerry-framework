package org.lanjerry.admin.listen;

import org.apache.shiro.SecurityUtils;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.common.auth.shiro.jwt.JwtToken;
import org.lanjerry.common.core.entity.sys.SysLog;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.lanjerry.common.log.annotation.EnableLog;
import org.lanjerry.common.log.bean.SysLogSaveDTO;
import org.lanjerry.common.log.event.SysLogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 异步监听系统日志事件
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-04
 */
@Slf4j
@EnableLog
@Component
public class SysLogListener {

    @Autowired
    private SysLogService logService;

    /**
     * 演示模式开关
     */
    @Value("${demo.enable}")
    private boolean enableDemo;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        if (!enableDemo) {
            SysLogSaveDTO dto = (SysLogSaveDTO) event.getSource();
            SysLog sysLog = BeanCopyUtil.beanCopy(dto, SysLog.class);
            JwtToken token = (JwtToken) SecurityUtils.getSubject().getPrincipal();
            if (token != null) {
                sysLog.setUserId(token.getId());
                sysLog.setUserAccount(token.getAccount());
            }
            logService.save(sysLog);
            log.info("远程操作日志记录成功：{}", dto);
        }
    }
}
