package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IChatsMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ImpChatsService implements IChatsService {

  @Autowired
  private IChatsRepository chatRepository;

  @Autowired
  private IMessageService messageService;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private IChatsMapper chatsMapper;

  @Autowired
  private IUsersService usersService;

  @Override
  public List<ChatDTO> getUserChats(String jwtToken) {
    String phoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    User user = usersService.findByPhoneNumber(phoneNumber);
    if (Objects.isNull(user)) {
      throw new ExceptionObjectNotFound("No user was found");
    }
    if (Objects.isNull(user.getChats()) || user.getChats().isEmpty()) {
      throw new ExceptionObjectNotFound("User do not have any active chat");
    }
    return chatsMapper.createChatDTOListFromChatList(user.getChats());
  }

  @Override
  public Chat createChat(Contact contact, String messageText) {
    if (contact == null || contact.getOwner() == null) {
      throw new IllegalStateException("Owner and/or contact should not be null");
    }

    Chat newChat = Chat
      .builder()
      .messages(new ArrayList<>())
      .user(contact.getOwner())
      .contact(contact)
      .build();
    Chat savedChat = chatRepository.save(newChat);

    Message message = Message
      .builder()
      .sender(contact.getOwner())
      .dateTime(LocalDateTime.now())
      .messageText(messageText)
      .chat(savedChat)
      .build();
    Message savedMessage = messageService.saveMessage(message);

    savedChat.getMessages().add(savedMessage);

    return chatRepository.save(savedChat);
  }
}
