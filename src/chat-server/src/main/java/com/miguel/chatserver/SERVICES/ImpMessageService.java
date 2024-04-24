package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageFirstSaveDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IContactsMapper;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.REPOSITORIES.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
  public MessageDTO saveMessage(MessageSaveDTO messageSaveDTO) {
    Chat chat = chatsService.findById(messageSaveDTO.getChatId());
    if (Objects.isNull(chat)) {
      throw new ExceptionObjectNotFound("Chat not found");
    }
    Message newMessage = this.sendMessage(chat, messageSaveDTO.getMessageText());
    return messagesMapper.createMessageDTOFromMessage(newMessage);
  }
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
    Message savedMessage = null;
    Chat savedChat = chatsService.createChatIfNotExists(contact);
    if (Objects.nonNull(savedChat)) {
      savedMessage = this.sendMessage(savedChat, messageText);
      savedChat.getMessages().add(savedMessage);
      chatsService.saveChat(savedChat);
    }
    return savedMessage;
  }

  @Override
  public List<MessageDTO> getChatMessages(Integer chatId) {
    List<MessageDTO> chatMessagesDTO;
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
