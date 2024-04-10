package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.Person;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpPersonService implements IPersonService {

  @Autowired
  private IPersonRepository personRepository;

  @Override
  public Person registerPerson(Person person) {
    return this.personRepository.save(person);
  }

  @Override
  public Person findByPhoneNumber(String phoneNumber) {
    return this.personRepository.findByPhoneNumber(phoneNumber).orElse(null);
  }

  @Override
  public Person createPersonFromRegisterRequest(AuthRegisterRequest request) {
    return Person.builder()
      .firstname(request.getFirstname())
      .surname(request.getSurname())
      .phoneNumber(request.getPhoneNumber())
      .build();
  }
}
