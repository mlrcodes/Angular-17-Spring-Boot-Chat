package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Message;

public interface IMessageService {

  public Message sendMessage(Chat chat, String messageText);

}
