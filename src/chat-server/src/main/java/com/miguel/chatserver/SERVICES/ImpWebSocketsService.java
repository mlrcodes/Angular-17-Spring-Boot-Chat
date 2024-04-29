package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.MODELS.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
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
  private IMessagesMapper messagesMapper;

  @Override
  @Transactional
  public void  sendMessage(
    MessageSaveDTO messageSaveDTO
  ) {
    Chat senderChat = chatsService.findById(messageSaveDTO.getChatId());
    User sender = senderChat.getUser();
    User recipient = senderChat.getContact().getContactUser();
    Chat recipientChat = chatsService.getContactChat(sender, recipient);

    if (Objects.isNull(senderChat) || Objects.isNull(recipientChat)) {
      throw new ExceptionObjectNotFound("Chat not found");
    }

    Map<String, Message> messagesMap = messageService.sendMessageAndUpdateChatsPair(
      chatsService.getChatsPairMap(senderChat, recipientChat),
      sender,
      messageSaveDTO.getMessageText()
    );

    Message contactMessage = messagesMap.get("contactMessage");
    MessageDTO contactMessageDTO = messagesMapper.createMessageDTOFromMessage(contactMessage);
    messagingTemplate.convertAndSendToUser(
      recipient.getPhoneNumber(),
      "/queue/messages",
      contactMessageDTO
    );

    Message senderMessage = messagesMap.get("senderMessage");
    MessageDTO senderMessageDTO = messagesMapper.createMessageDTOFromMessage(senderMessage);
    messagingTemplate.convertAndSendToUser(
      sender.getPhoneNumber(),
      "/queue/messages",
      senderMessageDTO
    );
  }
}
