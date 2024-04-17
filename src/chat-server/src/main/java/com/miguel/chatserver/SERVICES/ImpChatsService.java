package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpChatsService implements IChatsService {

  @Autowired
  private IChatRepository chatRepository;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private IUserService userService;

  @Autowired
  private IMessageService messageService;

  @Override
  public List<ChatDTO> getUserChats(String jwtToken) {
    String phoneNumber = jwtService.getPhoneNumberFromToken(jwtToken);
    List<Chat> userChats = chatRepository.findUserChats(phoneNumber);
    if(userChats.isEmpty()) {
      throw new ExceptionObjectNotFound("User do not have any active chat");
    }
    return this.createChatDTOListFromChatList(userChats);
  }

  @Override
  public Chat createChatFromDTO(ChatDTO chatDTO) {
    return null;
  }

  @Override
  public ChatDTO createChatDTOFromChat(Chat chat) {
    List<UserDTO> membersDTO = userService.createUserDTOListFromUserList(chat.getMembers());
    List<MessageDTO> messagesDTO = messageService.createMessageDTOListFromMessageList(chat.getMessages());
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
