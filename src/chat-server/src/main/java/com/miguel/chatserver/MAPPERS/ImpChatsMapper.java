package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Chat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpChatsMapper implements IChatsMapper {

  @Autowired
  private IUsersMapper usersMapper;

  @Autowired
  private IMessagesMapper messagesMapper;

  @Override
  public Chat createChatFromDTO(ChatDTO chatDTO) {
    return null;
  }

  @Override
  public ChatDTO createChatDTOFromChat(Chat chat) {
    List<UserDTO> membersDTO = usersMapper.createUserDTOListFromUserList(chat.getMembers());
    List<MessageDTO> messagesDTO = messagesMapper.createMessageDTOListFromMessageList(chat.getMessages());
    return ChatDTO
      .builder()
      .members(membersDTO)
      .messages(messagesDTO)
      .build();
  }

  @Override
  public List<ChatDTO> createChatDTOListFromChatList(List<Chat> chatList) {
    List<ChatDTO> chatDTOList = new ArrayList<>();
    for (Chat chat: chatList) {
      chatDTOList.add(
        this.createChatDTOFromChat(chat)
      );
    }
    return chatDTOList;
  }

}
