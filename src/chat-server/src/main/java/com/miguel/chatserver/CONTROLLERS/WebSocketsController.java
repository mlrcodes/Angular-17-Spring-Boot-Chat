package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketsController {

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/{chatId}")
  public MessageDTO sendMessage(
    @DestinationVariable Integer chatId,
    @Payload MessageSaveDTO messageDTO
  ) {
    // Aquí puedes manejar la lógica para guardar el mensaje en la base de datos
    return messageDTO;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/{chatId}")
  public MessageDTO addUser(
    @DestinationVariable Integer chatId,
    @Payload MessageSaveDTO messageDTO,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    headerAccessor.getSessionAttributes().put("chatId", chatId);
    return messageDTO;
  }

}
