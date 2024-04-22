package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Chat;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChatsRepository extends JpaRepository<Chat, Integer> {

  Optional<Chat> findByUserAndContact(User user, Contact contact);

}
