package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {

  public List<Message> findByChat(Chat chat);

  public void deleteByChat(Chat chat);
}
