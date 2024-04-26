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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
  public Message sendMessageAndUpdateChatsPair(Map<String, Chat> chatsPair, User sender, String messageText) {
    this.sendMessageAndUpdateChat(chatsPair.get("contactChat"), sender, messageText);
    return this.sendMessageAndUpdateChat(chatsPair.get("ownerChat"), sender, messageText);
  }

  private Message sendMessageAndUpdateChat(Chat chat, User sender, String messageText) {
    Message message = this.sendMessage(chat, sender, messageText);
    chat.getMessages().add(message);
    chatsService.saveChat(chat);
    return message;
  }

  @Override
  public List<MessageDTO> getChatMessages(Integer chatId) {
    Chat chat = chatsService.findById(chatId);
    if (Objects.nonNull(chat)) {
      List<Message> chatMessages = this.messageRepository.findByChat(chat);
      if (chatMessages.isEmpty()) {
        throw new ExceptionObjectNotFound("Void conversation");
      }
      return this.messagesMapper.createMessageDTOListFromMessageList(chatMessages);
    } else {
      throw new ExceptionObjectNotFound("Chat does not exist");
    }
  }
}
