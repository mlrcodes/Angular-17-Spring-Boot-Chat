package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IContactsMapper;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
  private IUserService userService;

  @Override
  public List<ContactDTO> getUserContacts(String jwtToken) {
    String phoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    User owner = userService.findByPhoneNumber(phoneNumber);
    List<Contact> userContacts = contactsRepository.findByOwner(owner);
    if(userContacts.isEmpty()) {
      throw new ExceptionObjectNotFound("User do not have any contact");
    }
    return contactsMapper.createContactDTOListFromContactList(userContacts);
  }

  @Override
  public ContactDTO createContact(ContactDTO contactDTO, String jwtToken) {
    String ownerPhoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    User owner = userService.findByPhoneNumber(ownerPhoneNumber);
    UserDTO contactUserDTO = contactDTO.getContactUser();
    User contactUser = userService.findByPhoneNumber(contactUserDTO.getPhoneNumber());

    if (Objects.isNull(owner) || Objects.isNull(contactUser)) {
      throw new IllegalArgumentException("Contact user not found");
    }

    String contactName = contactDTO.getContactName();
    Contact newContact = Contact.builder()
      .contactName(contactName)
      .owner(owner)
      .contactUser(contactUser)
      .build();

    Boolean contactExists = contactsRepository.existsByOwnerAndContactUser(owner, contactUser);
    if (contactExists) {
      throw new IllegalStateException("Contact already exists");
    }

    Contact savedContact;
    try {
      savedContact = contactsRepository.save(newContact);
    } catch (Exception ex) {
      throw new DataAccessException("Error creating new contact", ex){};
    }

    ContactDTO savedContactDTO = contactsMapper.createContactDTOFromContact(savedContact);
    return savedContactDTO;
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
}
