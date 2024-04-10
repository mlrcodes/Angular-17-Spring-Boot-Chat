package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;

public interface IAuthenticationService {
  public AuthRegisterResponse register(AuthRegisterRequest request);

  public AuthLoginRequest login(AuthLoginRequest login);
}
