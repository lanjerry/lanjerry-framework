package org.lanjerry.admin.config.websocket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 消息推送的WebSocket，配置消息代理端点，即stomp服务端
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-17
 */
@Slf4j
@Component
@ServerEndpoint("/ws/notification/{userId}")
public class NotificationEndpoint {

    /**
     * 客户端的连接会话
     */
    private Session session;

    /**
     * 打开连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        List<Session> sessions = SessionPool.sessions.get(userId);
        if (sessions == null) {
            sessions = new ArrayList<>();
        }
        sessions.add(session);
        SessionPool.sessions.put(userId, sessions);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(@PathParam("userId") Integer userId) {
        List<Session> sessions = SessionPool.sessions.get(userId);
        sessions.remove(session);
        if (sessions.size() == 0) {
            SessionPool.sessions.remove(userId);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：" + message);
        SessionPool.sendMessage(message);
    }
}
