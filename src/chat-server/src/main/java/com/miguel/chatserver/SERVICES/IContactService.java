package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactCreateRequest;
import com.miguel.chatserver.DTO.ContactEditRequest;
import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

import java.util.List;

public interface IContactService {

  public List<ContactResponseDTO> getUserContacts(String jwtToken);

  public Contact findByOwnerAndUserContact(User owner, User userContact);

  public ContactResponseDTO createContact(ContactCreateRequest contactRequest, String jwtToken);

  public ContactResponseDTO updateContact(Integer contactId, ContactEditRequest editRequest);

  public ResultMessageDTO deleteContact(Integer contactId);

  public Contact createDefaultContact(User owner, User contactUser);

  public Contact getContactFromContactPhoneNumber(String phoneNumber);

  }
