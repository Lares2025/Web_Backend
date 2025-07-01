package last.lares.domain.signal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import last.lares.domain.signal.repository.SignalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignalService {
    private final SignalRepository signalRepository;
    private final ObjectMapper mapper;

    public void handleMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        Map<String, Object> payload = mapper.readValue(message.getPayload(), Map.class);
        String type = payload.get("type").toString();

        switch (type) {
            case "register" :
                String clientId = signalRepository.registerSession(session, payload);
                log.info("신규 클라이언트 등록 : {}", clientId);
                break;

            case "offer" :
            case "answer" :
            case "candidate" :
                WebSocketSession toSession = signalRepository.getSession(payload);

                if (toSession != null && session.isOpen()) {
                    try {
                        toSession.sendMessage(message);
                    } catch (Exception e) {
                        log.warn("존재하지 않거나 종료된 세션 : {}", payload.get("to"));
                    }
                }
                break;

            default :
                log.error("올바르지 않은 메시지 타입 : {}", type);
        }
    }

    public void closeConnection(WebSocketSession session) throws JsonProcessingException {
        signalRepository.removeSession(session);
    }
}
