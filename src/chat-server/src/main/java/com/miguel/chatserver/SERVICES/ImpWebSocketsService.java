package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.MODELS.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImpWebSocketsService implements IWebSocketsService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private IUsersService usersService;

  @Autowired
  private IChatsService chatsService;

  @Autowired
  private IJWTService jwtService;


  @Override
  public MessageDTO sendMessage(
    Integer contactId,
    MessageSaveDTO messageSaveDTO,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    User sender = usersService.findByPhoneNumber(
      jwtService.getPhoneNumberFromToken(
        headerAccessor.getFirstNativeHeader("Authorization")
      )
    );
  }
}
