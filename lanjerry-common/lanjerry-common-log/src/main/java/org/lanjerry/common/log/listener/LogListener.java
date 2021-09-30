package org.lanjerry.common.log.listener;

import org.apache.logging.log4j.ThreadContext;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.Random;

/**
 * <p>
 * 给请求增加全局唯一ID给日志输出用
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-30
 */
@WebListener
public class LogListener implements ServletRequestListener {

    public static final String LOG_ID = "LogId";

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        //给请求增加全局唯一ID给日志输出用
        String logId = generatorLogId();
        ThreadContext.put(LOG_ID, logId);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ThreadContext.remove(LOG_ID);
    }

    private String generatorLogId() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int anInt = random.nextInt(999);
        return millis + String.format("%03d", anInt);
    }
}
