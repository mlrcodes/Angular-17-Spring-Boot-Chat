package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.MODELS.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpMessagesMapper implements IMessagesMapper {

  @Autowired
  private IUsersMapper usersMapper;

  @Override
  public MessageDTO createMessageDTOFromMessage(Message message) {
    return MessageDTO
      .builder()
      .messageId(message.getMessageId())
      .senderPhoneNumber(message.getSender().getPhoneNumber())
      .recipientPhoneNumber(message.getChat().getContact().getContactUser().getPhoneNumber())
      .chatId(message.getChat().getChatId())
      .messageText(message.getMessageText())
      .timestamp(message.getTimeStamp())
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
