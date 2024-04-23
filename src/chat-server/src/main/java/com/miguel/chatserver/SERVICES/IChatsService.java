package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;

import java.util.List;
import java.util.Objects;

public interface IChatsService {
  public List<ChatDTO> getUserChats(String token);

  public ChatDTO createChat(Contact contact);

  public Chat saveChat(Chat chat);

  public Chat getChat(User owner, User contact);

  public Chat createChatsPair(Contact contact);

  public Chat createChatIfNotExists(Contact contact);

  public void deleteContactChatIfExists(Contact contact);

  }
