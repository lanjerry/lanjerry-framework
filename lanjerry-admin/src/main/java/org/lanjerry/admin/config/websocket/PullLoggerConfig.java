package org.lanjerry.admin.config.websocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.lanjerry.common.log.consolelog.ConsoleLog;
import org.lanjerry.common.log.consolelog.ConsoleLogQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 日志推送的WebSocket，配置消息代理端点，即stomp服务端
 * </p>
 *
 * @author lanjerry
 * @since 2020-07-02
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class PullLoggerConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 注册stomp端点，主要是起到连接作用
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/logger") // 端点名称
                .setAllowedOrigins("*") // 跨域
                .addInterceptors() // 拦截处理，和http拦截类似
                .withSockJS(); // 使用sockJS
    }

    /**
     * 这是用于配置服务器向浏览器发送的消息。
     * clientOut就表示出出口。还有一个inBoundChannel用于处理浏览器向服务器发送的消息
     * 默认的发送器是采用多线程发送的
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(1).maxPoolSize(1);
    }

    /**
     * 推送日志
     */
    @PostConstruct
    public void pushLogger() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable runnable = () -> {
            while (true) {
                try {
                    ConsoleLog log = ConsoleLogQueue.poll();
                    if (log != null) {
                        if (messagingTemplate != null)
                            messagingTemplate.convertAndSend("/topic/pullLogger", log);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(runnable);
    }
}
