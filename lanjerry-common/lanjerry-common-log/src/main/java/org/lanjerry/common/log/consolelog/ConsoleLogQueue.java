package org.lanjerry.common.log.consolelog;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建一个阻塞队列，作为日志系统输出的日志的一个临时载体
 *
 * @author Caratacus
 * @link https://cloud.tencent.com/developer/article/1096792
 */
@Slf4j
public class ConsoleLogQueue {

    /**
     * 队列大小
     */
    private static final int QUEUE_MAX_SIZE = 10000;

    /**
     * 阻塞队列
     */
    private static final BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    /**
     * 消息入队
     */
    public static boolean push(ConsoleLog log) {
        return blockingQueue.add(log);
    }

    /**
     * 消息出队
     */
    public static ConsoleLog poll() {
        ConsoleLog result = null;
        try {
            result = (ConsoleLog) blockingQueue.take();
        } catch (InterruptedException e) {
            log.warn("消息出列异常:{}", e.getMessage());
        }
        return result;
    }
}
