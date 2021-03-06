package org.lanjerry.admin.config.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.lanjerry.admin.vo.sys.SysNotificationVO;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * Session池
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-17
 */
public class SessionPool {

    public static Map<Integer, List<Session>> sessions = new ConcurrentHashMap<>();

    public static void close(String sessionId) throws IOException {
        List<Session> sessions = SessionPool.sessions.get(sessionId);
        if (CollUtil.isNotEmpty(sessions)) {
            for (int i = 0; i <= sessions.size(); i++) {
                Session session = sessions.get(i);
                session.close();
            }
        }
    }

    public static void sendMessage(Integer sessionId, SysNotificationVO notification) {
        List<Session> sessions = SessionPool.sessions.get(sessionId);
        if (CollUtil.isNotEmpty(sessions)) {
            sessions.forEach(session -> session.getAsyncRemote().sendText(JSONUtil.toJsonStr(notification)));
        }
    }

    public static void sendMessage(SysNotificationVO notification) {
        for (Integer sessionId : SessionPool.sessions.keySet()) {
            List<Session> sessions = SessionPool.sessions.get(sessionId);
            if (CollUtil.isNotEmpty(sessions)) {
                sessions.forEach(session -> session.getAsyncRemote().sendObject(notification));
            }
        }
    }
}
