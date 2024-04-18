package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatsRepository extends JpaRepository<Chat, Integer> {

  @Query("SELECT DISTINCT c FROM Chat c JOIN c.members u WHERE u.phoneNumber = :phoneNumber")
  public List<Chat> findUserChats(@Param("phoneNumber") String phoneNumber);


}
