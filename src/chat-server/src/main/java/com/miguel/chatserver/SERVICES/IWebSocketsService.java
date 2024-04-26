package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public interface IWebSocketsService {

  public MessageDTO sendMessage(MessageSaveDTO messageDTO);
}
