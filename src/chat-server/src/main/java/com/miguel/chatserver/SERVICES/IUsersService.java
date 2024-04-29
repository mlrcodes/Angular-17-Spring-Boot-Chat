package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;

public interface IUsersService {
  public User registerUser(User user);

  public UserDTO getUserByPhoneNumber(String phoneNumber);

  public User findByPhoneNumber(String phoneNumber);

  public User saveUser(User user);

}
