package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.MODELS.Contact;

import java.util.List;

public interface IContactService {

  public List<ContactDTO> getUserContacts(String token);
}
