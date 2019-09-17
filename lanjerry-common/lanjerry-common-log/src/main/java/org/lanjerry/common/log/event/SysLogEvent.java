package org.lanjerry.common.log.event;

import org.lanjerry.common.log.bean.SysLogSaveDTO;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 系统日志事件
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-04
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLogSaveDTO source) {
        super(source);
    }
}
