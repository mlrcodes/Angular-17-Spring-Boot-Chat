package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

public interface IContactServiceExtended extends IContactService {
  public Contact findContactByOwnerAndContactUser(User owner, User contactUser);

  public Contact createDefaultContact(User owner, User contactUser);
}
