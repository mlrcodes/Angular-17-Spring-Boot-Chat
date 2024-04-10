package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person, Integer> {
  public Optional<Person> findByPhoneNumber(String phoneNumber);
}
