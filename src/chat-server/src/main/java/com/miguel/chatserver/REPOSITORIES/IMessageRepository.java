package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
}
