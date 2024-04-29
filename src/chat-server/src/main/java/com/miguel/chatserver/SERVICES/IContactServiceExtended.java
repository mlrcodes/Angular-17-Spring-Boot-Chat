package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

public interface IContactServiceExtended extends IContactService {
  public Contact findContactByPhoneNumber(String phoneNumber);

  public Contact findContactOrCreateDefaultOne(User owner, User contactUser);

}
