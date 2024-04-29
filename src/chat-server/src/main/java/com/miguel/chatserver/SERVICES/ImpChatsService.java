package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IChatsMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Primary
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

  @Autowired
  private IMessageService messageService;


  @Override
  public List<ChatDTO> getUserChats(String jwtToken) {
    User user = usersService.findByPhoneNumber(
      jwtService.getPhoneNumberFromToken(jwtToken)
    );
    return chatsMapper.createChatDTOListFromChatList(user.getChats());
  }

  @Override
  public Map<String, Chat> getChatsPair(User owner, User contactUser) {
    Contact contact = contactServiceExtended.findContactByPhoneNumber(contactUser.getPhoneNumber());
    Contact userContact = contactServiceExtended.findContactOrCreateDefaultOne(contactUser, owner);

    if (Objects.isNull(contact)) {
      throw new ExceptionObjectNotFound("Contact not found");
    }

    if (Objects.isNull(userContact)) {
      throw new ExceptionObjectNotFound("User not found");
    }

    Chat ownerChat = chatRepository.findByUserAndContact(owner, contact).orElse(null);
    Chat contactChat = chatRepository.findByUserAndContact(contactUser, userContact).orElse(null);
    return getChatsPairMap(ownerChat, contactChat);
  }


  @Override
  public Chat getOwnerChat(User owner, User contactUser) {
    return this.getChatsPair(owner, contactUser).get("ownerChat");
  }

  @Override
  public Chat getContactChat(User owner, User contactUser) {
    return this.getChatsPair(owner, contactUser).get("contactChat");
  }

  @Override
  public Map<String, Chat> createChatsIfNotExist(Contact contact) {
    Map<String, Chat> existingChats;

    try {
      existingChats = this.getChatsPair(
        contact.getOwner(),
        contact.getContactUser()
      );
      Chat ownerChat = existingChats.get("ownerChat");
      Chat contactChat = existingChats.get("contactChat");

      if (Objects.isNull(ownerChat) && Objects.isNull(contactChat)) {
        return getChatsPairMap(
          this.createOwnerChat(contact),
          this.createContactChat(contact)
        );
      }

      if (Objects.isNull(ownerChat) && Objects.nonNull(contactChat)) {
        return getChatsPairMap(
          this.createOwnerChat(contact),
          contactChat
        );
      }

      if (Objects.nonNull(ownerChat) && Objects.isNull(contactChat)) {
        return getChatsPairMap(
          ownerChat,
          this.createContactChat(contact)
        );
      }

    } catch (ExceptionObjectNotFound ex) {
      throw ex;
    }
    return existingChats;
  }

  @Override
  public void deleteContactChatIfExists(Contact contact) {
    User owner = contact.getOwner();
    Chat chat = chatRepository.findByUserAndContact(owner, contact).orElse(null);
    if (Objects.nonNull(chat)) {
      messageService.deleteChatMessagesIfExist(chat);
      chatRepository.delete(chat);
    }
  }

  @Override
  public Chat findById(Integer chatId) {
    return chatRepository.findById(chatId).orElse(null);
  }

  private Chat createOwnerChat(Contact contact) {
    Chat ownerChat = Chat
      .builder()
      .user(contact.getOwner())
      .contact(contact)
      .messages(new ArrayList<>())
      .build();

    return chatRepository.save(ownerChat);
  }

  private Chat createContactChat(Contact contact) {
    User contactUser = contact.getContactUser();
    Contact userContact = contactServiceExtended.findContactByPhoneNumber(contact.getOwner().getPhoneNumber());

    Chat contactChat = Chat
      .builder()
      .user(contactUser)
      .contact(userContact)
      .messages(new ArrayList<>())
      .build();

    return chatRepository.save(contactChat);
  }

  @Override
  public Map<String, Chat> getChatsPairMap(Chat ownerChat, Chat contactChat) {
    Map<String, Chat> chatsMap = new HashMap<>();
    chatsMap.put("ownerChat", ownerChat);
    chatsMap.put("contactChat", contactChat);
    return chatsMap;
  }
}
