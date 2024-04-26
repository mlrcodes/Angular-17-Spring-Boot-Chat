package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IChatsService {
  public List<ChatDTO> getUserChats(String token);

  public ChatDTO createChat(String phoneNumber);

  public Chat saveChat(Chat chat);

  public Map<String, Chat> getChatsPair(User owner, User contact);

  public Chat getContactChat(User owner, User contactChat);


  public Map<String, Chat> createChatsPair(Contact contact);

  public Map<String, Chat> createChatsIfNotExist(Contact contact);

  public void deleteContactChatIfExists(Contact contact);

  public Chat findById(Integer chatId);

  }
