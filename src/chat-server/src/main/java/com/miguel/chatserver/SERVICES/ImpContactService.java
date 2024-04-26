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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Primary
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
  private IMessageService messageService;

  @Autowired
  private IChatsService chatsService;

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
  public ContactResponseDTO createContact(
    ContactCreateRequest contactRequest,
    String jwtToken
  ) {
    User owner = userService.findByPhoneNumber(
      jwtService.getPhoneNumberFromToken(jwtToken)
    );
    User contactUser = userService.findByPhoneNumber(contactRequest.getContactPhoneNumber());

    if (Objects.isNull(contactUser)) {
      throw new ExceptionObjectNotFound("User not found");
    }

    Contact newContact = Contact.builder()
      .contactName(contactRequest.getContactName())
      .owner(owner)
      .contactUser(contactUser)
      .build();

    Boolean contactExists = contactsRepository.existsByOwnerAndContactUser(owner, contactUser);
    if (contactExists) {
      throw new ExceptionObjectAlreadyExists("Contact already exists");
    }
    Contact savedContact = contactsRepository.save(newContact);

    Map<String, Chat> savedChats = chatsService.createChatsIfNotExist(savedContact);

    String messageText = contactRequest.getMessage();
    if (StringUtils.hasText(messageText)) {
      messageService.sendMessageAndUpdateChatsPair(savedChats, owner, messageText);
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
    Contact contact = contactsRepository.findById(contactId).orElse(null);
    if (Objects.isNull(contact)) {
      throw new ExceptionObjectNotFound("No contact was found");
    }
    chatsService.deleteContactChatIfExists(contact);
    contactsRepository.delete(contact);
    return new ResultMessageDTO("Contact deleted successfully");
  }
}
