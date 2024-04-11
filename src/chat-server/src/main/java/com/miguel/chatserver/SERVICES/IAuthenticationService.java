package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;

public interface IAuthenticationService {
  public AuthRegisterResponse register(AuthRegisterRequest request);

  public AuthLoginResponse login(AuthLoginRequest login);
}
