package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactCreateRequest;
import com.miguel.chatserver.DTO.ContactEditRequest;
import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectAlreadyExists;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IContactsMapper;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImpContactService implements IContactService {

  @Autowired
  private IContactsMapper contactsMapper;

  @Autowired
  private IUsersMapper usersMapper;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private IContactsRepository contactsRepository;

  @Autowired
  private IUsersService userService;

  @Autowired
  private IChatsService chatsService;

  @Autowired
  private IMessageService messageService;

  @Override
  public List<ContactResponseDTO> getUserContacts(String jwtToken) {
    String phoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    User owner = userService.findByPhoneNumber(phoneNumber);
    List<Contact> userContacts = contactsRepository.findByOwner(owner);
    if(userContacts.isEmpty()) {
      throw new ExceptionObjectNotFound("User do not have any contact");
    }
    return contactsMapper.createContactResponseListFromContactList(userContacts);
  }

  @Override
  public Contact findByOwnerAndUserContact(User owner, User userContact) {
    return contactsRepository.findByOwnerAndContactUser(owner, userContact).orElse(null);
  }

  @Override
  public ContactResponseDTO createContact(ContactCreateRequest contactRequest, String jwtToken) {
    String ownerPhoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    User owner = userService.findByPhoneNumber(ownerPhoneNumber);
    User contactUser = userService.findByPhoneNumber(contactRequest.getContactPhoneNumber());

    if (Objects.isNull(owner) || Objects.isNull(contactUser)) {
      throw new IllegalArgumentException("Contact user not found");
    }

    String contactName = contactRequest.getContactName();
    Contact newContact = Contact.builder()
      .contactName(contactName)
      .owner(owner)
      .contactUser(contactUser)
      .build();

    Boolean contactExists = contactsRepository.existsByOwnerAndContactUser(owner, contactUser);
    if (contactExists) {
      throw new ExceptionObjectAlreadyExists("Contact already exists");
    }
    Contact savedContact = contactsRepository.save(newContact);

    Chat savedChat;
    String messageText = contactRequest.getMessage();
    if (StringUtils.hasText(messageText)) {
      savedChat = chatsService.createChatIfNotExists(savedContact);
      if (Objects.nonNull(savedChat)) {
        Message savedMessage = messageService.sendMessage(savedChat, messageText);
        savedChat.getMessages().add(savedMessage);
        chatsService.saveChat(savedChat);
      }
    }

    ContactResponseDTO savedContactDTO = contactsMapper.createContactResponseFromContact(savedContact);
    return savedContactDTO;
  }

  @Override
  public ContactResponseDTO updateContact(Integer contactId, ContactEditRequest editRequest) {
    Contact savedContact = contactsRepository.findById(contactId).orElse(null);
    if (Objects.isNull(savedContact)) {
      throw new ExceptionObjectNotFound("No contact was found");
    }
    savedContact.setContactName(editRequest.getContactName());
    return contactsMapper.createContactResponseFromContact(contactsRepository.save(savedContact));
  }

  @Override
  public ResultMessageDTO deleteContact(Integer contactId) {
    try {
      contactsRepository.deleteById(contactId);
    } catch (Exception ex) {
      throw new DataAccessException("Error deleting contact", ex){};
    }
    return new ResultMessageDTO("Contact deleted successfully");
  }

  @Override
  public Contact createDefaultContact(User owner, User contactUser) {
    return Contact
      .builder()
      .contactName("Unknown Contact")
      .owner(owner)
      .contactUser(contactUser)
      .build();
  }

  @Override
  public Contact getContactFromContactPhoneNumber(String phoneNumber) {
    User contactUser = userService.findByPhoneNumber(phoneNumber);
    return contactsRepository.findByContactUser(contactUser).orElse(null);
  }
}
