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
import org.springframework.util.StringUtils;

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

  @Autowired
  private IContactService contactService;

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
  public Chat getChat(User owner, User contactUser) {
    Contact contact = contactService.findByOwnerAndUserContact(owner, contactUser);
    if (Objects.isNull(contact)) {
      throw new ExceptionObjectNotFound("No contact was found");
    }
    Chat chat = chatRepository.findByUserAndContact(owner, contact).orElse(null);
    if (Objects.isNull(chat)) {
      throw new ExceptionObjectNotFound("Chat not found");
    }
    return chat;
  }

  @Override
  public Chat createChatIfNotExists(Contact contact) {
    Chat existingChat = this.getChat(contact.getOwner(), contact.getContactUser());
    if (Objects.isNull(existingChat)) {
      return createChatsPair(contact);
    }
    return existingChat;
  }

  @Override
  public ChatDTO createChat(Contact contact) {
    Chat chat = this.createChatIfNotExists(contact);
    return chatsMapper.createChatDTOFromChat(chat);
  }

  @Override
  public Chat saveChat(Chat chat) {
    return chatRepository.save(chat);
  }

  private Chat createChatsPair(Contact contact) {
    User owner = contact.getOwner();

    if (Objects.isNull(contact) || Objects.isNull(owner)) {
      throw new IllegalStateException("Owner and/or contact should not be null");
    }

    Chat ownerChat = Chat
      .builder()
      .user(owner)
      .contact(contact)
      .messages(new ArrayList<>())
      .build();

    Contact defaultContact = contactService.createDefaultContact(
      contact.getContactUser(),
      owner
    );

    Chat contactChat = Chat
      .builder()
      .user(contact.getContactUser())
      .contact(defaultContact)
      .messages(new ArrayList<>())
      .build();

    chatRepository.save(ownerChat);
    chatRepository.save(contactChat);

    return ownerChat;
  }
}
