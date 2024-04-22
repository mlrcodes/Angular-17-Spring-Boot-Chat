package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Message;

public interface IChatService {

  public Message saveMessage(Message message);
}
