package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImpChatsServiceExtended implements IChatsServiceExtended {

  @Autowired
  private IChatsRepository chatsRepository;


  @Override
  public List<ChatDTO> getUserChats(String token) {
    return null;
  }

  @Override
  public Chat saveChat(Chat chat) {
    return chatsRepository.save(chat);
  }

  @Override
  public Map<String, Chat> getChatsPair(User owner, User contact) {
    return null;
  }

  @Override
  public Chat getOwnerChat(User owner, User contactUser) {
    return null;
  }

  @Override
  public Chat getContactChat(User owner, User contactChat) {
    return null;
  }

  @Override
  public Map<String, Chat> createChatsIfNotExist(Contact contact) {
    return null;
  }

  @Override
  public void deleteContactChatIfExists(Contact contact) {

  }

  @Override
  public Chat findById(Integer chatId) {
    return null;
  }

  @Override
  public Map<String, Chat> getChatsPairMap(Chat ownerChat, Chat contactChat) {
    return null;
  }
}
