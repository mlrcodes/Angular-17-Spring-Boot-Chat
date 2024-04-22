package com.miguel.chatserver.SERVICES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpChatService {

  @Autowired
  private IMessageService messageService;

  @Autowired
  private IChatRepository chatRepository;

  @Autowired
  private IUsersService usersService;

  @Autowired
  private IContactService contactService;




}
