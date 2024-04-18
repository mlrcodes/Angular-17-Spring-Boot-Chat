package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactDTO;
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
import org.springframework.stereotype.Service;

import java.util.List;

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
    User contactUser = usersMapper.createUserFromDTO(contactUserDTO);
    String contactName = contactDTO.getContactName();
    Contact newContact = Contact
      .builder()
      .contactName(contactName)
      .owner(owner)
      .contactUser(contactUser)
      .build();
    try {
      Contact savedContact = contactsRepository.save(newContact);
      ContactDTO savedContactDTO = contactsMapper.createContactDTOFromContact(savedContact);
      return savedContactDTO;
    } catch (Exception ex) {
      throw new RuntimeException("Error creating new contact");
    }
  }
}
