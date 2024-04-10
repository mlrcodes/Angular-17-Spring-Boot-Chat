package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.MODELS.Person;
import com.miguel.chatserver.MODELS.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ImpAuthenticationService implements IAuthenticationService{

  @Autowired
  private IPersonService personService;

  @Autowired
  private IUserService userService;

  @Override
  public AuthRegisterResponse register(AuthRegisterRequest request) {

    User existingUser = this.userService.findByPhoneNumber(request.getPhoneNumber());

    if (Objects.nonNull(existingPerson)) {
      return null; // Crear y manejar excepción
    }

    Person person = this.personService.createPersonFromRegisterRequest(request);


    User user = this.userService.createUserFromRegisterRequest(request);


    Person savedPerson = this.personService.registerPerson(person);
    User savedUser = this.userService.registerUser(user);
  }

  @Override
  public AuthLoginRequest login(AuthLoginRequest login) {
    return null;
  }
}
