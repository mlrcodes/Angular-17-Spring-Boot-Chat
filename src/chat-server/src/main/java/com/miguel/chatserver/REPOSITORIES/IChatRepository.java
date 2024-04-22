package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Integer> {
}
