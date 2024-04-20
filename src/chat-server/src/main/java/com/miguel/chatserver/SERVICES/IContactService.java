package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;

import java.util.List;

public interface IContactService {

  public List<ContactDTO> getUserContacts(String jwtToken);

  public ContactDTO createContact(ContactDTO contactDTO, String jwtToken);

  public ResultMessageDTO deleteContact(Integer contactId);
}
