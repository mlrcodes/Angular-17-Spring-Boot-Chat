package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

import java.util.List;

public interface IChatsService {
  public List<ChatDTO> getUserChats(String token);

  public Chat createChat(Contact contact, String messageText);

}
