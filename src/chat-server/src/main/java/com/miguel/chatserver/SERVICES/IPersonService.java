package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.Person;
import com.miguel.chatserver.MODELS.User;

public interface IPersonService {

  public Person registerPerson(Person person);

  public Person findByPhoneNumber(String phoneNumber);

  public Person createPersonFromRegisterRequest(AuthRegisterRequest request);
}
