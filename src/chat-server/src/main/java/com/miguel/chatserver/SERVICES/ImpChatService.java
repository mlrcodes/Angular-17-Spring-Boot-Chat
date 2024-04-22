package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.REPOSITORIES.IChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpChatService implements IChatService {

  @Autowired
  private IMessageService messageService;


  @Override
  public Message saveMessage(Message message) {
    return this.messageService.saveMessage(message);
  }
}
