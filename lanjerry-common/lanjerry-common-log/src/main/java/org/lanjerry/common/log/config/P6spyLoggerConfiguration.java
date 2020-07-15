package org.lanjerry.common.log.config;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * p6spy日志实现
 * </p>
 *
 * @author lanjerry
 * @since 2020-07-15
 */
@Slf4j
public class P6spyLoggerConfiguration extends com.p6spy.engine.spy.appender.StdoutLogger {

    @Override
    public void logText(String text) {
        log.info(text);
    }
}
