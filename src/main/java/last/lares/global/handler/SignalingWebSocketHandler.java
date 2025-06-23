package last.lares.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
public class SignalingWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("새로운 연결이 시작되었습니다 : {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> payload = mapper.readValue(message.getPayload(), Map.class);
        String type = payload.get("type").toString();

        switch (type) {
            case "register" :
                String clientId = payload.get("clientId").toString();
                sessionList.put(clientId, session);

                log.info("신규 클라이언트 등록 : {}", clientId);
                break;

            case "offer" :
            case "answer" :
            case "candidate" :
                String to = payload.get("to").toString();
                WebSocketSession toSession = sessionList.get(to);

                if (toSession != null && session.isOpen()) toSession.sendMessage(message);
                else log.warn("존재하지 않거나 종료된 세션 : {}", to);

                break;

            default :
                log.error("올바르지 않은 메시지 타입 : {}", type);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();

        sessionList.entrySet().removeIf(
                entry -> entry
                        .getValue().getId()
                        .equals(sessionId)
        );

        log.info("세션이 정상적으로 종료되었습니다 : {}", sessionId);
    }
}
