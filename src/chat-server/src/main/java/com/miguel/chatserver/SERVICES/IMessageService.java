package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;

import java.util.List;

public interface IMessageService {

  public Message sendMessage(Chat chat, String messageText);

  public Message sendFirstContactMessage(Contact contact, String messageText);

  public List<Message> getChatMessages(Integer chatId);

}
