package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.MODELS.Message;

import java.util.List;

public interface IMessagesMapper {

  public MessageDTO createMessageDTOFromMessage(Message message);

  public List<MessageDTO> createMessageDTOListFromMessageList(List<Message> messageList);
}
