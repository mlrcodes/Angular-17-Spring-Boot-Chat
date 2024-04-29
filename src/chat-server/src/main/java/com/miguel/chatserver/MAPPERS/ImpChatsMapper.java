package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.*;
import com.miguel.chatserver.MODELS.Chat;
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

  @Autowired
  private IContactsMapper contactsMapper;

  @Override
  public Chat createChatFromDTO(ChatDTO chatDTO) {
    return null;
  }

  @Override
  public ChatDTO createChatDTOFromChat(Chat chat) {
    ContactResponseDTO contactResponse = contactsMapper.createContactResponseFromContact(chat.getContact());
    List<MessageDTO> messagesDTOList = messagesMapper.createMessageDTOListFromMessageList(chat.getMessages());
    return ChatDTO
      .builder()
      .chatId(chat.getChatId())
      .contact(contactResponse)
      .messages(messagesDTOList)
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
