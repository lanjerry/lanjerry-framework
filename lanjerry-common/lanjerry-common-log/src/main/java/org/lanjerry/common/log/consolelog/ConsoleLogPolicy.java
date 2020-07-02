package org.lanjerry.common.log.consolelog;

import java.text.DateFormat;
import java.util.Date;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * 利用rewrite把日志推送到页面
 *
 * @author Caratacus
 */
@Plugin(name = "ConsoleLogPolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class ConsoleLogPolicy implements RewritePolicy {

    @PluginFactory
    public static ConsoleLogPolicy factory() {
        return new ConsoleLogPolicy();
    }

    @Override
    public LogEvent rewrite(LogEvent source) {
        ConsoleLog log = ConsoleLog.builder()
                .body(source.getMessage().getFormattedMessage())
                .timestamp(DateFormat.getDateTimeInstance().format(new Date(source.getTimeMillis())))
                .fileName(source.getSource().getFileName())
                .lineNumber(source.getSource().getLineNumber())
                .threadName(source.getThreadName())
                .level(source.getLevel().name())
                .build();
        ConsoleLogQueue.getInstance().push(log);
        return source;
    }
}