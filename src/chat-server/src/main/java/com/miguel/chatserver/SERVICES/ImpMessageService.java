package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageFirstSaveDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImpMessageService implements IMessageService {

  @Autowired
  private IMessageRepository messageRepository;

  @Autowired
  private IMessagesMapper messagesMapper;

  @Autowired
  private IChatsService chatsService;

  @Autowired
  private IContactServiceExtended contactServiceExtended;

  @Override
  public Message sendMessage(Chat chat, User sender, String messageText) {
    Message message = Message
      .builder()
      .sender(sender)
      .timeStamp(new Date())
      .messageText(messageText)
      .chat(chat)
      .build();
    return messageRepository.save(message);
  }
  @Override
  public Map<String, Message> sendMessageAndUpdateChatsPair(
    Map<String, Chat> chatsPair,
    User sender, String messageText
  ) {
    return this.getMessagesPairMap(
      this.sendMessageAndUpdateChat(chatsPair.get("ownerChat"), sender, messageText),
      this.sendMessageAndUpdateChat(chatsPair.get("contactChat"), sender, messageText)
    );
  }

  private Message sendMessageAndUpdateChat(
    Chat chat,
    User sender,
    String messageText
  ) {
    Message message = this.sendMessage(chat, sender, messageText);
    chat.getMessages().add(message);
    chatsService.saveChat(chat);
    return message;
  }

  private Map<String, Message> getMessagesPairMap(
    Message senderMessage,
    Message contactMessage
  ) {
    Map<String, Message> messagesMap = new HashMap<>();
    messagesMap.put("senderMessage", senderMessage);
    messagesMap.put("contactMessage", contactMessage);
    return messagesMap;
  }

  @Override
  public List<MessageDTO> getChatMessagesDTO(Chat chat) {
    try {
      return this.messagesMapper
        .createMessageDTOListFromMessageList(
          chat.getMessages()
        );
    } catch (ExceptionObjectNotFound ex) {
      throw ex;
    }
  }

  @Override
  public void deleteChatMessagesIfExist(Chat chat) {
    List<Message> chatMessages = chat.getMessages();
    if (Objects.nonNull(chatMessages) && !chatMessages.isEmpty()) {
      messageRepository.deleteByChat(chat);
    }
  }
}
