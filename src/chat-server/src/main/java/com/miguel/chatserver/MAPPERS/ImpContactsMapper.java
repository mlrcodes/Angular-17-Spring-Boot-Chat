package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpContactsMapper implements IContactsMapper {


  @Autowired
  private IUsersMapper usersMapper;

  @Override
  public Contact createContactFromDTO(ContactDTO contactDTO) {
    return null;
  }

  @Override
  public List<Contact> createContactListFromDTOList(List<ContactDTO> contactDTOList) {
    return null;
  }

  @Override
  @Transactional
  public ContactDTO createContactDTOFromContact(Contact contact) {
    UserDTO contactUserDTO = usersMapper.createUserDTOFromUser(contact.getContactUser());

    return ContactDTO
      .builder()
      .contactId(contact.getContactId())
      .contactName(contact.getContactName())
      .contactUser(contactUserDTO)
      .build();
  }

  @Override
  public List<ContactDTO> createContactDTOListFromContactList(List<Contact> contactsList) {
    List<ContactDTO> contactDTOList = new ArrayList<>();
    for (Contact contact: contactsList) {
      contactDTOList.add(
        this.createContactDTOFromContact(contact)
      );
    }
    return contactDTOList;
  }

}
