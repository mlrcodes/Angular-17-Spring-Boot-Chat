package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.SERVICES.IWebSocketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketsController {

  @Autowired
  private IWebSocketsService webSocketsService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  public void sendMessage(
    @Payload MessageSaveDTO messageDTO
  ) {
    this.webSocketsService.sendMessage(messageDTO);
  }
}
