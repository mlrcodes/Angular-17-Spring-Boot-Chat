package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Message;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpMessagesMapper implements IMessagesMapper {

  @Autowired
  private IUsersMapper usersMapper;

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
    UserDTO senderDTO = usersMapper.createUserDTOFromUser(message.getSender());

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
