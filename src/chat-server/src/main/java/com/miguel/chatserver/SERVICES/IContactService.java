package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.*;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
public interface IContactService {

  public List<ContactResponseDTO> getUserContacts(String jwtToken);

  public ChatDTO createContact(ContactCreateRequest contactRequest, String jwtToken);

  public Contact updateContact(Integer contactId, ContactEditRequest editRequest);

  public ChatDTO getChatAfterContactUpdate(User owner, Integer contactId, ContactEditRequest editRequest);

  public ResultMessageDTO deleteContact(Integer contactId);

  }
