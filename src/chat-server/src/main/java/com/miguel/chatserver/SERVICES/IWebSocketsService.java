package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageSaveDTO;

public interface IWebSocketsService {

  public void sendMessage(MessageSaveDTO messageDTO);
}
