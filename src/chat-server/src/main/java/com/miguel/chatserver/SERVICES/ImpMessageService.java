package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpMessageService implements IMessageService {

  @Autowired
  private IUserService userService;

  @Override
  public Message createMessageFromDTO(MessageDTO messageDTO) {
    return null;
  }

  @Override
  public List<Message> createMessageListFromDTOList(List<MessageDTO> messageDTOList) {
    return null;
  }

  @Override
  public MessageDTO createMessageDTOFromMessage(Message message) {
    UserDTO senderDTO = userService.createUserDTOFromUser(message.getSender());

    return MessageDTO
      .builder()
      .sender(senderDTO)
      .dateTime(message.getDateTime())
      .build();
  }

  @Override
  public List<MessageDTO> createMessageDTOListFromMessageList(List<Message> messageList) {
    List<MessageDTO> messageDTOList = new ArrayList<>();
    for (Message message: messageList) {
      messageDTOList.add(
        this.createMessageDTOFromMessage(message)
      );
    }
    return messageDTOList;
  }
}
