package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.SERVICES.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

  @Autowired
  private SimpMessageSendingOperations messageTemplate;

  @Autowired
  private IUserService userService;

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String phoneNumber = (String) headerAccessor.getSessionAttributes().get("phoneNumber");
    if (Objects.nonNull(phoneNumber)) {
      log.info("User disconnected: {}", phoneNumber);
      var message = Message.builder()
        .dateTime(LocalDateTime.now())
        .sender(userService.findByPhoneNumber(phoneNumber))
        .build();
      messageTemplate.convertAndSend("topic/public", message);
    }
  }
}
