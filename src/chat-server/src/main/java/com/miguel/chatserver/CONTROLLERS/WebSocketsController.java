package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.SERVICES.IMessageService;
import com.miguel.chatserver.SERVICES.IWebSocketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketsController {

  @Autowired
  private IWebSocketsService webSocketsService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat.sendMessage")
  public MessageDTO sendMessage(
    @DestinationVariable Integer contactId,
    @Payload MessageSaveDTO messageDTO,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    MessageDTO message = this.webSocketsService.sendMessage(contactId, messageDTO, headerAccessor);

    messagingTemplate.convertAndSend("/chat/" + contactId, message);

  }

}
