package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.MODELS.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpContactService implements IContactService {
  @Override
  public Contact createContactFromDTO(ContactDTO contactDTO) {
    return null;
  }

  @Override
  public List<Contact> createContactListFromDTOList(List<ContactDTO> contactDTO) {
    return null;
  }
}
