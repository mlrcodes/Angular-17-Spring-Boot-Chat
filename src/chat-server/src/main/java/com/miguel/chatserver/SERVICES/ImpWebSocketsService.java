package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ImpWebSocketsService implements IWebSocketsService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private IUsersService usersService;

  @Autowired
  private IChatsService chatsService;

  @Autowired
  private IMessageService messageService;

  @Autowired
  private IJWTService jwtService;


  @Override
  public MessageDTO sendMessage(
    MessageSaveDTO messageSaveDTO,
    SimpMessageHeaderAccessor headerAccessor
  ) {
    User sender = usersService.findByPhoneNumber(
      jwtService.getPhoneNumberFromToken(
        headerAccessor.getFirstNativeHeader("Authorization")
      )
    );
    Chat senderChat = chatsService.findById(messageSaveDTO.getChatId());
    User recipient = senderChat.getContact().getContactUser();
    Chat recipientChat = chatsService.getChat(recipient, sender);

    if (Objects.isNull(senderChat) || Objects.isNull(recipientChat)) {
      throw new ExceptionObjectNotFound("Chat not found");
    }
    String messageText = messageSaveDTO.getMessageText();
    Message savedSenderMessage = messageService.sendMessage(senderChat, messageText);
    Message savedRecipientMessage = messageService.sendMessage(recipientChat, messageText);
    senderChat.getMessages().add(savedSenderMessage);
    recipientChat.getMessages().add(savedRecipientMessage);
    chatsService.saveChat(senderChat);
    chatsService.saveChat(recipientChat);
    return MessageDTO.builder()
      .messageId(savedRecipientMessage.getMessageId())
      .senderPhoneNumber(sender.getPhoneNumber())
      .recipientPhoneNumber(recipient.getPhoneNumber())
      .messageText(savedRecipientMessage.getMessageText())
      .timestamp(savedRecipientMessage.getTimeStamp())
      .build();
  }
}
