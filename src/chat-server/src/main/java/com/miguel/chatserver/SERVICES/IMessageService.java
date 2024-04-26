package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;

import java.util.List;
import java.util.Map;

public interface IMessageService {

  public Message sendMessage(Chat chat, User sender, String messageText);

  public Message sendMessageAndUpdateChatsPair(Map<String, Chat> chatsPair, User sender, String messageText);

  public List<MessageDTO> getChatMessages(Integer chatId);

}
