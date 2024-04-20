package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.MODELS.Contact;

import java.util.List;

public interface IContactsMapper {


  public Contact createContactFromDTO(ContactDTO contactDTO);

  public List<Contact> createContactListFromDTOList(List<ContactDTO> contactDTOList);

  public ContactDTO createContactDTOFromContact(Contact contact);

  public List<ContactDTO> createContactDTOListFromContactList(List<Contact> contactsList);
}
