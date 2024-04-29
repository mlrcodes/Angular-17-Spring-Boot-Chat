package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Contact;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpContactsMapper implements IContactsMapper {


  @Autowired
  private IUsersMapper usersMapper;

  @Override
  public ContactResponseDTO createContactResponseFromContact(Contact contact) {
    UserDTO contactUserDTO = usersMapper.createUserDTOFromUser(contact.getContactUser());
    return ContactResponseDTO
      .builder()
      .contactId(contact.getContactId())
      .contactName(contact.getContactName())
      .contactUser(contactUserDTO)
      .build();
  }

  @Override
  public List<ContactResponseDTO> createContactResponseListFromContactList(List<Contact> contactsList) {
    List<ContactResponseDTO> contactDTOList = new ArrayList<>();
    for (Contact contact: contactsList) {
      contactDTOList.add(
        this.createContactResponseFromContact(contact)
      );
    }
    return contactDTOList;
  }

}
