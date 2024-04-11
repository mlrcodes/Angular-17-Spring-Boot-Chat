package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

  public Optional<User> findByPhoneNumber(String phoneNumber);

  @Query(value = "SELECT COUNT(*) > 0 FROM Users WHERE phoneNumber = :phoneNumber", nativeQuery = true)
  public Boolean existsPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
