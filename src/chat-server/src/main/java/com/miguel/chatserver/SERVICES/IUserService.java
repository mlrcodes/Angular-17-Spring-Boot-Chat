package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;

import java.util.List;

public interface IUserService {
  public User registerUser(User user);

  public UserDTO getUserByPhoneNumber(String phoneNumber);

  public User findByPhoneNumber(String phoneNumber);


  public User findByEmail(String phoneNumber);

}
