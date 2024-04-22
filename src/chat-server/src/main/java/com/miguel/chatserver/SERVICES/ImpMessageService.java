package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.REPOSITORIES.IMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImpMessageService implements IMessageService {


  @Autowired
  private IMessageRepository messageRepository;

  @Autowired
  private IMessagesMapper messagesMapper;


  @Override
  public Message sendMessage(Chat chat, String messageText) {
    Message message = Message
      .builder()
      .sender(chat.getUser())
      .dateTime(LocalDateTime.now())
      .messageText(messageText)
      .chat(chat)
      .build();

    return messageRepository.save(message);
  }
}
