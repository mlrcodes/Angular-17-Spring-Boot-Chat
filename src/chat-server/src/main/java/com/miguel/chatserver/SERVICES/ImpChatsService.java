package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IChatsMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ImpChatsService implements IChatsService {

  @Autowired
  private IChatsRepository chatRepository;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private IChatsMapper chatsMapper;

  @Autowired
  private IUsersService usersService;

  @Autowired
  private IContactServiceExtended contactServiceExtended;

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
    Contact contact = contactServiceExtended.findContactByOwnerAndContactUser(owner, contactUser);
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
  public ChatDTO createChat(Contact contact) {
    Chat chat = this.createChatIfNotExists(contact);
    return chatsMapper.createChatDTOFromChat(chat);
  }

  @Override
  public Chat createChatIfNotExists(Contact contact) {
    Chat existingChat;
    try {
      existingChat = this.getChat(contact.getOwner(), contact.getContactUser());
    } catch (ExceptionObjectNotFound ex) {
      return this.createChatsPair(contact);
    }
    return existingChat;
  }

  @Override
  public void deleteContactChatIfExists(Contact contact) {
    User owner = contact.getOwner();
    Chat chat = chatRepository.findByUserAndContact(owner, contact).orElse(null);
    if (Objects.nonNull(chat)) {
      chatRepository.delete(chat);
    }
  }

  @Override
  public Chat findById(Integer chatId) {
    return chatRepository.findById(chatId).orElse(null);
  }

  @Override
  public Chat saveChat(Chat chat) {
    return chatRepository.save(chat);
  }

  @Override
  public Chat createChatsPair(Contact contact) {
    User owner = contact.getOwner();
    User contactUser = contact.getContactUser();

    if (Objects.isNull(contact) || Objects.isNull(owner)) {
      throw new IllegalStateException("Owner and/or contact should not be null");
    }

    Chat ownerChat = Chat
      .builder()
      .user(owner)
      .contact(contact)
      .messages(new ArrayList<>())
      .build();

    Contact defaultContact = contactServiceExtended.createDefaultContact(
      contactUser,
      owner
    );

    Chat contactChat = Chat
      .builder()
      .user(contactUser)
      .contact(defaultContact)
      .messages(new ArrayList<>())
      .build();

    chatRepository.save(ownerChat);
    chatRepository.save(contactChat);

    return ownerChat;
  }
}
