package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Chat;

import java.util.ArrayList;
import java.util.List;

public interface IChatsService {
  public List<ChatDTO> getUserChats(String token);

  public Chat createChatFromDTO(ChatDTO chatDTO);

  public ChatDTO createChatDTOFromChat(Chat chat);

  public List<ChatDTO> createChatDTOListFromChatList(List<Chat> chatList);
}
