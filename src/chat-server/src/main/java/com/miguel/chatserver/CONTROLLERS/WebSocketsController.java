package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.SERVICES.IWebSocketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketsController {

  @Autowired
  private IWebSocketsService webSocketsService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  public MessageDTO sendMessage(
    @Payload MessageSaveDTO messageDTO,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    MessageDTO message = this.webSocketsService.sendMessage(messageDTO, headerAccessor);

    messagingTemplate.convertAndSendToUser(
      message.getRecipientPhoneNumber(),
      "/queue/messages",
      message
    );

    return message;
  }

}
