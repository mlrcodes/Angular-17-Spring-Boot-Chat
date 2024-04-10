package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.User;

public interface IUserService {
  public User registerUser(User user);

  public User findByPhoneNumber(String phoneNumber);
  public User createUserFromRegisterRequest(AuthRegisterRequest request);
}
