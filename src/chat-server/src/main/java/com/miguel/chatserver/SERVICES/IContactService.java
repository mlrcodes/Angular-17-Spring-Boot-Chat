package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactCreateRequest;
import com.miguel.chatserver.DTO.ContactEditRequest;
import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.MODELS.Contact;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
public interface IContactService {

  public List<ContactResponseDTO> getUserContacts(String jwtToken);

  public ContactResponseDTO createContact(ContactCreateRequest contactRequest, String jwtToken);

  public ContactResponseDTO updateContact(Integer contactId, ContactEditRequest editRequest);

  public ResultMessageDTO deleteContact(Integer contactId);

  }
