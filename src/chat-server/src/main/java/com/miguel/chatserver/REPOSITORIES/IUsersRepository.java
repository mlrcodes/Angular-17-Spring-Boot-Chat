package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, Integer> {

  public Optional<User> findByPhoneNumber(String phoneNumber);

  public Optional<User> findByEmail(String email);

}
