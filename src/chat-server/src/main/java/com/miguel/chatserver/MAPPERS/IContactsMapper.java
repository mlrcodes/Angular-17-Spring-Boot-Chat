package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.MODELS.Contact;

import java.util.List;

public interface IContactsMapper {


  public Contact createContactFromDTO(ContactResponseDTO contactDTO);

  public List<Contact> createContactListFromDTOList(List<ContactResponseDTO> contactDTOList);

  public ContactResponseDTO createContactResponseFromContact(Contact contact);

  public List<ContactResponseDTO> createContactResponseListFromContactList(List<Contact> contactsList);
}
