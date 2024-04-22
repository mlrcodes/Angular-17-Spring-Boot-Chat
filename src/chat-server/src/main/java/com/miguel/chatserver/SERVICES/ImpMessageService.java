package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MAPPERS.IMessagesMapper;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.Message;
import com.miguel.chatserver.REPOSITORIES.IMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpMessageService implements IMessageService {


  @Autowired
  private IMessageRepository messageRepository;

  @Autowired
  private IMessagesMapper messagesMapper;

  @Override
  public Message saveMessage(Message message) {

    Message savedMessage;
    try {
      savedMessage = messageRepository.save(message);
    } catch (Exception ex) {
      throw new DataAccessException("Error saving new Message", ex) {};
    }

    return savedMessage;
  }
}
