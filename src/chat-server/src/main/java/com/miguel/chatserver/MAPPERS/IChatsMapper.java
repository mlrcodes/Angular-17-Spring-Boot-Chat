package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Chat;

import java.util.ArrayList;
import java.util.List;

public interface IChatsMapper {


  public Chat createChatFromDTO(ChatDTO chatDTO);

  public ChatDTO createChatDTOFromChat(Chat chat);

  public List<ChatDTO> createChatDTOListFromChatList(List<Chat> chatList);
}
