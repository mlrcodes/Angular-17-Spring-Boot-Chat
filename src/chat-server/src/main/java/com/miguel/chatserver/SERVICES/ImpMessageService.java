package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageFirstSaveDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
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
  public Message sendMessage(Chat chat, String messageText) {
    Message message = Message
      .builder()
      .sender(chat.getUser())
      .timeStamp(new Date())
      .messageText(messageText)
      .chat(chat)
      .build();
    return messageRepository.save(message);
  }

  @Override
  public MessageDTO sendFirstChatMessage(MessageFirstSaveDTO messageInfo) {
    return messagesMapper.createMessageDTOFromMessage(
      sendFirstContactMessage(
        contactServiceExtended.getContactFromContactPhoneNumber(messageInfo.getContactPhoneNumber()),
        messageInfo.getMessageText()
      )
    );
  }

  @Override
  public Message sendFirstContactMessage(Contact contact, String messageText) {
    Message savedSenderMessage = null;
    Message savedContactMessage;
    Map<String, Chat> savedChats = chatsService.createChatsIfNotExist(contact);
    if (Objects.nonNull(savedChats)) {
      Chat senderChat = savedChats.get("ownerChat");
      Chat contactChat = savedChats.get("contactChat");
      savedSenderMessage = this.sendMessage(senderChat, messageText);
      savedContactMessage = this.sendMessage(contactChat, messageText);
      senderChat.getMessages().add(savedSenderMessage);
      contactChat.getMessages().add(savedContactMessage);
      chatsService.saveChat(senderChat);
      chatsService.saveChat(contactChat);
    }
    return savedSenderMessage;
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
      throw new ExceptionObjectNotFound("Void conversation");
    }
  }
}
