package last.lares.domain.signal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SignalRepository {
    private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<>();

    public String registerSession(WebSocketSession session, Map<String,Object> payload) {
        String clientId = payload.get("clientId").toString();
        sessionList.put(clientId, session);
        return clientId;
    }

    public WebSocketSession getSession(Map<String,Object> payload) {
        String to = payload.get("to").toString();
        return sessionList.get(to);
    }

    public void removeSession(WebSocketSession session) {
        sessionList.entrySet().removeIf(
                entry -> entry
                        .getValue().getId()
                        .equals(session.getId())
        );
    }
}
