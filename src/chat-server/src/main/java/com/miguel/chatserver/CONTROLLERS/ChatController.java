package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.SERVICES.IChatService;
import com.miguel.chatserver.SERVICES.IChatsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  @Autowired
  private IChatService chatService;
  @MessageMapping("/chat/{chatId}")
  @SendTo("/topic/messages/{chatId}")
  public Message send(@DestinationVariable Integer chatId, Message message) throws Exception {
    if (chatService.validateChat(chatId, message.getSender().getUserId())) {
      chatService.saveMessage(message);
      return message;
    } else {
      throw new AccessDeniedException("No tiene acceso a esta conversación.");
    }
  }

}



  /**
  @MessageMapping("chat.sendMessage")
  @SendTo("/topic/public")
  public Message sendMessage(
     @Payload Message message
  ) {
    return message;
  }

  @MessageMapping("chat.addUser")
  @SendTo("/topic/public")
  public Message addUser(
    @Payload Message message,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    headerAccessor.getSessionAttributes().put("phoneNumber", message.getSender().getPhoneNumber());
    return message;
  }
    **/

