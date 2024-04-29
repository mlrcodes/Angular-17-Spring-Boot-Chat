package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.*;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
  public ChatDTO createContact(ContactCreateRequest contactRequest, String jwtToken) {
    return null;
  }

  @Override
  public Contact updateContact(Integer contactId, ContactEditRequest editRequest) {
    return null;
  }

  @Override
  public ChatDTO getChatAfterContactUpdate(User owner, Integer contactId, ContactEditRequest editRequest) {
    return null;
  }

  @Override
  public ResultMessageDTO deleteContact(Integer contactId) {
    return null;
  }



  @Override
  public Contact findContactByPhoneNumber(String phoneNumber) {
    User contactUser = usersService.findByPhoneNumber(phoneNumber);
    return contactsRepository.findByContactUser(contactUser).orElse(null);
  }

  @Override
  public Contact findContactOrCreateDefaultOne(User owner, User contactUser) {
    Contact contact = this.findContactByPhoneNumber(contactUser.getPhoneNumber());
    if (Objects.isNull(contact)) {
      return this.createDefaultContact(owner, contactUser);
    }
    return contact;
  }
  private Contact createDefaultContact(User owner, User contactUser) {
    Contact defaultContact = Contact
      .builder()
      .contactName("Unknown Contact")
      .owner(owner)
      .contactUser(contactUser)
      .build();

    return contactsRepository.save(defaultContact);
  }
}
