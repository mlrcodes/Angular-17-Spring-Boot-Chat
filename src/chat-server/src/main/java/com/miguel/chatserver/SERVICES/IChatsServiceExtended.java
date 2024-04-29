package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Chat;

public interface IChatsServiceExtended extends IChatsService {

  public Chat saveChat(Chat chat);

}
