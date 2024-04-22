package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.Status;
import com.miguel.chatserver.MODELS.User;

import java.util.Objects;

public interface IUsersService {
  public User registerUser(User user);

  public UserDTO getUserByPhoneNumber(String phoneNumber);

  public User findByPhoneNumber(String phoneNumber);

  public void connectUser(User user);
  public void disconnect(User user);

}
