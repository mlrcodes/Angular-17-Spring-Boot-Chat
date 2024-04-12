package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {
  public Optional<Token> findByToken(String token);
}
