package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.MODELS.Contact;

import java.util.List;

public interface IContactsMapper {

  public ContactResponseDTO createContactResponseFromContact(Contact contact);

  public List<ContactResponseDTO> createContactResponseListFromContactList(List<Contact> contactsList);
}
