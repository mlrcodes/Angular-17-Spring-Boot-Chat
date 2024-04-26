package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageFirstSaveDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;

import java.util.List;
import java.util.Map;

public interface IMessageService {

  public Message sendMessage(Chat chat, String messageText);

  public Message sendMessageAndUpdateChatsPair(Map<String, Chat> chatsPair, String messageText);

  public List<MessageDTO> getChatMessages(Integer chatId);

}
