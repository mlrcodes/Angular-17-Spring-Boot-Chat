package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.MODELS.Contact;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImpContactsMapper implements IContactsMapper {

  @Override
  public Contact createContactFromDTO(ContactDTO contactDTO) {
    return null;
  }

  @Override
  public List<Contact> createContactListFromDTOList(List<ContactDTO> contactDTO) {
    return null;
  }

  @Override
  public ContactDTO createContactDTOFromContact(Contact contact) {
    return null;
  }

  @Override
  public List<ContactDTO> createContactDTOListFromContactList(List<Contact> contact) {
    return null;
  }

}
