package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactCreateRequest;
import com.miguel.chatserver.DTO.ContactEditRequest;
import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IContactsRepository;
import com.miguel.chatserver.REPOSITORIES.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpContactServiceExtended implements IContactServiceExtended {

  @Autowired
  private IContactsRepository contactsRepository;

  @Autowired
  private IUsersService usersService;

  @Override
  public List<ContactResponseDTO> getUserContacts(String jwtToken) {
    return null;
  }

  @Override
  public ContactResponseDTO createContact(ContactCreateRequest contactRequest, String jwtToken) {
    return null;
  }

  @Override
  public ContactResponseDTO updateContact(Integer contactId, ContactEditRequest editRequest) {
    return null;
  }

  @Override
  public ResultMessageDTO deleteContact(Integer contactId) {
    return null;
  }



  @Override
  public Contact getContactFromContactPhoneNumber(String phoneNumber) {
    User contactUser = usersService.findByPhoneNumber(phoneNumber);
    return contactsRepository.findByContactUser(contactUser).orElse(null);
  }

  @Override
  public Contact findContactByOwnerAndContactUser(User owner, User contactUser) {
    return contactsRepository.findByOwnerAndContactUser(owner, contactUser).orElse(null);
  }

  @Override
  public Contact createDefaultContact(User owner, User contactUser) {
    Contact defaultContact = Contact
      .builder()
      .contactName("Unknown Contact")
      .owner(owner)
      .contactUser(contactUser)
      .build();

    return contactsRepository.save(defaultContact);
  }
}
